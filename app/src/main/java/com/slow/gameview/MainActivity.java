package com.slow.gameview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private int n = 1;
    private static final float MAX_BUG_SPEED_DP_PER_S = 300f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        JoystickView joystick = (JoystickView) findViewById(R.id.joystickView);
//        TextView textView = findViewById(R.id.textShow);
//            joystick.setFixedCenter(false); // set up auto-define center


        final MyView myView = (MyView) findViewById(R.id.myView);

        final String angleNoneString = getString(R.string.angle_value_none);
        final String angleValueString = getString(R.string.angle_value);
        final String offsetNoneString = getString(R.string.offset_value_none);
        final String offsetValueString = getString(R.string.offset_value);
        Button btnSpeed = findViewById(R.id.btnSpeed);
        Joystick joystick = (Joystick) findViewById(R.id.joystick);
        joystick.setJoystickListener(new JoystickListener() {
            @Override
            public void onDown() {
            }

            @Override
            public void onDrag(float degrees, float offset) {
                btnSpeed.setOnTouchListener(speedListener);
                myView.setVelocity(
                        (float) Math.cos(degrees * Math.PI / 180f) * offset * MAX_BUG_SPEED_DP_PER_S * n,
                        -(float) Math.sin(degrees * Math.PI / 180f) * offset * MAX_BUG_SPEED_DP_PER_S * n);
            }

            @Override
            public void onUp() {

                myView.setVelocity(0, 0);
            }
        });
    }

    public View.OnTouchListener speedListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {n = 2;}
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP ){n = 1;}

            return false;
        }
    };


}