package edu.apsu.csci.games.drawit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int PHOTO_INTENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.choose_pic_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoChoiceIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoChoiceIntent, PHOTO_INTENT);
            }
        });

        findViewById(R.id.black_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorListener(v);
            }
        });
        findViewById(R.id.blue_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorListener(v);
            }
        });
        findViewById(R.id.green_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorListener(v);
            }
        });
        findViewById(R.id.red_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorListener(v);
            }
        });
        findViewById(R.id.white_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorListener(v);
            }
        });
        findViewById(R.id.yellow_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorListener(v);
            }
        });

        findViewById(R.id.stroke_2_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStrokeListener(v);
            }
        });

        findViewById(R.id.stroke_4_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStrokeListener(v);
            }
        });

        findViewById(R.id.stroke_8_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStrokeListener(v);
            }
        });

        findViewById(R.id.stroke_12_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStrokeListener(v);
            }
        });

        findViewById(R.id.stroke_16_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStrokeListener(v);
            }
        });
    }

    private void setColorListener(View v){
        DrawIt drawit = findViewById(R.id.draw_canvas);
        switch(v.getId()){
            case R.id.black_btn : drawit.setPaintColor(Color.BLACK); break;
            case R.id.blue_btn : drawit.setPaintColor(Color.BLUE); break;
            case R.id.green_btn : drawit.setPaintColor(Color.GREEN); break;
            case R.id.red_btn : drawit.setPaintColor(Color.RED); break;
            case R.id.white_btn : drawit.setPaintColor(Color.WHITE); break;
            case R.id.yellow_btn : drawit.setPaintColor(Color.YELLOW); break;
        }
    }

    private void setStrokeListener(View v){
        DrawIt drawIt = findViewById(R.id.draw_canvas);
        switch (v.getId()){
            case R.id.stroke_2_btn : drawIt.setStroke(2); break;
            case R.id.stroke_4_btn : drawIt.setStroke(4); break;
            case R.id.stroke_8_btn : drawIt.setStroke(8); break;
            case R.id.stroke_12_btn : drawIt.setStroke(12); break;
            case R.id.stroke_16_btn : drawIt.setStroke(16); break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_INTENT && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            Log.i("onActivityResult", "Result OK and PHOTO_INTENT caller " +imageUri.getPath());
            //Drawable drawable = Drawable.createFromPath(imageUri.toString());
            //findViewById(R.id.draw_canvas).setBackground(drawable);
            DrawIt drawIt = findViewById(R.id.draw_canvas);
            drawIt.setImage(getContentResolver(), imageUri);
        }
    }
}
