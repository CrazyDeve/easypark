package com.benben.carmanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benben.carmanager.R;
import com.benben.carmanager.activity.ChallengeWordActivity;
import com.benben.carmanager.dao.ScoreDao;
import com.benben.carmanager.dao.SettingDao;
import com.benben.carmanager.dao.StudyRecordDao;
import com.benben.carmanager.dao.WordDao;
import com.benben.carmanager.dao.WordRecordDao;
import com.benben.carmanager.model.Score;
import com.benben.carmanager.utils.Api;

public class ChallengeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";
    private static final int REQUEST_CHALLENGE = 0x100;

    private Context context;
    private SettingDao settingDao;
    private TextView mTvDifficalt, mTvTotalNum, mTvHasBack, mTvNoStudy, mTvNeedReview, mBtnStart;
    private WordDao wordDao;
    private WordRecordDao wordRecordDao;
    private StudyRecordDao studyRecordDao;
    private ScoreDao scoreDao;
    private LinearLayout llScore;
    private TextView tvMaxScore;
    private TextView tvPreScore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        settingDao = new SettingDao(context);
        wordDao = new WordDao(context);
        scoreDao = new ScoreDao(context);
        wordRecordDao = new WordRecordDao(context);
        studyRecordDao = new StudyRecordDao(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.challenge_fragment, container, false);


        mBtnStart = view.findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);
        llScore = view.findViewById(R.id.ll_score);
        tvMaxScore = view.findViewById(R.id.tv_maxscore);
        tvPreScore = view.findViewById(R.id.tv_prescore);
        initData();
        return view;
    }

    @Override
    protected View initView() {
        return null;
    }

    public void initData() {
        Score score = scoreDao.find(Api.userId);
        int maxScore = score.getMaxScore();
        Api.maxScore = maxScore;
        if(score.getMaxScore()<=0 && score.getPreScore()<=0){
            llScore.setVisibility(View.GONE);
        }else{
            llScore.setVisibility(View.VISIBLE);
            tvMaxScore.setText(score.getMaxScore()+"");
            tvPreScore.setText(score.getPreScore()+"");
        }

//        String difficulty = settingDao.getDifficulty();
//        int newNum = settingDao.getNewNum();
//        int sockNum = settingDao.getSockNum();
//        int allWordCount = wordDao.getAllWordCount();
//        int memoryTotalWordSize = wordRecordDao.getMemoryTotalWordSize();
//        int typeReWordCount = wordRecordDao.getTypeReWordCount();
//        Log.d(TAG, "onCreateView: 当前难度 == "+difficulty);
//        mTvDifficalt.setText(difficulty);
//        mTvTotalNum.setText(""+allWordCount);
//        mTvHasBack.setText(memoryTotalWordSize+"");
//        StudyRecord studyRecord = studyRecordDao.addOrGet();
//        int todayNeedNewNum = studyRecord.getNeedNewNum() - studyRecord.getNewNum();
//        mTvNoStudy.setText(todayNeedNewNum +"");
//        mTvNeedReview.setText(typeReWordCount+"");
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_start:
                intent.setClass(getContext(), ChallengeWordActivity.class);
                startActivityForResult(intent,REQUEST_CHALLENGE);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: 从挑战单词返回了...");
        initData();
    }
}
