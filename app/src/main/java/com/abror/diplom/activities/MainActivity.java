package com.abror.diplom.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.abror.diplom.DBHelper.model.Student;
import com.abror.diplom.R;

import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.abror.diplom.mvp.contracts.MainContract;
import com.abror.diplom.mvp.presenters.MainPresenterImpl;
import com.abror.diplom.utils.PrefConfig;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.abror.diplom.utils.Constants.groups;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, MainContract.View {

    private PrefConfig prefConfig;
    private ConstraintLayout main_view;
    private MainContract.Presenter presenter;

    private ImageView student_image;
    private TextView student_name;
    private TextView student_group;
    private Student student;
    private View nav_header_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefConfig = new PrefConfig(this);
        presenter = new MainPresenterImpl(this, this);
        main_view = findViewById(R.id.main_view);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        findViewById(R.id.lecture_btn).setOnClickListener(this);
        findViewById(R.id.labs_btn).setOnClickListener(this);
        findViewById(R.id.presentations_btn).setOnClickListener(this);
        findViewById(R.id.glossary_btn).setOnClickListener(this);
        findViewById(R.id.test_btn).setOnClickListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        nav_header_view = navView.getHeaderView(0);

        if (getIntent().getSerializableExtra("student") != null) {
            student = (Student) getIntent().getSerializableExtra("student");
            setStudentInfo(student.getFull_name(), student.getGroup_id(), student.getImage());

        } else {
            presenter.onGetStudentClicked(prefConfig.getUserLogin());
        }
    }

    private void setStudentInfo(String full_name, Integer group_id, byte[] image) {
        Log.d("LOGGERR", "User Id: " + student.getId());
        student_image = nav_header_view.findViewById(R.id.student_photo);
        student_name = nav_header_view.findViewById(R.id.student_name);
        student_group = nav_header_view.findViewById(R.id.student_group);

        student_name.setText(full_name);
        student_group.setText("Группа " + groups[group_id]);

        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        student_image.setImageBitmap(bmp);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_results) {
            startActivity(new Intent(MainActivity.this, StudentResultsActivity.class));
        } else if (id == R.id.nav_exit) {
            prefConfig.setLoginStatus(false);
            Intent intent = new Intent(this, AuthActivity.class);
            finishAffinity();
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutDeveloperActivity.class));
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lecture_btn:
                startActivity(new Intent(MainActivity.this, LecturesActivity.class));
                break;
            case R.id.labs_btn:
                startActivity(new Intent(MainActivity.this, LabsActivity.class));
                break;
            case R.id.presentations_btn:
                startActivity(new Intent(MainActivity.this, PresentationsActivity.class));
                break;
            case R.id.glossary_btn:
                startActivity(new Intent(MainActivity.this, GlossaryActivity.class));
                break;
            case R.id.test_btn:
                startActivity(new Intent(MainActivity.this, TestingActivity.class));
                break;
        }
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(main_view, message, Snackbar.LENGTH_LONG).show();
        if (message.equals("Студент не найден")) {
            prefConfig.setLoginStatus(false);
            Intent intent = new Intent(this, AuthActivity.class);
            finishAffinity();
            startActivity(intent);
        }
    }

    @Override
    public void onGetStudentSuccess(Student student) {
        this.student = student;
        setStudentInfo(student.getFull_name(), student.getGroup_id(), student.getImage());
    }
}
