package com.benben.carmanager.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.benben.carmanager.R;
import com.benben.carmanager.dao.ScoreDao;
import com.benben.carmanager.dao.SettingDao;
import com.benben.carmanager.dao.StudyRecordDao;
import com.benben.carmanager.dao.WordDao;
import com.benben.carmanager.dao.WordRecordDao;
import com.benben.carmanager.model.StudyRecord;
import com.benben.carmanager.model.Word;
import com.benben.carmanager.utils.Api;
import com.benben.carmanager.utils.LocalTrueFalseMediaPlayer;
import com.benben.carmanager.utils.Sentence_split;
import com.benben.carmanager.utils.Tran_CN_split;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ChallengeWordActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "MemoryWordMainActivity";

    private TextView mTvTitle;
    private ImageView mIvBack;
    private TextView mWordText, mSentanceText, mTranEnText, mTodayNeedNewCount, mTodayNeedReviewCount,mUkPhonetic,mUsPhonetic;
    private LinearLayout mLLUkVoice, mLLUsVoice;
    private RadioGroup radioGroup;      //用于加载单词的四个选项
    private RadioButton radioOne, radioTwo, radioThree, radioFour;   //  单词意思的四个选项
    private RadioButton[] radioButtonsgroups = new RadioButton[4];
    private WordDao wordDao;
    private WordRecordDao wordRecordDao;
    private SettingDao settingDao;
    private StudyRecordDao studyRecordDao;
    private ScoreDao scoreDao;
    int start;    //定位要开始背单词的位置
    private List<Word> mWords;

    private Word nowword; //当前正在背诵的单词
    private LocalTrueFalseMediaPlayer localTrueFalseMediaPlayer;//提示音类
    private TextView mTvScore,mTvPosi,mTvTime;

    private int currentPosition;
    private int currentScore;
    private int currentTime = 15;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_word);
        mHandler = new Handler(getMainLooper());
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);


        mTvScore = findViewById(R.id.tvScore);
        mTvPosi = findViewById(R.id.tv_posi);
        mTvTime = findViewById(R.id.tv_time);

        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);


        //初始化按钮
        mWordText = findViewById(R.id.word_text);
        mLLUkVoice = findViewById(R.id.uk_voice);
        mLLUsVoice = findViewById(R.id.us_voice);
        mLLUkVoice.setOnClickListener(this);
        mLLUsVoice.setOnClickListener(this);
        mUkPhonetic = findViewById(R.id.ukphonetic_text);
        mUsPhonetic = findViewById(R.id.usphonetic_text);
        mSentanceText = findViewById(R.id.sentence_text);
        mTranEnText = findViewById(R.id.tranEN_text);
        radioGroup = findViewById(R.id.choose_group);
        radioOne = findViewById(R.id.choose_btn_one);
        radioTwo = findViewById(R.id.choose_btn_two);
        radioThree = findViewById(R.id.choose_btn_three);
        radioFour = findViewById(R.id.choose_btn_four);
        //给按钮添加监听
        radioGroup.setOnCheckedChangeListener(this);


        radioButtonsgroups[0] = radioOne;
        radioButtonsgroups[1] = radioTwo;
        radioButtonsgroups[2] = radioThree;
        radioButtonsgroups[3] = radioFour;
        mTodayNeedNewCount = findViewById(R.id.today_neednewCount);



        //初始化数据库操作
        wordDao = new WordDao(this);
        scoreDao = new ScoreDao(this);
        wordRecordDao = new WordRecordDao(this);
        settingDao = new SettingDao(this);
        studyRecordDao = new StudyRecordDao(this);

        localTrueFalseMediaPlayer=new LocalTrueFalseMediaPlayer(this);

        initData();
    }

    private void initData() {
        mTvTitle.setText(getString(R.string.单词挑战));

        start = wordRecordDao.getTypeStudyWordCount();//获取背过的该类单词数来定位开始位置
        //获取还需背诵的数量
        StudyRecord studyRecord = studyRecordDao.addOrGet();
        int todayNeedNewNum = settingDao.getChallengeTotalNum();
        mTodayNeedNewCount.setText(settingDao.getChallengeTotalNum() +"");
        int todayNeedReviewcount = studyRecord.getNeedRepeatNum() - studyRecord.getRepeatNum();//今日还需复习的单词数量

        //获取需要新背的单词
        mWords = wordDao.getWords(start, todayNeedNewNum);

        getNextWord();

    }

    public void setShowScoreAndPosi(){
        mTvScore.setText(currentScore+"");
        mTvPosi.setText(currentPosition+"");
    }



    private void getNextWord() {
        if(mWords!=null && mWords.size()>0){
            Iterator<Word> iterator = mWords.iterator();
            if(iterator.hasNext()){
                nowword = iterator.next();
                setTextColor();

                currentPosition++;
                currentTime =15;
                updateTime();
                setShowScoreAndPosi();
                setword(nowword);
                if(mHandler!=null){
                    mHandler.removeCallbacksAndMessages(null);
                }
                startTime();
            }
        }
    }

    private void startTime() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTime();
                startTime();
            }
        },1000);
    }

    /**
     * 更新时间
     */
    private void updateTime() {

        mTvTime.setText(currentTime+"");
        currentTime --;
        if(currentTime==0){
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

        }
    }


    /**
     * 还原单词与选项的颜色
     */
    private void setTextColor() {
        //还原单词选项的颜色
        radioGroup.setEnabled(true);
        radioOne.setEnabled(true);
        radioThree.setEnabled(true);
        radioTwo.setEnabled(true);
        radioFour.setEnabled(true);

        radioOne.setBackgroundColor(Color.parseColor("#ffffff"));      //将选项的按钮设置为白色
        radioTwo.setBackgroundColor(Color.parseColor("#ffffff"));     //将选项的按钮设置为白色
        radioThree.setBackgroundColor(Color.parseColor("#ffffff"));     //将选项的按钮设置为白色
        radioFour.setBackgroundColor(Color.parseColor("#ffffff"));        //将选项的按钮设置为白色

    }

    /**
     * 设置选项
     */
    private void setword(Word word) {
        mWordText.setText(word.getHeadWord());
        mUkPhonetic.setText(word.getUkphone());
        mUsPhonetic.setText(word.getUsphone());
        mSentanceText.setText(Sentence_split.getspit(word.getSentences()));
        mTranEnText.setText(word.getTranEN());

        wordRecordDao.SaveDate(word);//保存单词数据到数据库

        Random random = new Random();
        List<Word> worrywords = new ArrayList<Word>();
        //随机获得三个其他错误单词
        worrywords = wordDao.getwrongwords(3);
        int r = random.nextInt(4);//获取正确答案的选项位置
        switch (r) {
            case 0:
                radioOne.setText("A: " + Tran_CN_split.getspit(word.getTranCN()));
                radioTwo.setText("B: " + Tran_CN_split.getspit(worrywords.get(0).getTranCN()));
                radioThree.setText("C: " + Tran_CN_split.getspit(worrywords.get(1).getTranCN()));
                radioFour.setText("D: " + Tran_CN_split.getspit(worrywords.get(2).getTranCN()));
                break;
            case 1:
                radioOne.setText("A: " + Tran_CN_split.getspit(worrywords.get(0).getTranCN()));
                radioTwo.setText("B: " + Tran_CN_split.getspit(word.getTranCN()));
                radioThree.setText("C: " + Tran_CN_split.getspit(worrywords.get(1).getTranCN()));
                radioFour.setText("D: " + Tran_CN_split.getspit(worrywords.get(2).getTranCN()));
                break;
            case 2:
                radioOne.setText("A: " + Tran_CN_split.getspit(worrywords.get(1).getTranCN()));
                radioTwo.setText("B: " + Tran_CN_split.getspit(worrywords.get(0).getTranCN()));
                radioThree.setText("C: " + Tran_CN_split.getspit(word.getTranCN()));
                radioFour.setText("D: " + Tran_CN_split.getspit(worrywords.get(2).getTranCN()));
                break;
            case 3:
                radioOne.setText("A: " + Tran_CN_split.getspit(worrywords.get(2).getTranCN()));
                radioTwo.setText("B: " + Tran_CN_split.getspit(worrywords.get(0).getTranCN()));
                radioThree.setText("C: " + Tran_CN_split.getspit(worrywords.get(1).getTranCN()));
                radioFour.setText("D: " + Tran_CN_split.getspit(word.getTranCN()));
                break;
        }

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {


    }

    /**
     * 校验答案
     */
    private void invalideAnswer(String answer, RadioButton button) {

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
        //更新成绩
        scoreDao.update(Api.userId,currentScore);

    }
}