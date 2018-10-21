package com.example.edison.shudu;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;

public class Music {
    private static MediaPlayer mp = null;
    public static void play(Context context, int resource){
        stop(context);
        if(SettingActivity.getMusic(context)){
            mp = MediaPlayer.create(context, resource);
            mp.setLooping(true);
            mp.start();
        }
    }

    public static void stop(Context context){
        if(mp != null){
            mp.stop();
            mp.release();
            mp = null;
        }
    }
}
