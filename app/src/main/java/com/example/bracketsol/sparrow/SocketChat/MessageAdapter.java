package com.example.bracketsol.sparrow.SocketChat;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bracketsol.sparrow.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class MessageAdapter extends ArrayAdapter<MessageFormat> {
    public MessageAdapter(Callback<ResponseBody> context, int resource, List<MessageFormat> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(MainActivity.TAG, "getView:");

        MessageFormat message = getItem(position);

        if(TextUtils.isEmpty(message.getMessage())){


            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.socket_user_connected, parent, false);

            TextView messageText = convertView.findViewById(R.id.message_body);

            Log.i(MainActivity.TAG, "getView: is empty ");
            String userConnected = message.getUsername();
            messageText.setText(userConnected);

        }else if(message.getUniqueId().equals(MainActivity.uniqueId)){
            Log.i(MainActivity.TAG, "getView: " + message.getUniqueId() + " " + MainActivity.uniqueId);


            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.my_message, parent, false);
            TextView messageText = convertView.findViewById(R.id.message_body);
            messageText.setText(message.getMessage());

        }else {
            Log.i(MainActivity.TAG, "getView: is not empty");

            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.their_message, parent, false);

            TextView messageText = convertView.findViewById(R.id.message_body);
            TextView usernameText = (TextView) convertView.findViewById(R.id.name);

            messageText.setVisibility(View.VISIBLE);
            usernameText.setVisibility(View.VISIBLE);

            messageText.setText(message.getMessage());
            usernameText.setText(message.getUsername());
        }

        return convertView;
    }
}
