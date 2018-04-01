package com.example.carlosyang.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
    private RelativeLayout rl_main;
    private Button btn_reset;
    private CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rl_main= (RelativeLayout) findViewById(R.id.rl_main);
        btn_reset= (Button) findViewById(R.id.btn_reset);
        customView=new CustomView(this);
        rl_main.addView(customView);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.resetView();
            }
        });
    }
}
