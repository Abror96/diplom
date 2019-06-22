package com.abror.diplom.mvp.contracts;

import com.abror.diplom.DBHelper.model.TestResults;

import java.util.List;

public interface StudentResultContract {

    interface View {

        void onGetResultsSuccess(List<TestResults> resultsList);

        void showSnackbar(String message);

    }

    interface Presenter {

        void onDestroy();

        void onGetResultsCalled(int user_id);

    }

    interface Interactor {

        interface OnFinishedListener {

            void onFinished(List<TestResults> resultsList);

            void onFailure(String message);

        }

        void getResults(OnFinishedListener onFinishedListener, int user_id);

    }

}
