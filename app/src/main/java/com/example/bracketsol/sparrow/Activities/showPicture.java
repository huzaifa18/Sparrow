package com.example.bracketsol.sparrow.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.bracketsol.sparrow.R;

public class showPicture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);
        Bundle extras = getIntent().getExtras();
        byte[] image = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        ImageView imagee = (ImageView) findViewById(R.id.imageView1);
        imagee.setImageBitmap(bmp);
    }
}
