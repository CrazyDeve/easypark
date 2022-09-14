package com.benben.carmanager.fragment;
/*
   这里存放单词列表的碎片类
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.carmanager.R;
import com.benben.carmanager.adapter.WordAdapter;
import com.benben.carmanager.dao.WordDao;
import com.benben.carmanager.dao.WordRecordDao;
import com.benben.carmanager.dao.WordTypeDao;
import com.benben.carmanager.model.Word;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{
    private static final String TAG = "TabFragment";
    private List<Word> wordList=new ArrayList<Word>();//单词列表数据
    private View view;
    private String mTitle;//单词类型
    private String mtype;//单词书
    private RecyclerTouchListener onTouchListener;//侧滑菜单
    private AlertDialog.Builder builder;//添加单词本对话框
    private Word word0;//全局变量，记录单词结果列表
    private boolean[] checkedItems;//记录各个列表项的状态
    private String[] items;     //记录列表项要选择的单词本
    private View dialog;
    private RapidFloatingActionLayout rfaLayout;

    private RapidFloatingActionHelper rfabHelper;
    private WordAdapter wordAdapter;
    private WordDao wordDao;
    private WordTypeDao wordTypeDao;


    //这个构造方法是便于各导航同时调用一个fragment
    public TabFragment(String title, String type){
        mTitle=title;
        mtype=type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.tab_fragment,container,false);
        wordDao=new WordDao(getContext());
        wordTypeDao=new WordTypeDao(getContext());
        RecyclerView recyclerView=view.findViewById(R.id.word_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new WordAdapter(getData(),getContext()));
        onTouchListener = new RecyclerTouchListener(getActivity(), recyclerView);
        onTouchListener.setIndependentViews(R.id.rowButton)
                .setViewsToFade(R.id.rowButton)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Word word=getData().get(position);

//                        Intent intent = new Intent(getContext(), WordDetailsActivity.class);
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



            //删除弹出框
            if (viewID == R.id.change) {
                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("确认删除").setMessage("是否确认删除,删除后不可恢复！");
                builder.setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wordDao=new WordDao(getContext());
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
        rfaLayout=view.findViewById(R.id.rfal);

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getContext());
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();


        rfaContent
                .setItems(items)
                .setIconShadowColor(0xff888888)
        ;

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //获取不同类型单词数据
    private List<Word> getData() {
        List<Word> data = null;
        WordRecordDao wordRecordDao =new WordRecordDao(getContext());
        WordDao wordDao=new WordDao(getContext());
        switch(mTitle) {


        }

        return data;
    }

    @Override
    public void onResume() {

        RecyclerView recyclerView=view.findViewById(R.id.word_list);
        recyclerView.addOnItemTouchListener(onTouchListener);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        wordList=getData();
        wordAdapter=new WordAdapter(wordList,getContext());
        recyclerView.setAdapter(wordAdapter);
        super.onResume();
    }
    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch (position){
            case 0:


        }


    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        switch (position){
            case 0:

                break;

        }
        rfabHelper.toggleContent();
    }


}
