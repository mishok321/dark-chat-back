package com.misha.darkchatback.adapter;

import com.corundumstudio.socketio.SocketIOClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ChatAdapter {

    private static final Set<UUID> allPeers = new HashSet<>();
    private static final Map<String, List<Peer>> rooms = new HashMap<>();

    public ChatAdapter() {
    }

    public void addPeer(SocketIOClient client) {
        allPeers.add(client.getSessionId());
    }

    public void removePeer(SocketIOClient client) {
        allPeers.remove(client.getSessionId());
    }

    public Set<UUID> getAllPeers() {
        return allPeers;
    }

    public void createRoom(String roomID) {
        rooms.put(roomID, new ArrayList<>());

    }

    public void addPeerToRoom(Peer client, String roomID) {
        rooms.get(roomID).add(client);
    }

    public void removePeerFromAllRooms(SocketIOClient client) {
        for (Map.Entry<String, List<Peer>> e: rooms.entrySet()) {
            if (e.getValue().stream().map(Peer::getUuid).toList()
                    .contains(client.getSessionId())) {
                if (rooms.get(e.getKey()).size() == 1) {
                    rooms.remove(e.getKey());
                    return;
                }
                rooms.get(e.getKey()).remove(
                        e.getValue().stream()
                                .filter(el -> el.getUuid().equals(client.getSessionId()))
                                .findFirst().get()
                );
            }
        }

    }

    public Map<String, List<Peer>> getRooms() {
        return rooms;
    }
}
