package com.example.intenttesting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity {

    Button subButton;
    EditText subEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        subButton = findViewById(R.id.subButton);
        subEditText = findViewById(R.id.subEditText);

        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = subEditText.getText().toString();
                Log.d(MainActivity.KEY, "sub" + s);
                Intent intent = new Intent();
                intent.putExtra(MainActivity.KEY, s);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });


    }
}