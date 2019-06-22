package com.abror.diplom.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abror.diplom.DBHelper.model.TestResults;
import com.abror.diplom.R;
import com.abror.diplom.databinding.ActivityStudentResultsBinding;
import com.abror.diplom.mvp.contracts.StudentResultContract;
import com.abror.diplom.mvp.presenters.StudentResultPresenterImpl;
import com.abror.diplom.utils.PrefConfig;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class StudentResultsActivity extends AppCompatActivity implements StudentResultContract.View {

    private ActivityStudentResultsBinding binding;
    private ArrayList<TestResults> resultsArrayList;
    private PrefConfig prefConfig;
    private StudentResultContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_student_results);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        prefConfig = new PrefConfig(this);
        presenter = new StudentResultPresenterImpl(this, this);
        presenter.onGetResultsCalled(prefConfig.getUserId());

        binding.resultsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onGetResultsSuccess(List<TestResults> resultsList) {
        resultsArrayList = (ArrayList<TestResults>) resultsList;
        if (resultsList.size() == 0) {
            binding.line1.setVisibility(View.GONE);
            binding.resultsRecycler.setVisibility(View.GONE);
            binding.noResults.setVisibility(View.VISIBLE);
        } else {
            binding.line1.setVisibility(View.VISIBLE);
            binding.resultsRecycler.setVisibility(View.VISIBLE);
            binding.noResults.setVisibility(View.GONE);
        }
        binding.resultsRecycler.setAdapter(new ResultsAdapter());
    }

    @Override
    public void showSnackbar(String message) {
        Log.d("LOGGERR", "showSnackbar: " + message);
        Snackbar.make(binding.mainView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultViewHolder> {

        @NonNull
        @Override
        public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.test_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
            if (getItem(position).getScore() < 3) {
                holder.icon.setImageResource(R.drawable.ic_test_failed);
            } else holder.icon.setImageResource(R.drawable.ic_test_granted);
            holder.result.setText(getItem(position).getResult());
            holder.score.setText(String.valueOf(getItem(position).getScore()));
        }

        @Override
        public int getItemCount() {
            return resultsArrayList.size();
        }

        private TestResults getItem(int position) {
            return resultsArrayList.get(position);
        }

        class ResultViewHolder extends RecyclerView.ViewHolder {

            TextView score;
            TextView result;
            ImageView icon;

            public ResultViewHolder(@NonNull View itemView) {
                super(itemView);

                icon = itemView.findViewById(R.id.result_icon);
                result = itemView.findViewById(R.id.result_text);
                score = itemView.findViewById(R.id.result_score);
            }
        }
    }
}
