package com.abror.diplom.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.abror.diplom.R;
import com.abror.diplom.data.Test;
import com.abror.diplom.data.Variant;
import com.abror.diplom.databinding.ActivityTestBinding;
import com.abror.diplom.mvp.contracts.TestContract;
import com.abror.diplom.mvp.presenters.TestPresenterImpl;
import com.abror.diplom.utils.PrefConfig;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

public class TestActivity extends AppCompatActivity implements TestContract.View {

    private Variant variant;
    private ActivityTestBinding binding;
    private ArrayList<Test> tests;
    private int[] answers;
    private String[] questions;
    private int iteration = 0;
    private int[] radio_buttons = {
            R.id.first,
            R.id.second,
            R.id.third,
            R.id.fourth
    };
    private int number_of_correct_answers = 0;
    private int back_pressed = 0;
    private TestContract.Presenter presenter;
    private PrefConfig prefConfig;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        setSupportActionBar(binding.toolbar);

        presenter = new TestPresenterImpl(this, this);
        prefConfig = new PrefConfig(this);

        if (getIntent().getParcelableExtra("variant") != null) {
            variant = getIntent().getParcelableExtra("variant");
            tests = getIntent().getParcelableArrayListExtra("test");
            answers = variant.getAnswers();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(variant.getTitle());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            Log.d("LOGGERR", "onCreate: " + tests.size());
            setTest(iteration);
            binding.nextTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    back_pressed = 0;
                    if (binding.radioGroup.getCheckedRadioButtonId() != -1) {
                        if (iteration < tests.size() - 1) {
                            setTest(++iteration);

                            countScore();

                            binding.radioGroup.clearCheck();
                        } else {
                            ++iteration;
                            countScore();
                            Log.d("LOGGERR", "onClick: " + number_of_correct_answers);

                            setAlertDialog();
                        }

                    } else Snackbar.make(binding.mainView, "Выберите ответ", Snackbar.LENGTH_LONG).show();
                }
            });

        }
    }

    private void countScore() {
        int chosenAnswer = -1;
        if (binding.radioGroup.getCheckedRadioButtonId() == radio_buttons[0]) {
            chosenAnswer = 0;
        } else if (binding.radioGroup.getCheckedRadioButtonId() == radio_buttons[1]) {
            chosenAnswer = 1;
        } else if (binding.radioGroup.getCheckedRadioButtonId() == radio_buttons[2]) {
            chosenAnswer = 2;
        } else if (binding.radioGroup.getCheckedRadioButtonId() == radio_buttons[3]) {
            chosenAnswer = 3;
        }
        if (chosenAnswer == answers[iteration-1]) {
            // correct answer
            number_of_correct_answers++;
        } else {
            Log.d("LOGGERR", "\n"
                    + "\n chosen answer: " + chosenAnswer
                    + "\n correct answer: " + answers[iteration-1]);
        }
    }

    private void setAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.test_result_layout, null);
        dialogBuilder.setView(dialogView);

        ImageView icon = dialogView.findViewById(R.id.test_icon);
        TextView result_count = dialogView.findViewById(R.id.test_result_count);
        TextView result_text = dialogView.findViewById(R.id.test_result_text);
        TextView test_score = dialogView.findViewById(R.id.test_score);

        result_count.setText(number_of_correct_answers + " из 15");
        if (number_of_correct_answers > 13) {
            test_score.setText("Ваша оценка 5");
            score = 5;
        } else if (number_of_correct_answers > 10) {
            test_score.setText("Ваша оценка 4");
            score = 4;
        } else if (number_of_correct_answers > 7) {
            test_score.setText("Ваша оценка 3");
            score = 3;
        } else {
            score = 2;
            test_score.setText("Ваша оценка 2");
            icon.setImageResource(R.drawable.ic_test_failed);
            result_text.setText("К сожалению Вы не прошли тестирование.");
        }

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.d("LOGGERR", "onDismiss: " + prefConfig.getUserId());
                presenter.onSaveResultClicked(prefConfig.getUserId(), score, score >= 3 ? "Поздравляем! Вы прошли тест!" : "К сожалению Вы не прошли тестирование.");
            }
        });
    }

    private void setTest(int position) {
        binding.testNum.setText("Вопрос "+(position+1) + " / 15");
        binding.tvQuestion.setText(tests.get(position).getTitle());
        String[] options = tests.get(position).getOptions();
        for (int i = 0; i < options.length; i++) {
            ((RadioButton)binding.radioGroup.getChildAt(i)).setText(options[i]);
        }
        if (options.length == 3) binding.radioGroup.getChildAt(3).setVisibility(View.GONE);
        else if (options.length == 4) binding.radioGroup.getChildAt(3).setVisibility(View.VISIBLE);

        if (position == 14) {
            binding.btnText.setText("Закончить тест");
        }
    }

    @Override
    public void onBackPressed() {
        back_pressed++;
        if (back_pressed > 1) {
            super.onBackPressed();
        } else {
            Snackbar.make(binding.mainView, "Ваш тест не сохранится. Нажимите еще раз чтобы выйти.", 3500).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveResultSuccess() {
        finish();
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(binding.mainView, message, 2000);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        finish();
                    }
                },
                1500);
    }
}
