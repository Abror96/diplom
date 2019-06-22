package com.abror.diplom.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abror.diplom.R;
import com.abror.diplom.data.Glossary;
import com.abror.diplom.databinding.ActivityGlossaryBinding;

import java.util.ArrayList;

import static com.abror.diplom.utils.Constants.getGlossaryData;

public class GlossaryActivity extends AppCompatActivity {

    private ActivityGlossaryBinding binding;
    private ArrayList<Glossary> glossaryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_glossary);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        glossaryArrayList = getGlossaryData(this);
        binding.presentationsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.presentationsRecycler.setAdapter(new GlossaryAdapter());
    }

    public class GlossaryAdapter extends RecyclerView.Adapter<GlossaryAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.glossary_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.title.setText(Html.fromHtml(getItem(position).getTitle()));

        }

        private Glossary getItem(int position) {
            return glossaryArrayList.get(position);
        }

        @Override
        public int getItemCount() {
            return glossaryArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView title;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
