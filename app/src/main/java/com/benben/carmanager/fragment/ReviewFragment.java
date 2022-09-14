package com.benben.carmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.carmanager.R;
import com.benben.carmanager.activity.WordReviewActivity;
import com.benben.carmanager.adapter.StudyDayAdapter;
import com.benben.carmanager.dao.StudyRecordDao;

import java.util.ArrayList;

public class ReviewFragment extends BaseFragment {

    private StudyRecordDao studyRecordDao;
    private RecyclerView mRvDays;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studyRecordDao = new StudyRecordDao(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_fragment, container,false);
        mRvDays = view.findViewById(R.id.rv_days);
        StudyDayAdapter adapter = new StudyDayAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRvDays.setLayoutManager(linearLayoutManager);
        ArrayList<String> data = getData();
        adapter.setData(data);
        adapter.setOnItemClickListener(new StudyDayAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(String date) {
                Intent intent = new Intent(getContext(), WordReviewActivity.class);
                String nowDate = date.toString().trim();
                intent.putExtra("date",nowDate);
                getActivity().startActivity(intent);

            }
        });
        mRvDays.setAdapter(adapter);
        return view;
    }

    @Override
    protected View initView() {
        return null;
    }

    private ArrayList<String> getData() {
        ArrayList<String> allStudyDays = studyRecordDao.getAllStudyDays();
        return allStudyDays;
    }
}
