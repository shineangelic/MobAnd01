package it.eng.moband;

import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

public class OpzioniActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.opzioni);
    }

//TODO vedere la reference implementation su https://developer.android.com/reference/android/preference/PreferenceActivity.html


//    SeekBar sampleSizeSeekBar = (SeekBar) findViewById(R.id.sampleSizeSeekBar);
//    int val = sampleSizeSeekBar.getProgress();
  /*  SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
    editor.putString("name", "Elena");
    editor.putInt("idName", 12);
    editor.commit();
    final int sampleSizeSeekBarCorrection = 1;

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        val = progress + sampleSizeSeekBarCorrection; //e.g., to convert SeekBar value of 95 to real value of 100
    }
*/
}
