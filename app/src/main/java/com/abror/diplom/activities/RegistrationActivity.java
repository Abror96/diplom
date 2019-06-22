package com.abror.diplom.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.abror.diplom.DBHelper.model.Student;
import com.abror.diplom.R;
import com.abror.diplom.databinding.ActivityRegistrationBinding;
import com.abror.diplom.mvp.contracts.RegContract;
import com.abror.diplom.mvp.presenters.RegPresenterImpl;
import com.abror.diplom.utils.BottomCourseFragment;
import com.abror.diplom.utils.BottomGroupFragment;
import com.abror.diplom.utils.IOnBtnPressed;
import com.abror.diplom.utils.PrefConfig;
import com.abror.diplom.utils.RequestPermissionHandler;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.abror.diplom.utils.Constants.courses;
import static com.abror.diplom.utils.Constants.groups;
import static com.abror.diplom.utils.Constants.initProgressDialog;

public class RegistrationActivity extends AppCompatActivity implements IOnBtnPressed, RegContract.View {

    private ActivityRegistrationBinding binding;
    private BottomGroupFragment bottomGroupFragment;
    private BottomCourseFragment bottomCourseFragment;
    private int group_id = -1;
    private int course_id = -1;
    private PrefConfig prefConfig;
    private Bitmap selected_photo;
    private RequestPermissionHandler mRequestPermissionHandler;
    private ProgressDialog progressDialog;

    private RegContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);

        presenter = new RegPresenterImpl(this, this);
        prefConfig = new PrefConfig(this);
        mRequestPermissionHandler = new RequestPermissionHandler();
        progressDialog = initProgressDialog(this, "Идёт загрузка");

        BottomGroupFragment.setBtnClickListener(this);
        binding.groupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomGroupFragment = BottomGroupFragment.getInstance();
                bottomGroupFragment.show(getSupportFragmentManager(), "group");
            }
        });

        BottomCourseFragment.setBtnClickListener(this);
        binding.courseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomCourseFragment = BottomCourseFragment.getInstance();
                bottomCourseFragment.show(getSupportFragmentManager(), "course");
            }
        });

        binding.sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = binding.etEmail.getText().toString().trim();
                String pwd = binding.etPassword.getText().toString().trim();
                String conf_pwd = binding.etConfirmPassword.getText().toString().trim();
                String full_name = binding.etName.getText().toString().trim();

                if (!login.isEmpty() && !pwd.isEmpty() && !conf_pwd.isEmpty() && !full_name.isEmpty()
                        && group_id != -1 && course_id != -1 && selected_photo != null) {
                    if (pwd.equals(conf_pwd)) {
                        try {
                            presenter.onAddStudentClicked(
                                    new Date(),
                                    login,
                                    pwd,
                                    full_name,
                                    group_id,
                                    course_id,
                                    new ConvertClassAsync().execute().get());
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Snackbar.make(binding.mainView, "Пароли должны совпадать", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    if (login.isEmpty()) binding.emailLayout.setError("Обязательное поле");
                    else binding.emailLayout.setErrorEnabled(false);

                    if (pwd.isEmpty()) binding.password1Layout.setError("Обязательное поле");
                    else binding.password1Layout.setErrorEnabled(false);

                    if (conf_pwd.isEmpty()) binding.password2Layout.setError("Обязательное поле");
                    else binding.password2Layout.setErrorEnabled(false);

                    if (full_name.isEmpty()) binding.nameLayout.setError("Обязательное поле");
                    else binding.nameLayout.setErrorEnabled(false);

                    if (group_id == -1) binding.groupLayout.setBackgroundResource(R.drawable.edittext_rounded_corners_error);
                    else binding.groupLayout.setBackgroundResource(R.drawable.edittext_rounded_corners);

                    if (course_id == -1) binding.courseLayout.setBackgroundResource(R.drawable.edittext_rounded_corners_error);
                    else binding.courseLayout.setBackgroundResource(R.drawable.edittext_rounded_corners);

                    Snackbar.make(binding.mainView, "Заполните все обязательные поля и выберите фото", Snackbar.LENGTH_LONG).show();
                }

            }
        });

        binding.studentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
    }

    @Override
    public void onGroupClicked(int position) {
        binding.etGroup.setText(groups[position]);
        group_id = position;
        bottomGroupFragment.dismiss();
    }

    @Override
    public void onCourseClicked(int position) {
        binding.etCourse.setText(courses[position]);
        course_id = position;
        bottomCourseFragment.dismiss();
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(binding.mainView, message, Snackbar.LENGTH_LONG).show();
        Log.d("LOGGERR", "registration error: " + message);
    }

    @Override
    public void onAddStudentSuccess(Student student) {
        Log.d("LOGGERR", "registration success, user id: " + student.getId());
        Snackbar.make(binding.mainView, "Регистрация прошла успешно", Snackbar.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("student", student);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Log.d("LOGGERR", "onAddStudentSuccess: ");
        startActivity(intent);
    }

    private void checkPermission() {
        mRequestPermissionHandler.requestPermission(this, 777,
                new RequestPermissionHandler.RequestPermissionListener() {
                    @Override
                    public void onSuccess() {
                        ImagePicker.create(RegistrationActivity.this)
                                .returnMode(ReturnMode.ALL)
                                .folderMode(true)
                                .single()
                                .includeVideo(false)
                                .start();
                    }
                    @Override
                    public void onFailed() {
                    }
                }
        );
    }

    // handling after permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            String filePath = image.getPath();
            if (filePath != null) {
                selected_photo = BitmapFactory.decodeFile(filePath);
                binding.studentImage.setImageBitmap(selected_photo);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("StaticFieldLeak")
    class ConvertClassAsync extends AsyncTask<Void, Void, byte[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected byte[] doInBackground(Void... voids) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            selected_photo.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            return stream.toByteArray();
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            progressDialog.dismiss();
        }
    }

}
