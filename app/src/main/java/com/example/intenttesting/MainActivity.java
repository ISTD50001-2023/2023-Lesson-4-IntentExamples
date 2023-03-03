package com.example.intenttesting;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView testTextView;

    Button testButtonGallery;

    ImageView testImageView;
    Button testButtonActivity;

    Button testButtonCamera;

    public static String KEY = "RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testTextView = findViewById(R.id.testTextView);
        testButtonGallery = findViewById(R.id.testButtonGallery);
        testImageView = findViewById(R.id.testImageView);
        testButtonActivity = findViewById(R.id.testButtonActivity);
        testButtonCamera = findViewById(R.id.testButtonCamera);


        final ActivityResultLauncher<Intent> launcherGallery = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if( result.getResultCode() == Activity.RESULT_OK
                        && result.getData() != null){
                            Uri photoUri = result.getData().getData();
                            testImageView.setImageURI(photoUri);

                        }
                    }
                }
        );

        final ActivityResultLauncher<Intent> launcherActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Bundle b = result.getData().getExtras();
                        String s = b.getString(KEY);
                        Log.d(MainActivity.KEY, "main" + s);
                        testTextView.setText(s);
                    }
                }
        );

        ActivityResultLauncher<Intent> launcherCamera = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Bundle b = result.getData().getExtras();
                        Bitmap thumbnail = (Bitmap) b.get("data");
                        testImageView.setImageBitmap(thumbnail);
                    }
                }
        );

        testButtonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcherGallery.launch(intent);
            }
        });

        testButtonActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new  Intent ( MainActivity.this, SubActivity.class);
                launcherActivity.launch(intent);

            }
        });

        testButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launcherCamera.launch(intent);

            }
        });
    }


}