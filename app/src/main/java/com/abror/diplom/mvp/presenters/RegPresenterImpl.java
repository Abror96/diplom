package com.abror.diplom.mvp.presenters;

import android.content.Context;
import android.util.Log;

import com.abror.diplom.DBHelper.model.Student;
import com.abror.diplom.DBHelper.repository.StudentRepository;
import com.abror.diplom.mvp.contracts.RegContract;
import com.abror.diplom.utils.PrefConfig;

import java.util.Date;

public class RegPresenterImpl implements RegContract.Presenter, RegContract.Interactor.OnFinishedListener {

    private RegContract.View view;
    private RegContract.Interactor interactor;
    private Context context;
    PrefConfig prefConfig;

    public RegPresenterImpl(RegContract.View view, Context context) {
        this.view = view;
        this.context = context;
        interactor = new StudentRepository(context);
        prefConfig = new PrefConfig(context);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onAddStudentClicked(Date creationDate, String login, String password, String full_name, int group_id, int course, byte[] photo) {
        Student student = new Student();
        student.setLogin(login);
        student.setPassword(password);
        student.setFull_name(full_name);
        student.setGroup_id(group_id);
        student.setCourse(course);
        student.setImage(photo);

        interactor.addStudent(this, student);
    }

    @Override
    public void onFinished(Student student) {
        if (view != null) {
            view.onAddStudentSuccess(student);

            prefConfig.setLoginStatus(true);
            prefConfig.setUserLogin(student.getLogin());
            prefConfig.setUserId(student.getId());
        }
    }

    @Override
    public void onFailure(String message) {
        if (view != null) {
            view.showSnackbar(message);
        }
    }
}
