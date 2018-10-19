package com.example.edison.shudu;

import android.content.Context;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingActivity extends PreferenceActivity {

    private static final String OPT_MUSIC = "music";
    private static final String OPT_MUSIC_DEF = "true";
    private static final String OPT_HINTS = "hints";
    private static final String OPT_HINTS_DEF = "true";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

    }

    public static boolean getMusic(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(OPT_MUSIC, Boolean.parseBoolean(OPT_MUSIC_DEF));

    }

    public static boolean getHints(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(OPT_HINTS, Boolean.parseBoolean(OPT_HINTS_DEF));
    }
}
