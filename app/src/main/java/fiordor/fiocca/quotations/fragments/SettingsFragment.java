package fiordor.fiocca.quotations.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import fiordor.fiocca.quotations.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.preference_settings, rootKey);

    }
}
