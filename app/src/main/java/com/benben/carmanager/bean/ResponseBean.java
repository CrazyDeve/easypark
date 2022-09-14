package com.benben.carmanager.bean;

import java.io.Serializable;
import java.util.List;

public class ResponseBean {


    /**
     * code : 0
     * data : {"orderInfoList":[{"basic":{"id":"bfe1c872-fdb5-4144-8ef6-b95b35f5ba90","placeTime":"2020-02-28 20:45:30","payTime":null,"payUserType":1,"userId":"1","commercialId":"1","payPrice":28,"detailId":"9ac7e6e5-862a-4c8c-a656-4b375c4fdb1f","status":1,"remark":"1"},"detailList":[{"id":"d38209c7-df7b-442b-9d23-9a9b57c930a1","orderId":"9ac7e6e5-862a-4c8c-a656-4b375c4fdb1f","userId":"1","commercialId":"1","shopId":1,"shopName":"水饺","categoryId":3,"categoryName":"芹菜猪肉","specificationId":1,"specificationName":"小碗","setStartTime":"","payPrice":10,"payNum":2,"remark":""},{"id":"8d652b7a-3444-4d2a-8196-91985ea6b16e","orderId":"9ac7e6e5-862a-4c8c-a656-4b375c4fdb1f","userId":"1","commercialId":"1","shopId":1,"shopName":"水饺","categoryId":4,"categoryName":"韭菜猪肉","specificationId":1,"specificationName":"小碗","setStartTime":"","payPrice":8,"payNum":1,"remark":""}]},{"basic":{"id":"ac8750d0-25ed-411c-a574-eaf0dbe15626","placeTime":"2020-02-29 23:11:47","payTime":null,"payUserType":1,"userId":"1","commercialId":"1","payPrice":972,"detailId":"e78e6d62-8095-43ac-8717-31e53f063c5a","status":1,"remark":"1"},"detailList":[{"id":"8105a38c-b6bf-4c50-bef0-7dccd00989a0","orderId":"e78e6d62-8095-43ac-8717-31e53f063c5a","userId":"1","commercialId":"1","shopId":1,"shopName":"水饺","categoryId":4,"categoryName":"韭菜猪肉","specificationId":6,"specificationName":"大碗","setStartTime":null,"payPrice":108,"payNum":9,"remark":null}]}]}
     * message : 操作成功
     */

    private String code;
    private DataBean data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        private List<OrderInfoListBean> orderInfoList;
        private String token;
        private UserInfoBean user;
        private String imgUrl;
        private String versionCode;//版本号
        private String versionName;//版本名
        private String downloadUrl;//apk下载链接
        private String updateContent;//apk更新内容
        private String isForce;//是否强制升级
        private SalesDataBean salesData;
        private MsgInfoBean msgInfo;


        public void setMsgInfo(MsgInfoBean msgInfo) {
            this.msgInfo = msgInfo;
        }

        public MsgInfoBean getMsgInfo() {
            return msgInfo;
        }

        public static class MsgInfoBean{

