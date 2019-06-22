package com.abror.diplom.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.abror.diplom.DBHelper.model.Student;
import com.abror.diplom.R;
import com.abror.diplom.databinding.ActivityAuthBinding;
import com.abror.diplom.mvp.contracts.AuthContract;
import com.abror.diplom.mvp.presenters.AuthPresenterImpl;
import com.abror.diplom.utils.PrefConfig;
import com.google.android.material.snackbar.Snackbar;

public class AuthActivity extends AppCompatActivity implements AuthContract.View {

    private ActivityAuthBinding binding;
    private AuthContract.Presenter presenter;
    private PrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);

        presenter = new AuthPresenterImpl(this, this);
        prefConfig = new PrefConfig(this);

        binding.noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AuthActivity.this, RegistrationActivity.class));
            }
        });
        binding.logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = binding.etEmail.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();
                if (!login.isEmpty() && !password.isEmpty()) {
                    presenter.onAuthClicked(login, password);
                } else {
                    if (login.isEmpty()) binding.emailLayout.setError("Обязательное поле");
                    else binding.emailLayout.setErrorEnabled(false);

                    if (password.isEmpty()) binding.passwordLayout.setError("Обязательное поле");
                    else binding.passwordLayout.setErrorEnabled(false);
                }
            }
        });
    }

    @Override
    public void onAuthSuccess(Student student) {
        Snackbar.make(binding.mainView, "Авторизация прошла успешно", Snackbar.LENGTH_LONG).show();
        prefConfig.setLoginStatus(true);
        prefConfig.setUserLogin(student.getLogin());
        prefConfig.setUserId(student.getId());
        Log.d("LOGGERR", "onAuthSuccess, user id: " + student.getId());

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("student", student);
        finishAffinity();
        startActivity(intent);
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(binding.mainView, message, Snackbar.LENGTH_LONG).show();
    }
}
