package com.abror.diplom.mvp.contracts;

import com.abror.diplom.DBHelper.model.Student;

import java.util.Date;

public interface RegContract {

    interface View {

        void showSnackbar(String message);

        void onAddStudentSuccess(Student student);

    }

    interface Presenter {

        void onDestroy();

        void onAddStudentClicked(
                Date creationDate,
                String login,
                String password,
                String full_name,
                int group_id,
                int course,
                byte[] photo
        );

    }

    interface Interactor {

        interface OnFinishedListener {

            void onFinished(Student student);

            void onFailure(String message);

        }

        void addStudent(OnFinishedListener onFinishedListener,
                     Student student);

    }

}
