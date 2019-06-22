package com.abror.diplom.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.abror.diplom.R;
import com.abror.diplom.databinding.ActivityAboutDeveloperBinding;

public class AboutDeveloperActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAboutDeveloperBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_developer);

        binding.fb.setOnClickListener(this);
        binding.tw.setOnClickListener(this);
        binding.insta.setOnClickListener(this);
        binding.github.setOnClickListener(this);
        binding.linked.setOnClickListener(this);
        binding.google.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.particleView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.particleView.pause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/abbasov.abror")));
                break;
            case R.id.tw:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/abrorabbasov")));
                break;
            case R.id.insta:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/_abror_/")));
                break;
            case R.id.github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Abror96")));
                break;
            case R.id.linked:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/abror-abbasov/")));
                break;
            case R.id.google:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:abbasov894@gmail.com"));
                startActivity(Intent.createChooser(emailIntent, "Send feedback"));
                break;
        }
    }
}
