package com.abror.diplom.DBHelper.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.abror.diplom.DBHelper.dao.StudentDao;
import com.abror.diplom.DBHelper.dao.TestResultsDao;
import com.abror.diplom.DBHelper.model.Student;
import com.abror.diplom.DBHelper.model.TestResults;

@Database(entities = {Student.class, TestResults.class}, version = 1, exportSchema = false)
public abstract class StudentDataBase extends RoomDatabase {

    public abstract StudentDao studentDao();
    public abstract TestResultsDao testResultsDao();

}
