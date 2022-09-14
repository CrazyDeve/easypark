package com.benben.carmanager.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.benben.carmanager.dao.SettingDao;
import com.benben.carmanager.dao.StudyRecordDao;
import com.benben.carmanager.dao.WordDao;
import com.benben.carmanager.dao.WordRecordDao;
import com.benben.carmanager.model.StudyRecord;
import com.benben.carmanager.model.Word;
import com.benben.carmanager.utils.LocalTrueFalseMediaPlayer;
import com.benben.carmanager.utils.Sentence_split;
import com.benben.carmanager.utils.Tran_CN_split;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class WordReviewActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "WordReviewActivity";

    private TextView mTvTitle;
    private ImageView mIvBack;
    private TextView mWordText, mSentanceText, mTranEnText, mTodayNeedNewCount, mTodayNeedReviewCount, mUkPhonetic, mUsPhonetic;
    private LinearLayout mLLUkVoice, mLLUsVoice;
    private RadioGroup radioGroup;      //用于加载单词的四个选项
    private RadioButton radioOne, radioTwo, radioThree, radioFour;   //  单词意思的四个选项
    private RadioButton[] radioButtonsgroups = new RadioButton[4];
    private WordDao wordDao;
    private WordRecordDao wordRecordDao;
    private SettingDao settingDao;
    private StudyRecordDao studyRecordDao;
    int start;    //定位要开始背单词的位置
    private List<Word> mWords;

    private Word nowword; //当前正在背诵的单词
    private LocalTrueFalseMediaPlayer localTrueFalseMediaPlayer;//提示音类

    private int currentNum;
    private View llNoData;
    private View mainView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_review);
        currentNum = 0;
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);


        //初始化按钮
        llNoData = findViewById(R.id.ll_nodata);
        llNoData.setVisibility(View.GONE);
        mainView = findViewById(R.id.main_view);
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
        mTodayNeedReviewCount = findViewById(R.id.today_needreviewCount);


        //初始化数据库操作
        wordDao = new WordDao(this);
        wordRecordDao = new WordRecordDao(this);
        settingDao = new SettingDao(this);
        studyRecordDao = new StudyRecordDao(this);

        initData();
    }

    private void initData() {
        mTvTitle.setText(getString(R.string.单词复习));
        String strDate = getIntent().getStringExtra("date");
        if (TextUtils.isEmpty(strDate)) {
            strDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }

        strDate = strDate.trim();

        start = wordRecordDao.getTypeStudyWordCount();//获取背过的该类单词数来定位开始位置
        //获取还需背诵的数量
        StudyRecord studyRecord = studyRecordDao.addOrGet();


        //获取需要新背的单词
        mWords = wordDao.getDayWords(strDate);
        if (mWords != null) {
            mTodayNeedNewCount.setText(mWords.size() + "");
        }

        if (mWords != null && mWords.size() < 1) {
            llNoData.setVisibility(View.VISIBLE);
            mainView.setVisibility(View.GONE);
        } else {
            llNoData.setVisibility(View.GONE);
            mainView.setVisibility(View.VISIBLE);
            getNextWord();
        }


    }

    private void getNextWord() {
        if (mWords != null && mWords.size() > 0) {
            Iterator<Word> iterator = mWords.iterator();
            if (iterator.hasNext()) {
                nowword = iterator.next();
                setTextColor();
                setword(nowword);
            }
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
        currentNum++;
        mTodayNeedReviewCount.setText(currentNum + "");

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
        radioGroup.setClickable(false);            //默认选项未被选中

        radioGroup.setEnabled(false);
        radioTwo.setEnabled(false);
        radioOne.setEnabled(false);
        radioThree.setEnabled(false);
        radioFour.setEnabled(false);
        switch (checkedId) {                        //选项的点击事件
            case R.id.choose_btn_one:                //点击“A”选项
                String msg = radioOne.getText().toString().substring(3);        //截取字符串
                invalideAnswer(msg, radioOne);            //将参数传入到对应的方法里
                break;
            case R.id.choose_btn_two:                //点击“B”选项
                String msg1 = radioTwo.getText().toString().substring(3);    //截取字符串
                invalideAnswer(msg1, radioTwo);        //将参数传入到对应的方法里
                break;
            case R.id.choose_btn_three:            //点击“C”选项
                String msg2 = radioThree.getText().toString().substring(3);    //截取字符串
                invalideAnswer(msg2, radioThree);        //将参数传入到对应的方法里
                break;
            case R.id.choose_btn_four:            //点击“D”选项
                String msg3 = radioFour.getText().toString().substring(3);    //截取字符串
                invalideAnswer(msg3, radioFour);        //将参数传入到对应的方法里
                break;
        }
    }

    /**
     * 校验答案
     */
    private void invalideAnswer(String answer, RadioButton button) {

        if (answer.equals(Tran_CN_split.getspit(nowword.getTranCN()))) {
            button.setBackgroundColor(getResources().getColor(R.color.color_true));        //设置选项为绿色
            localTrueFalseMediaPlayer.play(true);
            wordRecordDao.trueSaveDate(nowword);
        }//保存到数据库
        else {
            button.setBackgroundColor(getResources().getColor(R.color.color_error));            //设置选项为红色
            localTrueFalseMediaPlayer.play(false);
            for (int k = 0; k < 4; k++) {                   //设置正确答案的颜色为绿色
                if (radioButtonsgroups[k].getText().toString().substring(3).equals(Tran_CN_split.getspit(nowword.getTranCN()))) {
                    radioButtonsgroups[k].setBackgroundColor(getResources().getColor(R.color.color_true));
                }
            }
            wordRecordDao.wrongSaveDate(nowword);//保存到数据库
        }

        //先设置不被点击然后设置延时
        radioOne.setChecked(false);        //默认不被点击
        radioTwo.setChecked(false);        //默认不被点击
        radioThree.setChecked(false);        //默认不被点击
        radioFour.setChecked(false);        //默认不被点击
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mWords != null && mWords.size() <= 1) {
                    Log.d(TAG, "run: 挑战结束了  == " + mWords.size());
                    Toast.makeText(WordReviewActivity.this, "完成复习！", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.d(TAG, "run: 当前单词数  == " + mWords.size());
                    mWords.remove(0);
                    getNextWord();
                }

            }
        }, 1000);
    }

}