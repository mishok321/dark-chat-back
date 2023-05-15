package com.misha.darkchatback.socket.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.misha.darkchatback.adapter.ChatAdapter;
import com.misha.darkchatback.socket.socket.ListOfRoomsDto;
import com.misha.darkchatback.service.SocketService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SocketServiceImpl implements SocketService {
    public static final String SHARE_ROOMS = "share-rooms";

    public void sendUpdateRoomsEvent(SocketIOServer server, ChatAdapter adapter) {
        List<Map<String, String>> rooms = new ArrayList<>();
        List<String> roomsIds = adapter.getRooms().keySet().stream().toList();
        roomsIds.forEach(id -> {
            rooms.add(
                    Map.of("roomID", id,
                            // room creator will always have index 0
                            "username", adapter.getRooms().get(id).get(0).getUsername(),
                            "usersInRoom", adapter.getRooms().get(id).size() + "")
            );
        });
        server.getBroadcastOperations().sendEvent(SHARE_ROOMS, new ListOfRoomsDto(rooms));
    }
}
