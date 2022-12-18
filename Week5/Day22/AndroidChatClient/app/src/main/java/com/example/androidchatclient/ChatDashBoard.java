package com.example.androidchatclient;
import static com.example.androidchatclient.MainActivity.webSocket;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatDashBoard extends AppCompatActivity {

    public static ArrayList<String> messages = new ArrayList<>(); // messages that comes in
    public static ListView lv_;
    public static ArrayAdapter adapter_;

    String userName_;
    String roomName_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_dash_board);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName_ = extras.getString("userName");
            roomName_ = extras.getString("roomName");
            Log.i("GD: ", roomName_);
        }

        TextView roomTitle = findViewById(R.id.roomID);
        roomTitle.setText(roomName_);
        lv_ = findViewById(R.id.listmessageID);
        // display the messages on the UI
        adapter_ = new ArrayAdapter(ChatDashBoard.this, android.R.layout.simple_list_item_1, messages);
        lv_.setAdapter(adapter_);

    }


    public void SendMessageToServer(View view) {
        TextView  textView =findViewById(R.id.outPutMessageID);
        String msgTobeSent = textView.getText().toString();
        webSocket.sendText(userName_ + " " + msgTobeSent );
//        messages.add(msgTobeSent);
        Log.i("GD:Chat Page", "Send button pressed");

    }

    public void handleSignOutbtn(View view) {
        Intent intent = new Intent(ChatDashBoard.this, MainActivity.class);
        startActivity(intent);
    }

}
