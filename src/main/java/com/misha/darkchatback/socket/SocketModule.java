package com.misha.darkchatback.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.misha.darkchatback.adapter.ChatAdapter;
import com.misha.darkchatback.adapter.Peer;
import com.misha.darkchatback.dto.socket.AddPeerDto;
import com.misha.darkchatback.dto.socket.EmptyDto;
import com.misha.darkchatback.dto.socket.GetMessageDto;
import com.misha.darkchatback.dto.socket.JoinRoomDto;
import com.misha.darkchatback.dto.socket.RelayIceDto;
import com.misha.darkchatback.dto.socket.RelaySdpDto;
import com.misha.darkchatback.dto.socket.RemovePeerDto;
import com.misha.darkchatback.dto.socket.SendMessageDto;
import com.misha.darkchatback.service.SocketService;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketModule {
    public static final String JOIN = "join";
    public static final String ADD_PEER = "add-peer";
    public static final String LEAVE = "leave";
    public static final String REMOVE_PEER = "remove-peer";
    public static final String RELAY_SDP = "relay-sdp";
    public static final String RELAY_ICE = "relay-ice";
    public static final String ICE_CANDIDATE = "ice-candidate";
    public static final String SESSION_DESCRIPTION = "session-description";
    public static final String REQUEST_INITIAL_ROOMS_UPDATE = "request-initial-rooms-update";
    public static final String ROOM_NEW_MESSAGE = "room-new-message";
    public static final String ROOM_RECEIVE_NEW_MESSAGE = "room-receive-new-message";

    private final SocketIOServer server;
    private final SocketService socketService;
    private final ChatAdapter adapter;

    public SocketModule(SocketIOServer server, SocketService socketService, ChatAdapter adapter) {
        this.server = server;
        this.socketService = socketService;
        this.adapter = adapter;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener(JOIN, JoinRoomDto.class, onJoin());
        server.addEventListener(LEAVE, JoinRoomDto.class, onLeave());
        server.addEventListener(RELAY_SDP, RelaySdpDto.class, onRelaySdp());
        server.addEventListener(RELAY_ICE, RelayIceDto.class, onRelayIce());
        server.addEventListener(REQUEST_INITIAL_ROOMS_UPDATE, EmptyDto.class,
                onRequestInitialRoomsUpdate());
        server.addEventListener(ROOM_NEW_MESSAGE, GetMessageDto.class, onGetMessage());
    }

    private DataListener<GetMessageDto> onGetMessage() {
        return ((socketIoClient, getMessageDto, ackRequest) -> {
            List<Peer> clients = adapter.getRooms().get(getMessageDto.getRoomID());
            clients.forEach(peer -> {
                server.getClient(peer.getUuid())
                        .sendEvent(ROOM_RECEIVE_NEW_MESSAGE, new SendMessageDto(
                        getMessageDto.getMessage(), getMessageDto.getAuthor(),
                                getMessageDto.getColor()
                ));
            });
        });
    }

    private DataListener<EmptyDto> onRequestInitialRoomsUpdate() {
        return (socketIoClient, emptyDto, ackRequest) -> {
            socketService.sendUpdateRoomsEvent(server, adapter);
        };
    }

    private DataListener<JoinRoomDto> onJoin() {
        return (socketIoClient, roomIdObj, ackRequest) -> {
            if (!adapter.getRooms().containsKey(roomIdObj.getRoom())) {
                adapter.createRoom(roomIdObj.getRoom());
            }
            List<Peer> clients = adapter.getRooms().get(roomIdObj.getRoom());

            clients.forEach(peer -> {
                server.getClient(peer.getUuid()).sendEvent(
                        ADD_PEER,
                        new AddPeerDto(
                                socketIoClient.getSessionId().toString(),
                                false,
                                roomIdObj.getUsername()
                        )
                );
                socketIoClient.sendEvent(
                        ADD_PEER,
                        new AddPeerDto(
                                server.getClient(peer.getUuid()).getSessionId().toString(),
                                true,
                                peer.getUsername()
                        )
                );
            });

            adapter.addPeerToRoom(new Peer(socketIoClient.getSessionId(), roomIdObj.getUsername()),
                    roomIdObj.getRoom());

            socketService.sendUpdateRoomsEvent(server, adapter);
        };
    }

    private DataListener<RelayIceDto> onRelayIce() {
        return (socketIoClient, relayIceObj, ackRequest) -> {
            server.getClient(UUID.fromString(relayIceObj.getPeerID())).sendEvent(
                    ICE_CANDIDATE,
                    new RelayIceDto(
                            socketIoClient.getSessionId().toString(),
                            relayIceObj.getIceCandidate()
                    )
            );
        };
    }

    private DataListener<RelaySdpDto> onRelaySdp() {
        return (socketIoClient, relaySdpObj, ackRequest) -> {
            server.getClient(UUID.fromString(relaySdpObj.getPeerID()))
                    .sendEvent(SESSION_DESCRIPTION,
                            new RelaySdpDto(socketIoClient.getSessionId().toString(),
                                    relaySdpObj.getSessionDescription()));
        };
    }

    private DataListener<JoinRoomDto> onLeave() {
        return (socketIoClient, roomIdObj, ackRequest) -> {
            leaveAllRooms(socketIoClient);
            adapter.removePeerFromAllRooms(socketIoClient);
        };
    }

    private void leaveAllRooms(SocketIOClient socketIoClient) {
        adapter.getRooms().forEach((key, value) -> {
            adapter.getRooms().get(key).forEach(peer -> {
                server.getClient(peer.getUuid()).sendEvent(
                        REMOVE_PEER,
                        new RemovePeerDto(socketIoClient.getSessionId().toString())
                );
                socketIoClient.sendEvent(
                        REMOVE_PEER,
                        new RemovePeerDto(server.getClient(peer.getUuid())
                                .getSessionId().toString())
                );
            });
        });

        socketService.sendUpdateRoomsEvent(server, adapter);
    }

    private ConnectListener onConnected() {
        return adapter::addPeer;
    }

    private DisconnectListener onDisconnected() {
        return socketIOClient -> {
            adapter.removePeerFromAllRooms(socketIOClient);
            adapter.removePeer(socketIOClient);
            leaveAllRooms(socketIOClient);
        };
    }
}
