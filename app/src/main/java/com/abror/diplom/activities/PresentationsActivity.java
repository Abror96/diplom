package com.abror.diplom.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abror.diplom.R;
import com.abror.diplom.data.Presentation;
import com.abror.diplom.databinding.ActivityPresentationsBinding;

import java.util.ArrayList;

import static com.abror.diplom.utils.Constants.getPresentationData;

public class PresentationsActivity extends AppCompatActivity {

    private ActivityPresentationsBinding binding;
    private ArrayList<Presentation> presentationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_presentations);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        presentationArrayList = getPresentationData();
        binding.presentationsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.presentationsRecycler.setAdapter(new PresentationsAdapter());
    }


    public class PresentationsAdapter extends RecyclerView.Adapter<PresentationsAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.presentation_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.title.setText(getItem(position).getTitle());
            holder.main_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), PdfViewerActivity.class);
                    intent.putExtra("fileName", getItem(position).getFileName());
                    startActivity(intent);
                }
            });
        }

        private Presentation getItem(int position) {
            return presentationArrayList.get(position);
        }

        @Override
        public int getItemCount() {
            return presentationArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            LinearLayout main_view;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                main_view = itemView.findViewById(R.id.main_view);
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
