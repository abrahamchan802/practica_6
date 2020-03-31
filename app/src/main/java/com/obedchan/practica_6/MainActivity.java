package com.obedchan.practica_6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    public String serverIP;
    public String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText ipText = findViewById(R.id.editText);
        final EditText msgText = findViewById(R.id.editText2);


        Button msgBtn = findViewById(R.id.button);
        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverIP = ipText.getText().toString();
                msg = msgText.getText().toString();
                sendMsg();
            }
        });
    }

    public void sendMsg(){
        MsgTask mt = new MsgTask();
        mt.execute();
    }

    class MsgTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Socket socket = new Socket(serverIP,4444);
                if(socket != null){
                    ObjectOutputStream OOS = new ObjectOutputStream(socket.getOutputStream());
                    OOS.writeObject(msg);

                    ObjectInputStream OIS = new ObjectInputStream(socket.getInputStream());
                    String message = (String) OIS.readObject();
                    Log.i("Socket", message);
                    socket.close();
                }
                return null;
            }catch (Exception e){
                return null;
            }
        }
    }


}
