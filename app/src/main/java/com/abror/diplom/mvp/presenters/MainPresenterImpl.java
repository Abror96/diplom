package com.abror.diplom.mvp.presenters;

import android.content.Context;

import com.abror.diplom.DBHelper.model.Student;
import com.abror.diplom.DBHelper.repository.StudentRepository;
import com.abror.diplom.mvp.contracts.MainContract;

public class MainPresenterImpl implements MainContract.Presenter, MainContract.Interactor.OnFinishedListener {

    private MainContract.View view;
    private Context context;
    private MainContract.Interactor interactor;

    public MainPresenterImpl(MainContract.View view, Context context) {
        this.view = view;
        this.context = context;
        interactor = new StudentRepository(context);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onGetStudentClicked(String login) {
        interactor.getStudentByLogin(this, login);
    }

    @Override
    public void onFinished(Student student) {
        if (view != null) {
            view.onGetStudentSuccess(student);
        }
    }

    @Override
    public void onFailure(String message) {
        if (view != null) {
            view.showSnackbar(message);
        }
    }
}
