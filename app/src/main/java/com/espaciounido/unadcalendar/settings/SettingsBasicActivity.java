package com.espaciounido.unadcalendar.settings;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.utils.TinyDB;
import com.espaciounido.unadcalendar.utils.Utils;


public class SettingsBasicActivity extends PreferenceActivity {

    private static final boolean ALWAYS_SIMPLE_PREFS = false;
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference.getKey().equals("user_email")) {
                String defaultEmail = preference.getContext().getResources()
                        .getString(R.string.pref_default_display_email);
                if (!defaultEmail.equals(stringValue)) {
                    if (!Utils.validateEmail(stringValue)) {
                        Toast.makeText(preference.getContext(), "El " + preference.getTitle() + " no es correcto.",
                                Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }
    };
    private PreferenceModel model;

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    private static boolean isSimplePreferences(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
                || !isXLargeTablet(context);
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_legacy);

        Toolbar actionbar = (Toolbar) findViewById(R.id.toolbar);
        actionbar.setTitle(R.string.title_activity_settingbasic);
        actionbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        model = new PreferenceModel(this);
        actionbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.saveRemote();
                finish();
            }
        });

        if (!TinyDB.getInstance(this).getBoolean("isShowNotifyFirtEvent")) {
            showNotifyFirtEvent();
            TinyDB.getInstance(this).putBoolean("isShowNotifyFirtEvent", true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            model.saveRemote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupSimplePreferencesScreen();
    }

    private void setupSimplePreferencesScreen() {
        if (!isSimplePreferences(this)) {
            return;
        }

        PreferenceCategory fakeHeader = new PreferenceCategory(this);
        addPreferencesFromResource(R.xml.pref_user);

        fakeHeader.setTitle(R.string.pref_header_general);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.pref_general);


        bindPreferenceSummaryToValue(findPreference("user_name"));
        bindPreferenceSummaryToValue(findPreference("user_cead"));
        bindPreferenceSummaryToValue(findPreference("user_email"));
        bindPreferenceSummaryToValue(findPreference("user_carrera"));
        bindPreferenceSummaryToValue(findPreference("user_period"));
    }

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this) && !isSimplePreferences(this);
    }


    private void showNotifyFirtEvent() {
        Toast.makeText(getBaseContext(), R.string.text_message_firtsettings, Toast.LENGTH_LONG).show();
    }

}
