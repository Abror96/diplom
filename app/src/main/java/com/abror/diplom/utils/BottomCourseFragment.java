package com.abror.diplom.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.abror.diplom.R;
import com.abror.diplom.databinding.FragmentBottomCourseBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomCourseFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private FragmentBottomCourseBinding binding;
    private static IOnBtnPressed onBtnPressed;

    public static void setBtnClickListener(IOnBtnPressed onBtnPressed) {
        BottomCourseFragment.onBtnPressed = onBtnPressed;
    }

    public static BottomCourseFragment getInstance() {
        return new BottomCourseFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_course, container, false);

        binding.first.setOnClickListener(this);
        binding.second.setOnClickListener(this);
        binding.third.setOnClickListener(this);
        binding.fourth.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first:
                onBtnPressed.onCourseClicked(0);
                break;
            case R.id.second:
                onBtnPressed.onCourseClicked(1);
                break;
            case R.id.third:
                onBtnPressed.onCourseClicked(2);
                break;
            case R.id.fourth:
                onBtnPressed.onCourseClicked(3);
                break;
        }
    }
}
