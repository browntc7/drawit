package edu.apsu.csci.games.drawit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PHOTO_INTENT = 1;
    private Uri imageUri;
    private SharedPreferences preferences;
    private static final String drawKey = "drawKey";

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

        findViewById(R.id.event_circle_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawIt drawIt = findViewById(R.id.draw_canvas);
                drawIt.setEvent(DrawIt.CIRCLE_EVENT);
            }
        });

        findViewById(R.id.event_pencil_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawIt drawIt = findViewById(R.id.draw_canvas);
                drawIt.setEvent(DrawIt.PENCIL_EVENT);
            }
        });

        findViewById(R.id.event_sqaure_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawIt drawIt = findViewById(R.id.draw_canvas);
                drawIt.setEvent(DrawIt.SQUARE_EVENT);
            }
        });

        final Button save_button = findViewById(R.id.save_button);
        preferences = this.getSharedPreferences("prefID", Context.MODE_PRIVATE);
        final DrawIt drawIt = findViewById(R.id.draw_canvas);


        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener()  {

            @Override
            public void onClick(View v) {
                String draw = drawIt.toString();

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString(drawKey, draw);
                editor.apply();
                Toast.makeText(getApplicationContext(),"Your drawing was successfully saved",
                        Toast.LENGTH_SHORT).show();
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
            imageUri = data.getData();
            Log.i("onActivityResult", "Result OK and PHOTO_INTENT caller " +imageUri.getPath());
            //Drawable drawable = Drawable.createFromPath(imageUri.toString());
            //findViewById(R.id.draw_canvas).setBackground(drawable);
            DrawIt drawIt = findViewById(R.id.draw_canvas);
            drawIt.setImage(getContentResolver(), imageUri);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        DrawIt drawIt = findViewById(R.id.draw_canvas);
        outState.putSerializable("Path Array", drawIt.getArrayPaths());
        outState.putSerializable("Shapes Array", drawIt.getArrayShapes());
        outState.putSerializable("Color Array", drawIt.getArrayColors());
        outState.putSerializable("Stroke Array", drawIt.getArrayStrokes());
        if(imageUri != null) {
            outState.putString("Image Background", imageUri.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        DrawIt drawIt = findViewById(R.id.draw_canvas);
        drawIt.setArrayPaths(savedInstanceState.getSerializable("Path Array"));
        drawIt.setArrayColors(savedInstanceState.getSerializable("Color Array"));
        drawIt.setArrayShapes(savedInstanceState.getSerializable("Shapes Array"));
        drawIt.setArrayStrokes(savedInstanceState.getSerializable("Stroke Array"));
        String imageUriString = savedInstanceState.getString("Image Background");

        if (imageUriString != null){ //allow for screen rotation with no image selected
            Uri uri = Uri.parse(imageUriString);
            drawIt.setImage(getContentResolver(), uri);
        }
    }
}
