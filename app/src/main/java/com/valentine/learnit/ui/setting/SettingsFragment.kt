package com.valentine.learnit.ui.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.valentine.learnit.R


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_screen, rootKey)
        //val feedBackPref: Preference? = findPreference(getString(R.string.feedback_pref))

    }

}