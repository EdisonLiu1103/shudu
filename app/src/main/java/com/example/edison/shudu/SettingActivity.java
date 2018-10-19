package com.example.edison.shudu;

import android.preference.PreferenceActivity;
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
}
