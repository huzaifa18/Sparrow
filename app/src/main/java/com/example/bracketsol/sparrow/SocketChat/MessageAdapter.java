package com.example.bracketsol.sparrow.SocketChat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bracketsol.sparrow.MessageActivity.ChatActivityMain;
import com.example.bracketsol.sparrow.MyApp;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Utils.Prefs;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<MessageFormat> {

    Context mcontext;

    public MessageAdapter(ChatActivityMain context, int resource, List<MessageFormat> objects) {

        super((Context) context, resource, objects);
        mcontext = (Context) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(MainActivity.TAG, "getView:");

        MessageFormat message = getItem(position);

        Log.e("TAG", "ok" + message.getMessage());
        Log.e("TAG", "ok" + message.getUsername());
        Log.e("TAG", "ok" + message.getUniqueId());

        if (TextUtils.isEmpty(message.getMessage())) {


            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.socket_user_connected, parent, false);

            TextView messageText = convertView.findViewById(R.id.message_body);
            ImageView imageView = convertView.findViewById(R.id.my_image);

            Log.i(MainActivity.TAG, "getView: is empty ");
            String userConnected = message.getUsername();
            messageText.setText(userConnected);


        } else if (message.getUniqueId() != Prefs.getUserIDFromPref(mcontext)) {
            Log.i(MainActivity.TAG, "getView: " + message.getUniqueId() + " " + MainActivity.uniqueId);


            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.my_message, parent, false);
            TextView messageText = convertView.findViewById(R.id.message_body);
            ImageView imageView = convertView.findViewById(R.id.my_image);
            Log.i("users","my message"+message.getUniqueId()+message.getUsername());

            if (message.getImg()!= null && !message.getImg().isEmpty()) {
                imageView.setVisibility(View.VISIBLE);
                byte[] decodedString = Base64.decode(message.getImg(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                Log.i("users","my message"+message.getMessage());
                Glide.with(mcontext).load(decodedByte).into(imageView);

            } else {
                imageView.setVisibility(View.GONE);

            }

            Glide.with(mcontext)
                    .load(message.getImg())
                    .into(imageView);
            messageText.setText(message.getMessage());


        } else {
            Log.i(MainActivity.TAG, "getView: is not empty");

            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.their_message, parent, false);

            TextView messageText = convertView.findViewById(R.id.message_body);
            ImageView imageView = convertView.findViewById(R.id.their_image);
            TextView usernameText = (TextView) convertView.findViewById(R.id.name);


            messageText.setVisibility(View.VISIBLE);
            usernameText.setVisibility(View.VISIBLE);

            messageText.setText(message.getMessage());
            usernameText.setText(message.getUsername());
            if (message.getImg()!= null && !message.getImg().isEmpty()) {
                imageView.setVisibility(View.VISIBLE);
                byte[] decodedString = Base64.decode(message.getImg(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                Glide.with(mcontext).load(decodedByte).into(imageView);


                messageText.setVisibility(View.VISIBLE);
                usernameText.setVisibility(View.VISIBLE);

                messageText.setText(message.getMessage());
                usernameText.setText(message.getUsername());
            } else {
                imageView.setVisibility(View.GONE);
                messageText.setVisibility(View.VISIBLE);
                usernameText.setVisibility(View.VISIBLE);

                messageText.setText(message.getMessage());
                usernameText.setText(message.getUsername());


            }
        }
        return convertView;
    }
}
