package com.benben.carmanager.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.benben.carmanager.R;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/**
 * Android x SwipeRefreshLayout 二次封装
 * 完成功能点
 * 1.完成加载更多功能 且刷新后自动关闭动画
 * 2.完成下拉刷新后自动关闭自带刷新动画功能
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MySwipeRefreshLayout";

    //每页显示的数量
    private static final int pageSize = 10;
    //当前是否完全显示了最后一个item
    private boolean isshowLast = false;
    //手指是否是向上滑动
    private boolean isToUp = false;
    //是否正在加载更多中...
    private boolean isLoadingMore = false;
    private SwipeRecyclerView mRecyclerView;
    private Context mContext;
    private int startY, endY;

    //最后一个item 已经完全显示 并且布局管理器中的数量已经大于或登录 pageSize  满足上拉加载的条件
    private boolean isLastViewAndCanLoadMore = false;


    private OnLoadMoreListener onLoadMoreListener;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (mRecyclerView != null && mRecyclerView.getFooterCount() > 0) {
                    mRecyclerView.removeFooterView(mFootView);
                    isLoadingMore = false;
                }
            } else if (msg.what == 2) {
                //自动关闭下拉刷新的提示动画
                setRefreshing(false);
            }
        }
    };
    private View mFootView;
    private OnRefreshListener onRefreshListener;


    public MySwipeRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mRecyclerView == null) {
            findRecyclerView(this);
        }

    }

    private void findRecyclerView(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() > 0) {
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View child = viewGroup.getChildAt(i);
                    if (child instanceof SwipeRecyclerView) {
                        mRecyclerView = (SwipeRecyclerView) child;
                        if (mRecyclerView != null) {
                            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                    Log.d(TAG, "onScrolled: dy == " + dy);
                                    if (dy > 0) { //向上滚动
                                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                                        if (layoutManager instanceof LinearLayoutManager) {
                                            LinearLayoutManager lm = (LinearLayoutManager) layoutManager;
                                            int visibleItemCount = lm.getChildCount();
                                            int totalItemCount = lm.getItemCount();
                                            int firstVisibleItemPosition = lm.findFirstVisibleItemPosition();
                                            int lastCompletelyVisibleItemPosition = lm.findLastCompletelyVisibleItemPosition();
                                            Log.d(TAG, "onScrolled: 布局管理器获取的可见子view的数量 == " + visibleItemCount);
                                            Log.d(TAG, "onScrolled: 布局管理器获取的总的子view的数量 == " + totalItemCount);
                                            Log.d(TAG, "onScrolled: 布局管理器中获取第一个显示子view的位置 == " + firstVisibleItemPosition);
                                            Log.d(TAG, "onScrolled: 布局管理器中获取完整显示最后一个子view的位置 == " + lastCompletelyVisibleItemPosition);

                                            if ((totalItemCount - 1) == lastCompletelyVisibleItemPosition) {
                                                Log.d(TAG, "onScrolled: 已经滚动到底部了...");
                                                if (totalItemCount >= pageSize) {
                                                    //判断当前总的数据 是否已经大于等于规定的每页最少显示数量
                                                    isLastViewAndCanLoadMore = true;
                                                    canLoadMore();
                                                } else {
                                                    isLastViewAndCanLoadMore = false;
                                                }

                                            } else {
                                                isLastViewAndCanLoadMore = false;
                                            }
                                        } else {
                                            isLastViewAndCanLoadMore = false;
                                        }

                                    } else if (dy == 0) {
                                        isLastViewAndCanLoadMore = true;
                                    } else {
                                        isLastViewAndCanLoadMore = false;
                                    }
                                }
                            });
                        }
                    } else {
                        findRecyclerView(child);
                    }
                }
            }
        }

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                endY = (int) ev.getY();
                int distanceY = startY - endY;
                if (distanceY > 0) {
                    canLoadMore();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }


    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: 下拉刷新被调用了... ");
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
            mHandler.sendEmptyMessageDelayed(2, 2000);
        }
    }

    /**
     * 已经滑到了底部 判断是否可以加载更多数据
     */
    private void canLoadMore() {
        Log.d(TAG, "canLoadMore: 进入判断是否加载更多...");
        if (!isLoadingMore && mRecyclerView != null && isLastViewAndCanLoadMore && recyclerViewIsShowLastItem()) {
            Log.d(TAG, "canLoadMore: 加载更多中...");

            isLoadingMore = true;
            mFootView = LayoutInflater.from(mContext).inflate(R.layout.mwrap_refreshlayout_footview, mRecyclerView, false);
            mRecyclerView.addFooterView(mFootView);

            //加载数据
            loadData();
            //两秒之后自动关闭
            mHandler.sendEmptyMessageDelayed(1, 2000);

        }
    }


    /**
     * 用于复测recyclerview 是否完整的滑到了底部
     */
    private boolean recyclerViewIsShowLastItem() {
        if (mRecyclerView == null) {
            return false;
        }

        LinearLayoutManager lm;
        try {
            lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        int totalItemCount = lm.getItemCount();
        int lastCompletelyVisibleItemPosition = lm.findLastCompletelyVisibleItemPosition();


        if((totalItemCount - 1) == lastCompletelyVisibleItemPosition){
            if (totalItemCount >= pageSize) {
                //判断当前总的数据 是否已经大于等于规定的每页最少显示数量

                return true;
            } else {
                return false;
            }

        }else{
            return false;
        }

    }

    /**
     * 执行加载数据
     */
    private void loadData() {
        if (onLoadMoreListener != null) {
            onLoadMoreListener.onLoadMore();
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void setOnRefreshListener(@Nullable OnRefreshListener listener) {
        super.setOnRefreshListener(this);
        onRefreshListener = listener;
    }

    public interface OnLoadMoreListener {
        //开始加载更多
        void onLoadMore();
    }
}