            /**
             * sysMsg : {"msgIcon":"www.touxiang.com/pics/123.png","msgType":"manager","msgContent":"智能点餐系统上线了，赶快去下载吧","downloadUrl":"www.baidu.com","responseType":"download"}
             * msgProject : [{"photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584772491372&di=b6e152c7d360a2619a63f66c51552067&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2Ff11f3a292df5e0fe37ad5cca566034a85fdf7240.jpg","title":"鸡翅怎么做好吃_鸡翅做法 【心食谱】","create_time":"1584761916000","project_name":"美食专题","url":"https://home.meishichina.com/recipe-368930.html"},{"photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584772491372&di=b6e152c7d360a2619a63f66c51552067&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2Ff11f3a292df5e0fe37ad5cca566034a85fdf7240.jpg","title":"鸡翅怎么做好吃_鸡翅做法 【心食谱】","create_time":"1584761916000","project_name":"美食专题","url":"https://home.meishichina.com/recipe-368930.html"},{"photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584772491372&di=b6e152c7d360a2619a63f66c51552067&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2Ff11f3a292df5e0fe37ad5cca566034a85fdf7240.jpg","title":"鸡翅怎么做好吃_鸡翅做法 【心食谱】","create_time":"1584761916000","project_name":"美食专题","url":"https://home.meishichina.com/recipe-368930.html"},{"photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584772491372&di=b6e152c7d360a2619a63f66c51552067&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2Ff11f3a292df5e0fe37ad5cca566034a85fdf7240.jpg","title":"鸡翅怎么做好吃_鸡翅做法 【心食谱】","create_time":"1584761916000","project_name":"美食专题","url":"https://home.meishichina.com/recipe-368930.html"},{"photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584772491372&di=b6e152c7d360a2619a63f66c51552067&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2Ff11f3a292df5e0fe37ad5cca566034a85fdf7240.jpg","title":"鸡翅怎么做好吃_鸡翅做法 【心食谱】","create_time":"1584761916000","project_name":"美食专题","url":"https://home.meishichina.com/recipe-368930.html"},{"photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584772491372&di=b6e152c7d360a2619a63f66c51552067&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2Ff11f3a292df5e0fe37ad5cca566034a85fdf7240.jpg","title":"鸡翅怎么做好吃_鸡翅做法 【心食谱】","create_time":"1584761916000","project_name":"美食专题","url":"https://home.meishichina.com/recipe-368930.html"},{"photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584772491372&di=b6e152c7d360a2619a63f66c51552067&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2Ff11f3a292df5e0fe37ad5cca566034a85fdf7240.jpg","title":"鸡翅怎么做好吃_鸡翅做法 【心食谱】","create_time":"1584761916000","project_name":"美食专题","url":"https://home.meishichina.com/recipe-368930.html"}]
             */

            private SysMsgBean sysMsg;
            private List<MsgProjectBean> msgProject;

            public SysMsgBean getSysMsg() {
                return sysMsg;
            }

            public void setSysMsg(SysMsgBean sysMsg) {
                this.sysMsg = sysMsg;
            }

            public List<MsgProjectBean> getMsgProject() {
                return msgProject;
            }

            public void setMsgProject(List<MsgProjectBean> msgProject) {
                this.msgProject = msgProject;
            }

            public static class SysMsgBean {
                /**
                 * msgIcon : www.touxiang.com/pics/123.png
                 * msgType : manager
                 * msgContent : 智能点餐系统上线了，赶快去下载吧
                 * downloadUrl : www.baidu.com
                 * responseType : download
                 */

                private String msgIcon;
                private String msgType;
                private String msgContent;
                private String downloadUrl;
                private String responseType;

                public String getMsgIcon() {
                    return msgIcon;
                }

                public void setMsgIcon(String msgIcon) {
                    this.msgIcon = msgIcon;
                }

                public String getMsgType() {
                    return msgType;
                }

                public void setMsgType(String msgType) {
                    this.msgType = msgType;
                }

                public String getMsgContent() {
                    return msgContent;
                }

                public void setMsgContent(String msgContent) {
                    this.msgContent = msgContent;
                }

                public String getDownloadUrl() {
                    return downloadUrl;
                }

                public void setDownloadUrl(String downloadUrl) {
                    this.downloadUrl = downloadUrl;
                }

                public String getResponseType() {
                    return responseType;
                }

                public void setResponseType(String responseType) {
                    this.responseType = responseType;
                }
            }

            public static class MsgProjectBean {
                /**
                 * photo : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584772491372&di=b6e152c7d360a2619a63f66c51552067&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2Ff11f3a292df5e0fe37ad5cca566034a85fdf7240.jpg
                 * title : 鸡翅怎么做好吃_鸡翅做法 【心食谱】
                 * create_time : 1584761916000
                 * project_name : 美食专题
                 * url : https://home.meishichina.com/recipe-368930.html
                 */

