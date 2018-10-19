package com.example.edison.shudu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.security.PublicKey;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "sudoku";
    public static final String KEY_DIFFICULTY = "difficulty";
    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD = 2;

    protected static final int DIFFICULTY_CONTINUE = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    public String getTitleString(int i, int j) {
        return null;
    }
}
