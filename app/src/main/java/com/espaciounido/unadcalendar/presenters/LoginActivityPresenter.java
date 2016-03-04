package com.espaciounido.unadcalendar.presenters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.interfaces.views.ILoginActivity;
import com.espaciounido.unadcalendar.interfaces.presenters.ILoginActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by raalzate on 22/02/2016.
 */
public class LoginActivityPresenter implements ILoginActivityPresenter {

    private final ILoginActivity iLoginActivity;
    private final Activity activity;

    private UserLoginTask mAuthTask = null;


    public LoginActivityPresenter(ILoginActivity iLoginActivity, Activity activity) {
        this.iLoginActivity = iLoginActivity;
        this.activity = activity;
    }

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "alzategomez.raul@gmail.com:123456", "bar@example.com:world"
    };

    @Override
    public boolean isAuthTask() {
        return mAuthTask != null;
    }

    @Override
    public void loadContacts(Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ADDRESS));
            cursor.moveToNext();
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(activity,
                        android.R.layout.simple_dropdown_item_1line, emails);
        iLoginActivity.setAutoCompleteEmail(adapter);
    }

    @Override
    public boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (activity.checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (activity.shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            iLoginActivity.showPermissionRationale(new View.OnClickListener() {
                @Override
                @TargetApi(Build.VERSION_CODES.M)
                public void onClick(View v) {
                    activity.requestPermissions(new String[]{READ_CONTACTS}, ILoginActivity.REQUEST_READ_CONTACTS);
                }
            });

        } else {
            activity.requestPermissions(new String[]{READ_CONTACTS}, ILoginActivity.REQUEST_READ_CONTACTS);

        }
        return false;
    }

    @Override
    public Loader<Cursor> getContactsContract() {
        return new CursorLoader(activity,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }


    @Override
    public void attemptLoginEmailAndPassword(AutoCompleteTextView emailView, EditText passwordView) {

        if (isAuthTask()) {
            return;
        }

        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(activity.getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(activity.getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(activity.getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            iLoginActivity.showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }


    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                iLoginActivity.loginPass();
            } else {
                iLoginActivity.showProgress(false);
                iLoginActivity.errorLoginPass();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            iLoginActivity.showProgress(false);
        }
    }
}
