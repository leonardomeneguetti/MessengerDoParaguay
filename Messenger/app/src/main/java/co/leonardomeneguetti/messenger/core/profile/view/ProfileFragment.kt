package co.leonardomeneguetti.messenger.core.profile.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import co.leonardomeneguetti.messenger.R

class ProfileFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}