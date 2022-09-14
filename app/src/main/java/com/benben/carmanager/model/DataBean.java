package com.benben.carmanager.model;

import com.benben.carmanager.R;

import java.util.ArrayList;
import java.util.List;



public class DataBean {

    public int imageUrl;

    public static List<DataBean> getTestData() {
        List<DataBean> data = new ArrayList<>();
        DataBean dataBean = new DataBean();
        dataBean.imageUrl = R.mipmap.icon_banner1;
        data.add(dataBean);

        dataBean = new DataBean();
        dataBean.imageUrl = R.mipmap.icon_banner2;
        data.add(dataBean);

        dataBean = new DataBean();
        dataBean.imageUrl = R.mipmap.icon_banner3;
        data.add(dataBean);

        dataBean = new DataBean();
        dataBean.imageUrl = R.mipmap.icon_banner4;
        data.add(dataBean);

        dataBean = new DataBean();
        dataBean.imageUrl = R.mipmap.icon_banner5;
        data.add(dataBean);
        return data;
    }
}
