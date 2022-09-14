package com.benben.carmanager.utils;

public class Api {

            public static final String baseUrl = "";

    public static final String api_getHomeMenu = ""; //查询菜单
    public static  boolean isShowReview =false; //是否弹出了复习提醒




    public static String TOKEN = "";
    public static final String USER_TOKEN_KEY = "";
    public static final String USER_ID_KEY = "";
    public static String api_create_order = "";//创建订单
    public static String api_getCateMenu = ""; //获取分类列表 用于管理分类页面
    public static String api_add_menucate = "api/mergeMenu"; //添加菜单分类
    public static String api_add_goods = "api/mergeProductInfo"; //添加菜单分类
    public static String api_deleteMainCate = "api/deleteMainMenu"; //添加菜单分类
    public static String request_delete_goods = "api/deleteProduct";//删除产品
    public static String request_queryRunOrder = "api/queryOrder/1";//查询运行中的订单
    public static String request_queryCompleteOrder = "api/queryOrder/2";//查询完成的订单
    public static String request_operationOrder = "api/operationOrder";//操作订单
    public static String api_start_countdown = "api/operationOrder/setTime";//操作订单
    public static String request_history_order = "api/queryOrder/condition";//获取历史订单


    public static final String API_REQUEST_REGISTER = "api/register";//注册
    public static final String API_REQUEST_LOGIN = "api/login";//登录接口
    public static final String API_GET_USERINFO = "api/userInfo";//获取用户信息
    public static final String API_LOGIN_OUT = "api/logOut";//退出登录

    public static final String API_UPLOAD = "api/upload";//上传图片
    public static final String API_SAVE_USERINFO = "api/mergeUserInfo";//保存修改用户信息
    public static final String API_APP_VERSION = "api/appVersion";//获取版本信息

    public static final String REQUEST_MSG_INFO = "api/messageInfo";//请求消息模块的信息


    public static boolean isRegister = false;
    //用户id
    public static int userId = 0;
    //历史最高成绩
    public static int maxScore;
    //全部分页规定每页显示10条数据
    public static final int pageSize = 10;

    //请求拍照的请求码
    public static final int REQUEST_IMAGE_CAPTURE = 0x102;//请求拍照


    public static String CarNumberKey = "car_number";
    public static String CAR_BRAND_KEY = "car_brand_key";
}
