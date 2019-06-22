package com.abror.diplom.mvp.presenters;

import android.content.Context;

import com.abror.diplom.DBHelper.model.Student;
import com.abror.diplom.DBHelper.repository.StudentRepository;
import com.abror.diplom.mvp.contracts.AuthContract;

public class AuthPresenterImpl implements AuthContract.Presenter, AuthContract.Interactor.OnFinishedListener {

    private AuthContract.View view;
    private AuthContract.Interactor interactor;
    private Context context;

    public AuthPresenterImpl(AuthContract.View view, Context context) {
        this.view = view;
        this.context = context;
        interactor = new StudentRepository(context);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onAuthClicked(String login, String password) {
        interactor.authStudent(this, login, password);
    }

    @Override
    public void onFinished(Student student) {
        if (view != null) {
            view.onAuthSuccess(student);
        }
    }

    @Override
    public void onFailure(String message) {
        if (view != null) {
            view.showSnackbar(message);
        }
    }
}