                private String photo;
                private String title;
                private String createTime;
                private String projectName;
                private String url;

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getProjectName() {
                    return projectName;
                }

                public void setProjectName(String projectName) {
                    this.projectName = projectName;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public String getUpdateContent() {
            return updateContent;
        }

        public void setUpdateContent(String updateContent) {
            this.updateContent = updateContent;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getIsForce() {
            return isForce;
        }

        public void setIsForce(String isForce) {
            this.isForce = isForce;
        }

        public SalesDataBean getSalesData() {
            return salesData;
        }

        public void setSalesData(SalesDataBean salesData) {
            this.salesData = salesData;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public UserInfoBean getUser() {
            return user;
        }

        public void setUser(UserInfoBean user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<OrderInfoListBean> getOrderInfoList() {
            return orderInfoList;
        }

        public void setOrderInfoList(List<OrderInfoListBean> orderInfoList) {
            this.orderInfoList = orderInfoList;
        }


        public static  class SalesDataBean implements Serializable{


            /**
             * yesterday : {"number":0,"salesNumber":0}
             * today : {"number":0,"salesNumber":0}
             */

            private YesterdayBean yesterday;
            private TodayBean today;

            public YesterdayBean getYesterday() {
                return yesterday;
            }

            public void setYesterday(YesterdayBean yesterday) {
                this.yesterday = yesterday;
            }

            public TodayBean getToday() {
                return today;
            }

            public void setToday(TodayBean today) {
                this.today = today;
            }

            public static class YesterdayBean {
                /**
                 * number : 0
                 * salesNumber : 0
                 */

                public String number;
                public String salesNumber;


            }

            public static class TodayBean {
                /**
                 * number : 0
                 * salesNumber : 0
                 */

                public String number;
                public String salesNumber;


            }
        }

        public static class UserInfoBean implements Serializable {
            private int id;
            private String phone;
            private String userInfo;
            private InfoBean info;

            public String getUserInfo() {
                return userInfo;
            }
            public void setUserInfo(String userInfo) {
                this.userInfo = userInfo;
            }
            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public static class InfoBean implements Serializable{
                private int id;
                private String phone;
                private String iconUrl;//头像
                private String shopName;
                private String desc;//店铺简介

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getIconUrl() {
                    return iconUrl;
                }

                public void setIconUrl(String iconUrl) {
                    this.iconUrl = iconUrl;
                }

                public String getShopName() {
                    return shopName;
                }

                public void setShopName(String shopName) {
                    this.shopName = shopName;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }
            }
        }

        public static class OrderInfoListBean implements Serializable{
            /**
             * basic : {"id":"bfe1c872-fdb5-4144-8ef6-b95b35f5ba90","placeTime":"2020-02-28 20:45:30","payTime":null,"payUserType":1,"userId":"1","commercialId":"1","payPrice":28,"detailId":"9ac7e6e5-862a-4c8c-a656-4b375c4fdb1f","status":1,"remark":"1"}
             * detailList : [{"id":"d38209c7-df7b-442b-9d23-9a9b57c930a1","orderId":"9ac7e6e5-862a-4c8c-a656-4b375c4fdb1f","userId":"1","commercialId":"1","shopId":1,"shopName":"水饺","categoryId":3,"categoryName":"芹菜猪肉","specificationId":1,"specificationName":"小碗","setStartTime":"","payPrice":10,"payNum":2,"remark":""},{"id":"8d652b7a-3444-4d2a-8196-91985ea6b16e","orderId":"9ac7e6e5-862a-4c8c-a656-4b375c4fdb1f","userId":"1","commercialId":"1","shopId":1,"shopName":"水饺","categoryId":4,"categoryName":"韭菜猪肉","specificationId":1,"specificationName":"小碗","setStartTime":"","payPrice":8,"payNum":1,"remark":""}]
             */

            private BasicBean basic;
            private List<DetailListBean> detailList;

            public BasicBean getBasic() {
                return basic;
            }

            public void setBasic(BasicBean basic) {
                this.basic = basic;
            }

            public List<DetailListBean> getDetailList() {
                return detailList;
            }

            public void setDetailList(List<DetailListBean> detailList) {
                this.detailList = detailList;
            }

            public static class BasicBean implements Serializable{
                /**
                 * id : bfe1c872-fdb5-4144-8ef6-b95b35f5ba90
                 * placeTime : 2020-02-28 20:45:30
                 * payTime : null
                 * payUserType : 1
                 * userId : 1
                 * commercialId : 1
                 * payPrice : 28
                 * detailId : 9ac7e6e5-862a-4c8c-a656-4b375c4fdb1f
                 * status : 1
                 * remark : 1
                 */

                private String id;
                private String placeTime;
                private Object payTime;
                private int payUserType;
                private String userId;
                private String commercialId;
                private double payPrice;
                private String detailId;
                private int status;
                private String remark;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getPlaceTime() {
                    return placeTime;
                }

                public void setPlaceTime(String placeTime) {
                    this.placeTime = placeTime;
                }

                public Object getPayTime() {
                    return payTime;
                }

                public void setPayTime(Object payTime) {
                    this.payTime = payTime;
                }

                public int getPayUserType() {
                    return payUserType;
                }

                public void setPayUserType(int payUserType) {
                    this.payUserType = payUserType;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public String getCommercialId() {
                    return commercialId;
                }

                public void setCommercialId(String commercialId) {
                    this.commercialId = commercialId;
                }

                public double getPayPrice() {
                    return payPrice;
                }

                public void setPayPrice(double payPrice) {
                    this.payPrice = payPrice;
                }

                public String getDetailId() {
                    return detailId;
                }

                public void setDetailId(String detailId) {
                    this.detailId = detailId;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }
            }

            public static class DetailListBean implements Serializable{
                /**
                 * id : d38209c7-df7b-442b-9d23-9a9b57c930a1
                 * orderId : 9ac7e6e5-862a-4c8c-a656-4b375c4fdb1f
                 * userId : 1
                 * commercialId : 1
                 * shopId : 1
                 * shopName : 水饺
                 * categoryId : 3
                 * categoryName : 芹菜猪肉
                 * specificationId : 1
                 * specificationName : 小碗
                 * setStartTime :
                 * payPrice : 10
                 * payNum : 2
                 * remark :
                 */

                private String id;
                private String orderId;
                private String userId;
                private String commercialId;
                private int shopId;
                private String shopName;
                private int categoryId;
                private String categoryName;
                private int specificationId;
                private String specificationName;
                private String setStartTime;
                private double payPrice;
                private int payNum;
                private String remark;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getOrderId() {
                    return orderId;
                }

                public void setOrderId(String orderId) {
                    this.orderId = orderId;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public String getCommercialId() {
                    return commercialId;
                }

                public void setCommercialId(String commercialId) {
                    this.commercialId = commercialId;
                }

                public int getShopId() {
                    return shopId;
                }

                public void setShopId(int shopId) {
                    this.shopId = shopId;
                }

                public String getShopName() {
                    return shopName;
                }

                public void setShopName(String shopName) {
                    this.shopName = shopName;
                }

                public int getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(int categoryId) {
                    this.categoryId = categoryId;
                }

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public int getSpecificationId() {
                    return specificationId;
                }

                public void setSpecificationId(int specificationId) {
                    this.specificationId = specificationId;
                }

                public String getSpecificationName() {
                    return specificationName;
                }

                public void setSpecificationName(String specificationName) {
                    this.specificationName = specificationName;
                }

                public String getSetStartTime() {
                    return setStartTime;
                }

                public void setSetStartTime(String setStartTime) {
                    this.setStartTime = setStartTime;
                }

                public double getPayPrice() {
                    return payPrice;
                }

                public void setPayPrice(double payPrice) {
                    this.payPrice = payPrice;
                }

                public int getPayNum() {
                    return payNum;
                }

                public void setPayNum(int payNum) {
                    this.payNum = payNum;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }
            }
        }
    }
}
