package fiordor.fiocca.quotations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import fiordor.fiocca.quotations.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fr_settings, SettingsFragment.class, null)
                .commit();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        Toast.makeText(this, ("Valor: " + pref.getString("username", "no encontrado")), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}