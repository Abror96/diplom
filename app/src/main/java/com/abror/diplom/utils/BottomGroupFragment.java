package com.abror.diplom.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.abror.diplom.R;
import com.abror.diplom.databinding.FragmentBottomGroupBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomGroupFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private FragmentBottomGroupBinding binding;
    private static IOnBtnPressed onBtnPressed;

    public static void setBtnClickListener(IOnBtnPressed onBtnPressed) {
        BottomGroupFragment.onBtnPressed = onBtnPressed;
    }

    public static BottomGroupFragment getInstance() {
        return new BottomGroupFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_group, container, false);

        binding.first.setOnClickListener(this);
        binding.second.setOnClickListener(this);
        binding.third.setOnClickListener(this);
        binding.fourth.setOnClickListener(this);
        binding.fifth.setOnClickListener(this);
        binding.sixth.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first:
                onBtnPressed.onGroupClicked(0);
                break;
            case R.id.second:
                onBtnPressed.onGroupClicked(1);
                break;
            case R.id.third:
                onBtnPressed.onGroupClicked(2);
                break;
            case R.id.fourth:
                onBtnPressed.onGroupClicked(3);
                break;
            case R.id.fifth:
                onBtnPressed.onGroupClicked(4);
                break;
            case R.id.sixth:
                onBtnPressed.onGroupClicked(5);
                break;
        }
    }
}
