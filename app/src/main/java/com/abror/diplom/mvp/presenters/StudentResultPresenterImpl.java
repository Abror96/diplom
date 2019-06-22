package com.abror.diplom.mvp.presenters;

import android.content.Context;

import com.abror.diplom.DBHelper.model.TestResults;
import com.abror.diplom.DBHelper.repository.StudentRepository;
import com.abror.diplom.mvp.contracts.StudentResultContract;

import java.util.List;

public class StudentResultPresenterImpl implements StudentResultContract.Presenter, StudentResultContract.Interactor.OnFinishedListener {

    private StudentResultContract.View view;
    private Context context;
    private StudentResultContract.Interactor interactor;

    public StudentResultPresenterImpl(StudentResultContract.View view, Context context) {
        this.view = view;
        this.context = context;
        interactor = new StudentRepository(context);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onGetResultsCalled(int user_id) {
        interactor.getResults(this, user_id);
    }

    @Override
    public void onFinished(List<TestResults> resultsList) {
        if (view != null) {
            view.onGetResultsSuccess(resultsList);
        }
    }

    @Override
    public void onFailure(String message) {
        if (view != null) {
            view.showSnackbar(message);
        }
    }
}
