package edu.apsu.csci.games.drawit;

import android.content.Intent;
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
