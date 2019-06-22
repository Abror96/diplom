package com.abror.diplom.DBHelper.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import com.abror.diplom.DBHelper.db.StudentDataBase;
import com.abror.diplom.DBHelper.model.Student;
import com.abror.diplom.DBHelper.model.TestResults;
import com.abror.diplom.mvp.contracts.AuthContract;
import com.abror.diplom.mvp.contracts.MainContract;
import com.abror.diplom.mvp.contracts.RegContract;
import com.abror.diplom.mvp.contracts.StudentResultContract;
import com.abror.diplom.mvp.contracts.TestContract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StudentRepository implements RegContract.Interactor, AuthContract.Interactor, MainContract.Interactor, TestContract.Interactor, StudentResultContract.Interactor {

    private String DB_NAME = "db_students";
    private StudentDataBase studentDataBase;


    public StudentRepository(Context context) {
        studentDataBase = Room.databaseBuilder(context, StudentDataBase.class, DB_NAME).build();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteTest(TestResults testResults) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                studentDataBase.testResultsDao().deleteTest(testResults);
                return null;
            }
        }.execute();
    }

    public List<TestResults> getStudentResults(Integer studentId) {
        return studentDataBase.testResultsDao().getStudentResults(studentId);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void addStudent(RegContract.Interactor.OnFinishedListener onFinishedListener, Student student) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Long id = studentDataBase.studentDao().addStudent(student);
                    student.setId(id.intValue());
                    onFinishedListener.onFinished(student);
                } catch (Exception e) {
                    String error = e.getMessage();
                    Log.d("LOGGERR", "Insert user error: " + error);
                    if (error.contains("2067")) {
                        onFinishedListener.onFailure("Такой логин уже существует. Выберите другой");
                    }
                }
                return null;
            }
        }.execute();
    }

    @Override
    public void authStudent(AuthContract.Interactor.OnFinishedListener onFinishedListener, String login, String password) {
        try {
            Student student = new GetStudentAsync().execute(login).get();
            if (student != null) {
                if (student.getPassword().equals(password)) {
                    onFinishedListener.onFinished(student);
                } else {
                    onFinishedListener.onFailure("Неверный логин или пароль");
                }
            } else {
                onFinishedListener.onFailure("Студент не найден");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.d("LOGGERR", "authStudent 1: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("LOGGERR", "authStudent 2: " + e.getMessage());
        }
    }


    @Override
    public void getStudentByLogin(MainContract.Interactor.OnFinishedListener onFinishedListener, String login) {
        try {
            Student student = new GetStudentAsync().execute(login).get();
            if (student != null) {
                onFinishedListener.onFinished(student);
            } else {
                onFinishedListener.onFailure("Студент не найден");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.d("LOGGERR", "authStudent 1: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("LOGGERR", "authStudent 2: " + e.getMessage());
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void saveResult(TestContract.Interactor.OnFinishedListener onFinishedListener, TestResults testResults) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    studentDataBase.testResultsDao().insertTest(testResults);
                    onFinishedListener.onFinished();
                } catch (Exception e) {
                    Log.d("LOGGERR", "saveResult: " + e.getMessage());
                    onFinishedListener.onFailure("Произошла ошибка при сохранении теста");
                }
                return null;
            }
        }.execute();
    }

    @Override
    public void getResults(StudentResultContract.Interactor.OnFinishedListener onFinishedListener, int user_id) {
        try {
            List<TestResults> testResults = new GetStudentResults().execute(user_id).get();
            onFinishedListener.onFinished(testResults);
        } catch (Exception e) {
            onFinishedListener.onFailure("Ошибка при получении результатов тестирований");
            Log.d("LOGGERR", "getResults: " + e.getMessage());
            e.printStackTrace();
        }
    }

    class GetStudentAsync extends AsyncTask<String, Void, Student> {

        @Override
        protected Student doInBackground(String... strings) {
            return studentDataBase.studentDao().getStudentByLogin(strings[0]);
        }
    }

    class GetStudentResults extends AsyncTask<Integer, Void, List<TestResults>> {
        @Override
        protected List<TestResults> doInBackground(Integer... integers) {
            return studentDataBase.testResultsDao().getStudentResults(integers[0]);
        }
    }

}
