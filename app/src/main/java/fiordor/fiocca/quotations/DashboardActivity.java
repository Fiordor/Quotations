package fiordor.fiocca.quotations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    private final String NO_VALUE = "no value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getString("db", NO_VALUE).equals(NO_VALUE))
            pref.edit().putString("db", "room").apply();
        if (pref.getString("http", NO_VALUE).equals(NO_VALUE))
            pref.edit().putString("http", "GET").apply();
        if (pref.getString("language", NO_VALUE).equals(NO_VALUE))
            pref.edit().putString("language", "en").apply();
    }

    public void dashUnifiedCallback(View view) {

        Intent intent = null;
        switch (view.getId()) {
            case R.id.btGet : intent = new Intent(this, QuotationActivity.class); break;
            case R.id.btFavourite : intent = new Intent(this, FavouriteActivity.class); break;
            case R.id.btSettings : intent = new Intent(this, SettingsActivity.class); break;
            case R.id.btAbout : intent = new Intent(this, AboutActivity.class); break;
            default: return;
        }
        startActivity(intent);
    }
}