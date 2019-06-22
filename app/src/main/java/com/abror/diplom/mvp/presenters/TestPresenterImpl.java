package com.abror.diplom.mvp.presenters;

import android.content.Context;
import android.util.Log;

import com.abror.diplom.DBHelper.model.TestResults;
import com.abror.diplom.DBHelper.repository.StudentRepository;
import com.abror.diplom.mvp.contracts.TestContract;

public class TestPresenterImpl implements TestContract.Presenter, TestContract.Interactor.OnFinishedListener {

    private TestContract.View view;
    private Context context;
    private TestContract.Interactor interactor;

    public TestPresenterImpl(TestContract.View view, Context context) {
        this.view = view;
        this.context = context;
        interactor = new StudentRepository(context);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onSaveResultClicked(int user_id, int score, String result) {
        TestResults testResults = new TestResults();
        testResults.setStudent_id(user_id);
        testResults.setScore(score);
        testResults.setResult(result);

        interactor.saveResult(this, testResults);
    }

    @Override
    public void onFinished() {
        if (view != null) {
            view.onSaveResultSuccess();
            Log.d("LOGGERR", "onFinished: test save success");
        }
    }

    @Override
    public void onFailure(String message) {
        if (view != null) {
            view.showSnackbar(message);
        }
    }
}
