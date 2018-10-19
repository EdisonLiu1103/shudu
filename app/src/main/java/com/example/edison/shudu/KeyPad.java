package com.example.edison.shudu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class KeyPad extends AppCompatActivity {

    public KeyPad(GameActivity gameActivity, int[] tiles, PuzzleView puzzleView) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_pad);
    }
}
