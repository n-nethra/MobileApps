package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //5:15

    ImageView guacBowl1, guacBowl2, guacBowl3, guacBowl4, guacBowl5, guacBowl6, bowl1, bowl2, bowl3, bowl4, bowl5, bowl6, chipBowl1, chipBowl2, chipBowl3, chipBowl4, chipBowl5, chipBowl6;
    boolean gameRunning = false;
    Button startButton;
    TextView timeText, menuText;
    final long GAME_TIME_MS = 30000;
    LinearLayout linearLayout;
    Handler handler = new Handler();
    ImageView point;
    int time;
    int score;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeText = findViewById(R.id.id_timeText);
        startButton = findViewById(R.id.id_startbutton);
        linearLayout = findViewById(R.id.id_linear_layout);
        menuText = findViewById(R.id.id_menu_text);
        score = 0;
        bowl1 = findViewById(R.id.id_img_bowl1);
        bowl2 = findViewById(R.id.id_img_bowl2);
        bowl3 = findViewById(R.id.id_img_bowl3);
        bowl4 = findViewById(R.id.id_img_bowl4);
        bowl5 = findViewById(R.id.id_img_bowl5);
        bowl6 = findViewById(R.id.id_img_bowl6);

        guacBowl1 = findViewById(R.id.id_img_guacbowl1);
        guacBowl2 = findViewById(R.id.id_img_guacbowl2);
        guacBowl3 = findViewById(R.id.id_img_guacbowl3);
        guacBowl4 = findViewById(R.id.id_img_guacbowl4);
        guacBowl5 = findViewById(R.id.id_img_guacbowl5);
        guacBowl6 = findViewById(R.id.id_img_guacbowl6);

        chipBowl1 = findViewById(R.id.id_img_chipbowl1);
        chipBowl2 = findViewById(R.id.id_img_chipbowl2);
        chipBowl3 = findViewById(R.id.id_img_chipbowl3);
        chipBowl4 = findViewById(R.id.id_img_chipbowl4);
        chipBowl5 = findViewById(R.id.id_img_chipbowl5);
        chipBowl6 = findViewById(R.id.id_img_chipbowl6);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guacBowl1.setImageAlpha(0);
                chipBowl1.setImageAlpha(0);
                guacBowl2.setImageAlpha(0);
                chipBowl2.setImageAlpha(0);
                guacBowl3.setImageAlpha(0);
                chipBowl3.setImageAlpha(0);
                guacBowl4.setImageAlpha(0);
                chipBowl4.setImageAlpha(0);
                guacBowl5.setImageAlpha(0);
                chipBowl5.setImageAlpha(0);
                guacBowl6.setImageAlpha(0);
                chipBowl6.setImageAlpha(0);
                score = 0;
                startGame();
                menuText.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.INVISIBLE);
                startButton.setClickable(false);
            }
        });

    }
    private void startGame() {
        gameRunning = true;
        time = (int) GAME_TIME_MS;
        startTimer();
        handler.postDelayed(randomMoles, 2000);
    }

    public void startTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (gameRunning && time > 0) {
                    time -= 1000;
                    timeText.setText(String.valueOf(time / 1000));
                    startTimer();
                } else {
                    endGame();
                }
            }
        }, 1000);
    }

    private final Runnable randomMoles = new Runnable() {
        @Override
        public void run() {
            if (gameRunning && time > 0) {
                int randomMole = (int) (Math.random() * 6) + 1;
                showMole(randomMole);
                handler.postDelayed(this, (long)Math.random()*5000 + 3000);
            }
        }
    };

    private void showMole(int bowlNumber) {
        Log.d("TAG_SHOWMOLE", "METHOD");
        ImageView guacBowl = getGuacBowl(bowlNumber);
        ImageView chipBowl = getChipBowl(bowlNumber);
        ScaleAnimation scaleUpAnimation =  new ScaleAnimation(0.5f,1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ScaleAnimation scaleDownAnimation = new ScaleAnimation(1.0f,0.5f, 1.0f,0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleUpAnimation.setDuration(800);
        scaleDownAnimation.setDuration(800);
        scaleDownAnimation.setStartOffset(2000);
        AnimationSet scaleAnimation = new AnimationSet(false);
        scaleAnimation.addAnimation(scaleUpAnimation);
        scaleAnimation.addAnimation(scaleDownAnimation);

        ScaleAnimation chipDisappearAnimation = new ScaleAnimation(1.0f,0.5f, 1.0f,0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        Boolean isSpecial = false;

        int x = (int)(Math.random()*100)+1;
        if (x < 20){
            guacBowl.setImageResource(R.drawable.shinybowl);
            isSpecial = true;
            Log.d("TAG_SPECIAL", "special");
        }
        else{
            guacBowl.setImageResource(R.drawable.dipguacbowl);
            Log.d("TAG_SPECIAL", "not special");
        }

        Log.d("TAG_SHOWMOLE", guacBowl.getImageAlpha() + "");
        if (guacBowl.getImageAlpha() == 0) {
            guacBowl.setImageAlpha(255);
            Log.d("TAG_SFDJ", "SHOWS");
            guacBowl.startAnimation(scaleAnimation);
            guacBowl.setClickable(true);
            Boolean finalIsSpecial = isSpecial;
            guacBowl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guacBowl.setClickable(false);
                    guacBowl.setImageAlpha(0);
                    chipBowl.setImageAlpha(255);
                    if (finalIsSpecial) {
                        time += 10000;
                    }
                    else {
                        addScore();
                    }
                    chipBowl.startAnimation(chipDisappearAnimation);
                    handler.postDelayed(() -> {
                        chipBowl.setImageAlpha(0);
                    }, 2000);
                }
            });
            handler.postDelayed(() -> {
                guacBowl.setImageAlpha(0);
            }, 3000);
        }
    }

    private ImageView getGuacBowl(int bowlNumber) {
        switch (bowlNumber) {
            case 1:
                return guacBowl1;
            case 2:
                return guacBowl2;
            case 3:
                return guacBowl3;
            case 4:
                return guacBowl4;
            case 5:
                return guacBowl5;
            case 6:
                return guacBowl6;
            default:
                return null;
        }
    }

    private ImageView getChipBowl(int bowlNumber) {
        switch (bowlNumber) {
            case 1:
                return chipBowl1;
            case 2:
                return chipBowl2;
            case 3:
                return chipBowl3;
            case 4:
                return chipBowl4;
            case 5:
                return chipBowl5;
            case 6:
                return chipBowl6;
            default:
                return null;
        }
    }

    public void addScore(){
        score ++;
        linearLayout.bringToFront();
        if (score < 20) {
            point = new ImageView(this);
            point.setId(View.generateViewId());
            point.setImageResource(R.drawable.chip);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            point.setLayoutParams(layoutParams);
            linearLayout.addView(point);
        }
    }

    private void endGame() {
        gameRunning = false;
        linearLayout.removeAllViews();
        timeText.setText("Game Over");
        menuText.setText("Score: " + score);
        menuText.setVisibility(View.VISIBLE);
        startButton.setText("Play Again?");
        startButton.setVisibility(View.VISIBLE);
        startButton.setClickable(true);
        linearLayout.removeAllViews();
    }

}