package com.ucap.lawyers.bean.userLogin.userLawyer;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/15.
 * 信息公告
 */

public class LawyerMessageNotice {


    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 5651
         * optname : 周芳
         * pubdate : 无
         * subject : 各律师事务所：以后新执业律师的法律资格证调档统一由司法局政务中心窗口办理，省外异动到成都的律师可不用再调法律资格证案。
         * pathobjs : [{"imgname":"附件1.成都市律师档案信息表.doc","path":"http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/fileout?id=164960","imsize":36352}]
         */

        private int id;
        private String optname;
        private String pubdate;
        private String subject;
        private List<PathobjsBean> pathobjs;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOptname() {
            return optname;
        }

        public void setOptname(String optname) {
            this.optname = optname;
        }

        public String getPubdate() {
            return pubdate;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public List<PathobjsBean> getPathobjs() {
            return pathobjs;
        }

        public void setPathobjs(List<PathobjsBean> pathobjs) {
            this.pathobjs = pathobjs;
        }

        public static class PathobjsBean {
            /**
             * imgname : 附件1.成都市律师档案信息表.doc
             * path : http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/fileout?id=164960
             * imsize : 36352
             */

            private String imgname;
            private String path;
            private int imsize;

            public String getImgname() {
                return imgname;
            }

            public void setImgname(String imgname) {
                this.imgname = imgname;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public int getImsize() {
                return imsize;
            }

            public void setImsize(int imsize) {
                this.imsize = imsize;
            }
        }
    }
}
