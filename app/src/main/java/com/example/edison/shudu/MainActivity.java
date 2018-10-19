package com.example.edison.shudu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG="Sudoku";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View continueButton = this.findViewById(R.id.button1);
        continueButton.setOnClickListener( this);

        View newButton = this.findViewById(R.id.button2);
        newButton.setOnClickListener(this);

        View aboutButton = this.findViewById(R.id.button3);
        aboutButton.setOnClickListener(this);

        View exitButton = this.findViewById(R.id.button4);
        exitButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.button1:
                StartGame(GameActivity.DIFFICULTY_CONTINUE);
                break;
            case R.id.button2:
                openNewGameDialog();
                break;
            case R.id.button3:
                i = new Intent(this,About.class);
                startActivity(i);
                break;
            case R.id.button4:
                finish();
                break;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        Music.stop(this);
    }


    /**
     * 关于raw，及获取音乐文件的方法
     */
    @Override
    protected void onResume() {
        super.onResume();
        //Music.play(this,R.raw.);
        }

    private void openNewGameDialog(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.new_game_title)
                .setItems(R.array.difficulty, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StartGame(i);
                    }
                })
                .show();
    }

    private void StartGame(int i){
        Log.e(TAG,"clicked on" + i);
        //startActivity(new Intent(this,GraphicsActivity.class));
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.KEY_DIFFICULTY,i);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //super.OnCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }


    public boolean onOptionItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.settings:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
        }
        return false;
    }

}
