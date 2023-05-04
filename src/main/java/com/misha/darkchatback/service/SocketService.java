package com.misha.darkchatback.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.misha.darkchatback.adapter.ChatAdapter;

public interface SocketService {
    void sendUpdateRoomsEvent(SocketIOServer server, ChatAdapter adapter);
}
