package com.abror.diplom.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.abror.diplom.R;
import com.abror.diplom.databinding.ActivityPptViewerBinding;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.shockwave.pdfium.PdfDocument;

public class PdfViewerActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener {

    private ActivityPptViewerBinding binding;
    private String fileName = "";
    private String TAG = "LOGGERR";
    private int currentPage = 0;
    private int lastPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ppt_viewer);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getStringExtra("fileName") != null) {
            fileName = getIntent().getStringExtra("fileName");
            Log.d(TAG, "onCreate: " + fileName+".pdf");
            binding.pdfView.fromAsset(fileName+".pdf")
                    .defaultPage(0)
                    .onPageChange(this)
                    .onLoad(this)
                    .swipeHorizontal(true)
                    .pageSnap(true)
                    .spacing(0)
                    .pageFling(true)
                    .load();

            binding.first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.pdfView.jumpTo(0, true);
                }
            });

            binding.prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentPage > 0) {
                        binding.pdfView.jumpTo(currentPage - 1, true);
                    }
                }
            });

            binding.next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentPage+1 < lastPage) {
                        binding.pdfView.jumpTo(currentPage + 1, true);
                    }
                }
            });

            binding.last.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.pdfView.jumpTo(lastPage, true);
                }
            });
        }
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = binding.pdfView.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        currentPage = page;
        lastPage = pageCount;
        binding.pageCount.setText((page + 1) + " / " + pageCount);
    }

    @Override
    public void onPageError(int page, Throwable t) {
        Log.e(TAG, "Cannot load page " + page);
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
