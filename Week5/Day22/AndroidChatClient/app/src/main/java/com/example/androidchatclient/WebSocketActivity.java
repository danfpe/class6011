package com.example.androidchatclient;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import com.neovisionaries.ws.client.WebSocketAdapter;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class WebSocketActivity extends WebSocketAdapter {
    String message = "I enjoy learning new things";


    @Override
    public void onConnected(com.neovisionaries.ws.client.WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        Log.i("GD:  1WebSocket:", "Connected.");
    }

    @Override
    public void onTextMessage (com.neovisionaries.ws.client.WebSocket webSocket, String payload ) throws Exception {
        Log.i("GD: message", "glllllll");
        JSONObject json  = new JSONObject(payload);
        String type = (String) json.get("type");
        String user = (String) json.get("user");
        String room = (String) json.get("room");

        String timeStamp = " ";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            timeStamp = new SimpleDateFormat("HH:mm").format(new java.util.Date());
        }
        if ( type.equals("join")) {
            message = user + " has joined " + room + "  " + timeStamp;
            Log.i("GD: Message ", message);
        }
        else if (type.equals("message")) {
            String jsonMessage = (String) json.get("message");
            message = user + ": " +  jsonMessage + "  " + timeStamp;
            Log.i("GD: Message ", message);

        }else{
            message = user + " has left the " + room + "  " + timeStamp;
            Log.i("GD: Message ", message);
        }

        ChatDashBoard.messages.add(message);
        ChatDashBoard.lv_.post(new Runnable() {
            @Override
            public void run() {
                ChatDashBoard.adapter_.notifyDataSetChanged();
                ChatDashBoard.lv_.smoothScrollToPosition(ChatDashBoard.adapter_.getCount() );
            }
        });

    }



}
