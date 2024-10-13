package com.example.extrawidgetspractice1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView switchText;
    Switch aSwitch;
    EditText textDisplay;
    Button button;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchText = findViewById(R.id.id_text_switch);
        aSwitch = findViewById(R.id.id_switch);
        textDisplay = findViewById(R.id.id_displaytext);
        button = findViewById(R.id.id_button);
        seekBar = findViewById(R.id.id_seekbar);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){

                }
            }
        });{



    }



    }

}