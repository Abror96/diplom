package com.abror.diplom.mvp.contracts;

import com.abror.diplom.DBHelper.model.TestResults;

public interface TestContract {

    interface View {

        void onSaveResultSuccess();

        void showSnackbar(String message);

    }

    interface Presenter {

        void onDestroy();

        void onSaveResultClicked(int user_id, int score, String result);

    }

    interface Interactor {

        interface OnFinishedListener {

            void onFinished();

            void onFailure(String message);

        }

        void saveResult(OnFinishedListener onFinishedListener, TestResults testResults);

    }

}
