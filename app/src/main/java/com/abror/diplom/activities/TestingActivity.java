package com.abror.diplom.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abror.diplom.R;
import com.abror.diplom.data.Variant;
import com.abror.diplom.databinding.ActivityTestingBinding;

import java.util.ArrayList;

import static com.abror.diplom.utils.Constants.getAllTests;

public class TestingActivity extends AppCompatActivity {

    private ActivityTestingBinding binding;
    private ArrayList<Variant> variantArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_testing);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        variantArrayList = getAllTests();

        binding.testsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.testsRecycler.setAdapter(new TestingAdapter());
    }

    class TestingAdapter extends RecyclerView.Adapter<TestingAdapter.TestViewHolder> {


        @NonNull
        @Override
        public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.presentation_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
            holder.title.setText(getItem(position).getTitle());
            holder.imageView.setImageResource(R.drawable.ic_test);

            holder.main_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                    intent.putExtra("variant", getItem(position));
                    intent.putExtra("test", getItem(position).getQuestions());
                    Log.d("LOGGERR", "onClick: " + getItem(position).getQuestions().size());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return variantArrayList.size();
        }

        private Variant getItem(int position) {
            return variantArrayList.get(position);
        }

        class TestViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            LinearLayout main_view;
            ImageView imageView;

            public TestViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                main_view = itemView.findViewById(R.id.main_view);
                imageView = itemView.findViewById(R.id.image);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
