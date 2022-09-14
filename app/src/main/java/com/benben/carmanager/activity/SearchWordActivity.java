package com.benben.carmanager.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.carmanager.R;
import com.benben.carmanager.adapter.WordAdapter;
import com.benben.carmanager.dao.WordDao;
import com.benben.carmanager.dao.WordTypeDao;
import com.benben.carmanager.model.Word;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;

import java.util.List;

public class SearchWordActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private TextView mTvTitle;
    private ImageView mIvBack;

    private WordDao wordDao;
    private EditText serchInput;
    private List<Word> wordList;
    private RecyclerView recyclerView;
    private String input;
    public WordAdapter wordAdapter;
    public List<Word> wordresult;
    private RecyclerTouchListener onTouchListener;//侧滑菜单
    private AlertDialog.Builder builder;//添加单词本对话框
    private Word word0;//全局变量，记录单词结果列表
    private boolean[] checkedItems;//记录各个列表项的状态
    private String[] items;     //记录列表项要选择的单词本
    View dialog;
    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;
    private WordTypeDao wordTypeDao;
    private String type ;//搜索的单词类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);
        mTvTitle.setText(getString(R.string.搜索单词));

        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void init(){
        type=getIntent().getStringExtra("type");//获取页面传过来的要搜索的单词类型
        wordDao=new WordDao(this);
        wordTypeDao=new WordTypeDao(this);
        input="a";
        serchInput=findViewById(R.id.serch_input);
        serchInput.setHint("从 "+type+" 搜索：");
        RecyclerView recyclerView=findViewById(R.id.word_list_search);
        wordresult=getData();
        wordAdapter=new WordAdapter(wordresult,this);
        onTouchListener = new RecyclerTouchListener(this, recyclerView);
        onTouchListener.setIndependentViews(R.id.rowButton)
                .setViewsToFade(R.id.rowButton)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Word word=getData().get(position);

//                        Intent intent = new Intent(SearchWordActivity.this, WordDetailsActivity.class);
//                        intent.putExtra("word",word.get_id());
//                        startActivity(intent);

                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {


                    }
                })
                .setSwipeOptionViews(R.id.add, R.id.edit, R.id.change)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {

                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        final int id=wordList.get(position).get_id();
                        final int wordRank=wordList.get(position).getWordRank();
                        final String wordType=wordList.get(position).getWordType();
                        LayoutInflater inflater = getLayoutInflater();
                        //添加单词弹出框
                       if (viewID == R.id.edit) {

                        }
                        //删除弹出框
                        else if (viewID == R.id.change) {
                            builder = new AlertDialog.Builder(SearchWordActivity.this);
                            builder.setTitle("确认删除").setMessage("是否确认删除,删除后不可恢复！");
                            builder.setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    wordDao=new WordDao(SearchWordActivity.this);
                                    Word word=wordDao.find(id);
                                    Log.d("haha", "onClick: "+word.get_id());
                                    wordDao.delete(word);
                                    wordList.clear();
                                    wordList.addAll(getData());
                                    wordAdapter.notifyDataSetChanged();


                                }
                            })
                                    .setNegativeButton("取消",null);
                            builder.setIcon(R.drawable.slide_about_icon);
                            builder.show();
                        }
                    }
                });
        recyclerView.addOnItemTouchListener(onTouchListener);//添加监听器
        recyclerView.setAdapter(wordAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        serchInput.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        input=serchInput.getText().toString().trim();
        //System.out.println(input);
        wordresult.clear();
        wordresult.addAll(getData());
        wordAdapter.notifyDataSetChanged();//更新结果数据
    }

    private List<Word> getData() {
        wordList=wordDao.getSerchWords(input,type);
        System.out.println(wordList.size());
        return wordList;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}