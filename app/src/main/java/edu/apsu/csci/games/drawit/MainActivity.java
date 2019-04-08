package edu.apsu.csci.games.drawit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawIt drawView;

    private static final int PHOTO_INTENT = 1;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawView = (DrawIt) findViewById(R.id.draw_canvas);

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

        //thanks to stackoverflow user JRowan for canvas to bitmap save and storage instructions
        //stackoverflow.com/questions/18676311/android-app-how-to-save-a-bitmap-drawing-on-canvas-as-image-check-code/18676403
        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                File folder = new File(Environment.getExternalStorageDirectory().toString());
                boolean success = false;
                if (!folder.exists()) {
                    success = folder.mkdirs();
                }

                Toast.makeText(getApplicationContext(), "Folder found : " + success,
                        Toast.LENGTH_SHORT).show();
                File file = new File(Environment.getExternalStorageDirectory().toString() + "/DrawIt.png");

                if (!file.exists()) {
                    try {
                        success = file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Toast.makeText(getApplicationContext(), "Image saved in file '/DrawIt.png' : " + success,
                        Toast.LENGTH_SHORT).show();


                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(file);

                    System.out.println(ostream);

                    DrawIt drawIt = findViewById(R.id.draw_canvas);

                    Bitmap well = drawView.getBitmap();
                    Bitmap save = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
                    Paint paint = new Paint();
                    paint.setColor(Color.WHITE);
                    Canvas now = new Canvas(save);
                    now.drawRect(new Rect(0, 0, 320, 480), paint);
                    now.drawBitmap(well, new Rect(0, 0, well.getWidth(), well.getHeight()), new Rect(0, 0, 320, 480), null);

                    if (save == null) {
                        Toast.makeText(getApplicationContext(), "NULL bitmap save\n",
                                Toast.LENGTH_SHORT).show();
                    }
                    save.compress(Bitmap.CompressFormat.PNG, 100, ostream);


                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Null error", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "File error", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "IO error", Toast.LENGTH_SHORT).show();
                }
                // call DrawIt clear();
                // can later implement MediaScannerConnection to show saved bitmap in gallery
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
