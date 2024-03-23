package com.toantd2000.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextView;
    private MaterialButton startButton;
    private MaterialButton stopButton;
    private MaterialButton clearButton;
    private NLSReceiver nlsReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.text);

        nlsReceiver = new NLSReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.toantd2000.myapplication.UPDATE");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(nlsReceiver,filter, RECEIVER_EXPORTED);
        }

        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlsReceiver);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == stopButton.getId()) {
            stopService(new Intent(this, NLService.class));
        } else if (view.getId() == startButton.getId()) {
            startService(new Intent(this, NLService.class));
        } else if (view.getId() == clearButton.getId()) {
            mTextView.setText("");
        }
    }

    class NLSReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String title = intent.getExtras().getString("title", "--");
            String text = intent.getExtras().getString("text", "--");
            mTextView.setText(String.format("%s\n |%s | %s\n-----------------------------------------", mTextView.getText(), title, text));
        }
    }
}