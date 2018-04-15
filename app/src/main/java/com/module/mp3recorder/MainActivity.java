package com.module.mp3recorder;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.module.mp3recorddemo.R;
import com.module.mp3recorder.audio.CzAudioRecord;
import com.module.mp3recorder.audio.Mp3Record;
import com.module.mp3recorder.listener.AudioRecordListener;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private CzAudioRecord mRecorder;
    private String filePath= Environment.getExternalStorageDirectory().getPath() + "/test.mp3";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createFile();
        mRecorder = new Mp3Record();
        mRecorder.setAudioPath(filePath);
        mRecorder.setAudioListener(new AudioRecordListener() {
            @Override
            public void onGetVolume(int volume) {
                Log.d(TAG, "onGetVolume: -->" + volume);
            }
        });
        Button startButton = (Button) findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecorder.startRecord();
            }
        });
        Button stopButton = (Button) findViewById(R.id.StopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecorder.stopRecord();
            }
        });
        Button btnPause = findViewById(R.id.btn_pause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecorder.onPause();
            }
        });
        Button btnResume = findViewById(R.id.btn_resume);
        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecorder.onResume();
            }
        });
    }

    private void createFile() {
        File file = new File(filePath);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecorder.stopRecord();
    }
}