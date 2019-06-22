package com.abror.diplom.DBHelper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.abror.diplom.DBHelper.model.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    long addStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Query("SELECT * FROM student WHERE login=:login")
    Student getStudentByLogin(String login);

    @Query("SELECT * FROM student")
    List<Student> getAllStudent();

}
