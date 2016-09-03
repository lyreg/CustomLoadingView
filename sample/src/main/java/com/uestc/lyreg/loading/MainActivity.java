package com.uestc.lyreg.loading;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.uestc.lyreg.newton.NewtonCradleLoading;
import com.uestc.lyreg.newton.R;

public class MainActivity extends AppCompatActivity {

    private NewtonCradleLoading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupView();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupView() {
        loading = (NewtonCradleLoading) findViewById(R.id.loading);
    }

    public void onButtonClicked(View v) {
        Button button = (Button) v;

        String text = button.getText().toString();
        if(text.equals("start")) {
            button.setText("stop");
            loading.start();
        } else if (text.equals("stop")) {
            button.setText("start");
            loading.stop();
        }
    }
}
