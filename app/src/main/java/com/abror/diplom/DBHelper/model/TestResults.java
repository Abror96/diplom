package com.abror.diplom.DBHelper.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "test_results", foreignKeys = @ForeignKey(entity = Student.class,
                                        parentColumns = "id",
                                        childColumns = "student_id",
                                        onDelete = CASCADE), indices = @Index(value = "student_id"))
public class TestResults {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private Integer student_id;
    private Integer score;
    private String result;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
