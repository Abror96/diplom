package com.abror.diplom.DBHelper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.abror.diplom.DBHelper.model.TestResults;

import java.util.List;

@Dao
public interface TestResultsDao {

    @Insert
    void insertTest(TestResults testResults);

    @Delete
    void deleteTest(TestResults... testResults);

    @Query("SELECT * FROM test_results WHERE student_id=:studentId")
    List<TestResults> getStudentResults(Integer studentId);

}
