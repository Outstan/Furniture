package com.hongwei.furniture.modules.test.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hongwei.furniture.R;

/**
 * FileName: ColorTrackFragment
 * Author: XYB
 * Date: 2020/12/25
 * Description: ColorTrackTextView测试fragment
 */
public class ColorTrackFragment extends Fragment {

    private TextView textView;

    private String string;

    private ColorTrackFragment(String context){
        string = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_test,container,false);
        textView = view.findViewById(R.id.test);

        textView.setText(string);

        return view;
    }

    public static Fragment newInstance(String item) {
        return new ColorTrackFragment(item);
    }
}
