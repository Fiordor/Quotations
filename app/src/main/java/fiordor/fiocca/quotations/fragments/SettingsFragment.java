package fiordor.fiocca.quotations.fragments;

import android.os.Bundle;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import fiordor.fiocca.quotations.R;
import fiordor.fiocca.quotations.database.QuotationsDatabase;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.preference_settings, rootKey);

        Preference pref = getPreferenceManager().findPreference("db");

        pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if (newValue.toString().equals("sqlite")) {
                    QuotationsDatabase.destroyInstance();
                }

                return true;
            }
        });
    }
}
