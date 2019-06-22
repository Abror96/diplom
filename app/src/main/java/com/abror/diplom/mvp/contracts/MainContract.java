package com.abror.diplom.mvp.contracts;

import com.abror.diplom.DBHelper.model.Student;

public interface MainContract {

    interface View {

        void showSnackbar(String message);

        void onGetStudentSuccess(Student student);

    }

    interface Presenter {

        void onDestroy();

        void onGetStudentClicked(String login);

    }

    interface Interactor {

        interface OnFinishedListener {

            void onFinished(Student student);

            void onFailure(String message);

        }

        void getStudentByLogin(OnFinishedListener onFinishedListener, String login);

    }

}
