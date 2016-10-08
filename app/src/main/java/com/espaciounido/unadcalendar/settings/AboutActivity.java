package com.espaciounido.unadcalendar.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.espaciounido.unadcalendar.BuildConfig;
import com.espaciounido.unadcalendar.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.version)
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        version.setText(String.format("Versión %s (%s)", getVersion(), getCodeVersion()));
    }

    private String getVersion() {
        return BuildConfig.VERSION_NAME;
    }

    private int getCodeVersion() {
        return BuildConfig.VERSION_CODE;
    }

}
