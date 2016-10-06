package com.espaciounido.unadcalendar.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.espaciounido.unadcalendar.data.repo.FirebaseReferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by MyMac on 6/09/16.
 */
public class PreferenceModel implements Preference {

    private SharedPreferences SP;

    public PreferenceModel(Context context) {
        SP = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public String getName() {
        return SP.getString("user_name", "");
    }

    @Override
    public String getEmail() {
        return SP.getString("user_email", "");
    }

    @Override
    public String getPeriod() {
        return SP.getString("user_period", "");
    }

    @Override
    public String getCarrera() {
        return SP.getString("user_carrera", "");
    }

    @Override
    public String getCEAD() {
        return SP.getString("user_cead", "");
    }

    @Override
    public int getNotiActiveFrequency() {
        return Integer.parseInt(SP.getString("active_frequency", "8"));
    }

    @Override
    public int getReplyFrequency() {
        return Integer.parseInt(SP.getString("reply_frequency", "-1"));
    }

    @Override
    public String tokenNotification() {
        return SP.getString("token_notification", "");
    }

    @Override
    public void saveRemote() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String token = FirebaseInstanceId.getInstance().getToken();
            FirebaseReferences.getUser().child(user.getUid()).setValue(
                    new UserData(getName(), getPeriod(), getEmail(), getCarrera(), getCEAD(), token)
            );
        }
    }


}
