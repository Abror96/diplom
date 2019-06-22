package com.abror.diplom.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.abror.diplom.R;
import com.abror.diplom.utils.PrefConfig;

public class SplashScreen extends AppCompatActivity {

    private PrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        prefConfig = new PrefConfig(this);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Intent intent;
                        if (!prefConfig.getLoginStatus()) {
                            intent = new Intent(getApplicationContext(), AuthActivity.class);
                        } else {
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }
                },
                3000);

    }
}
