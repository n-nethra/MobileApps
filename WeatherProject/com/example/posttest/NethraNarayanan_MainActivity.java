package com.example.posttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView nameText;
    RadioGroup radioGroup;
    Button runButton;
    Button launchButton;
    ConstraintLayout constraintLayout;
    String choice;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = findViewById(R.id.id_main_name);
        radioGroup = findViewById(R.id.id_main_radioGroup);
        runButton = findViewById(R.id.id_main_runButton);
        launchButton = findViewById(R.id.id_main_lauchButton);
        constraintLayout = findViewById(R.id.id_main_constraintLayout);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.id_rb_toast){
                    choice = "toast";
                }
                else if (checkedId == R.id.id_rb_color){
                    choice = "color";
                }
                else if (checkedId == R.id.id_rb_uppercase){
                    choice = "uppercase";
                    Log.d("TAG_CHOICE", choice);
                }
            }
        });

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choice.equals("toast")){
                    Toast.makeText(MainActivity.this, "Toast Selected. Hi!", Toast.LENGTH_SHORT).show();
                }
                if (choice.equals("color")){
                   int x = (int) (Math.random()*3+1);
                   if (x == 1){
                       constraintLayout.setBackgroundColor(Color.MAGENTA);
                   }
                   if (x == 2){
                       constraintLayout.setBackgroundColor(Color.GREEN);
                   }
                   if (x == 3){
                       constraintLayout.setBackgroundColor(Color.YELLOW);
                   }
                }
                if (choice.equals("uppercase")){
                    nameText.setText(nameText.getText().toString().toUpperCase());
                }
            }
        });

        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("CHOICE", choice);
                startActivity(i);
            }
        });



    }
}