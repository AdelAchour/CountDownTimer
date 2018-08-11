package com.prod.adelachour.testapplication;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button start, increment;
    TextView timeRemaining, cpt;
    ConstraintLayout layoutUI;
    CountDownTimer countDownTimer;
    int cptInt = 0;
    long timeLeftMS = 60000; //1 min
    Runnable timeRun;

    Handler handlerTic = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            timeRemaining = (TextView)findViewById(R.id.timeRemainingTV);

            int minute = (int)timeLeftMS / 60000;
            int seconde = (int)timeLeftMS % 60000 / 1000;

            String timeLeftText;

            timeLeftText = "" + minute;
            timeLeftText += ":";
            if (seconde<10) timeLeftText += "0";
            timeLeftText += seconde;

            timeRemaining.setText(timeLeftText);

            //timeRemaining.setText("Hey done!");
        }
    };

    Handler handlerFinish = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            timeRemaining.setText("Done!");
            layoutUI.setBackgroundColor(Color.RED);
        }
    };

    Handler handlerDanger = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            timeRemaining.setTextColor(Color.RED);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button)findViewById(R.id.timeButton);
        increment = (Button)findViewById(R.id.buttonIncrement);
        cpt = (TextView)findViewById(R.id.cptTV);
        layoutUI = (ConstraintLayout)findViewById(R.id.layoutUI);





        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer = new CountDownTimer(timeLeftMS, 1000) {
                    @Override
                    public void onTick(long l) {
                        timeLeftMS = l;
                        if (timeLeftMS == 10000){
                            handlerDanger.sendEmptyMessage(0);
                        }
                        handlerTic.sendEmptyMessage(0);
                    }

                    @Override
                    public void onFinish() {
                        handlerFinish.sendEmptyMessage(0);
                    }
                }.start();
            }
        });


        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cptInt++;
                cpt.setText(cptInt+" points");
            }
        });




    }
}
