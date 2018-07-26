package com.example.admin.videorecorddemo.test;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/3/19.
 */

public class DouyinBean implements Serializable {
    /**
     * ret : 1
     * message : success
     * data : [{"videoimg":"http://gcskedou.oss-cn-shanghai.aliyuncs.com/imgs/201804021233361941395nologin.png","videourl":"http://gcskedou.oss-cn-shanghai.aliyuncs.com/self/201804021233327953046nologin.mp4","like":3,"user_id":1028,"user_logo":"http://gcskedou.oss-cn-shanghai.aliyuncs.com/imgs/201804040107048042181nologin.png","user_name":"就","user_describe":"哦哦哦","video_id":161,"is_fallow":1,"like_status":0},{"videoimg":"http://gcskedou.oss-cn-shanghai.aliyuncs.com/imgs/201804111039126527190nologin.png","videourl":"http://gcskedou.oss-cn-shanghai.aliyuncs.com/self/201804111039100358850nologin.mp4","like":3,"user_id":1032,"user_logo":"http://gcskedou.oss-cn-shanghai.aliyuncs.com/icon/%E6%B3%A8%E5%86%8C/12.png","user_name":"此处为昵称18395997651","user_describe":null,"video_id":165,"is_fallow":1,"like_status":0},{"videoimg":"http://gcskedou.oss-cn-shanghai.aliyuncs.com/imgs/201804130159552874651nologin.png","videourl":"http://gcskedou.oss-cn-shanghai.aliyuncs.com/self/201804130159399034476nologin.mp4","like":2,"user_id":1038,"user_logo":"http://gcskedou.oss-cn-shanghai.aliyuncs.com/photos/android_photos_mtd2f020ecdee7170b5d1b04a85e6db87e1523672341.jpg","user_name":"天堂地狱","user_describe":"蝌蚪五线谱简谱版","video_id":168,"is_fallow":1,"like_status":0},{"videoimg":"","videourl":"","like":2,"user_id":1030,"user_logo":"http://modernimg.oss-cn-hangzhou.aliyuncs.com/photos/android_photos_mt8f50a00221f3e8415cda64509c8297181520243026.jpg","user_name":"暴躁的秀发","user_describe":"hello","video_id":0,"is_fallow":1,"like_status":0},{"videoimg":"http://gcskedou.oss-cn-shanghai.aliyuncs.com/imgs/201804021233361941395nologin.png","videourl":"http://gcskedou.oss-cn-shanghai.aliyuncs.com/self/201804021233327953046nologin.mp4","like":1,"user_id":7026,"user_logo":"http://gcskedou.oss-cn-shanghai.aliyuncs.com/imgs/201803300433314256555nologin.png","user_name":"胡古古","user_describe":"123123123123","video_id":160,"is_fallow":1,"like_status":0},{"videoimg":"","videourl":"","like":1,"user_id":7373,"user_logo":"http://modernimg.oss-cn-hangzhou.aliyuncs.com/photos/pc_photos_15056996595_1515575271571726.jpg","user_name":"小爷","user_describe":"","video_id":0,"is_fallow":1,"like_status":0},{"videoimg":"","videourl":"","like":2,"user_id":1030,"user_logo":"http://modernimg.oss-cn-hangzhou.aliyuncs.com/photos/android_photos_mt8f50a00221f3e8415cda64509c8297181520243026.jpg","user_name":"暴躁的秀发","user_describe":"hello","video_id":0,"is_fallow":1,"like_status":0}]
     * pages : 1
     * isNull : 0
     */

    private int ret;
    private String message;
    private int pages;
    private int isNull;
    private List<DataBean> data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getIsNull() {
        return isNull;
    }

    public void setIsNull(int isNull) {
        this.isNull = isNull;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * videoimg : http://gcskedou.oss-cn-shanghai.aliyuncs.com/imgs/201804021233361941395nologin.png
         * videourl : http://gcskedou.oss-cn-shanghai.aliyuncs.com/self/201804021233327953046nologin.mp4
         * like : 3
         * user_id : 1028
         * user_logo : http://gcskedou.oss-cn-shanghai.aliyuncs.com/imgs/201804040107048042181nologin.png
         * user_name : 就
         * user_describe : 哦哦哦
         * video_id : 161
         * is_fallow : 1
         * like_status : 0
         */

        private String videoimg;
        private String videourl;
        private int like;
        private int user_id;
        private String user_logo;
        private String user_name;
        private String user_describe;
        private int video_id;
        private int is_fallow;
        private int like_status;

        public String getVideoimg() {
            return videoimg;
        }

        public void setVideoimg(String videoimg) {
            this.videoimg = videoimg;
        }

        public String getVideourl() {
            return videourl;
        }

        public void setVideourl(String videourl) {
            this.videourl = videourl;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_logo() {
            return user_logo;
        }

        public void setUser_logo(String user_logo) {
            this.user_logo = user_logo;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_describe() {
            return user_describe;
        }

        public void setUser_describe(String user_describe) {
            this.user_describe = user_describe;
        }

        public int getVideo_id() {
            return video_id;
        }

        public void setVideo_id(int video_id) {
            this.video_id = video_id;
        }

        public int getIs_fallow() {
            return is_fallow;
        }

        public void setIs_fallow(int is_fallow) {
            this.is_fallow = is_fallow;
        }

        public int getLike_status() {
            return like_status;
        }

        public void setLike_status(int like_status) {
            this.like_status = like_status;
        }
    }
}
