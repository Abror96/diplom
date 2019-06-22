package com.abror.diplom.mvp.contracts;

import com.abror.diplom.DBHelper.model.Student;

public interface AuthContract {

    interface View {

        void onAuthSuccess(Student student);

        void showSnackbar(String message);

    }

    interface Presenter {

        void onDestroy();

        void onAuthClicked(String login, String password);

    }

    interface Interactor {

        interface OnFinishedListener {

            void onFinished(Student student);

            void onFailure(String message);

        }

        void authStudent(OnFinishedListener onFinishedListener, String login, String password);

    }

}
