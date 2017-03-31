package com.ucap.lawyers.bean.userLogin.userLawyer;

import java.util.List;

/**
 * Created by wekingwang on 2016/12/22.
 */

public class LawyerMyCommonlyJournal {
    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * content : 无
         * files : [{"ramark":"新建文本文档.txt","path":"http://localhost:9527/scslsxt/servlet/fileout?id=40998"},{"ramark":"新建文本文档.txt","path":"http://localhost:9527/scslsxt/servlet/fileout?id=40998"}]
         * operate_time : 2016-12-2
         * archchoose : 调查
         */

        private String content;
        private String operate_time;
        private String archchoose;
        private List<FilesBean> files;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getOperate_time() {
            return operate_time;
        }

        public void setOperate_time(String operate_time) {
            this.operate_time = operate_time;
        }

        public String getArchchoose() {
            return archchoose;
        }

        public void setArchchoose(String archchoose) {
            this.archchoose = archchoose;
        }

        public List<FilesBean> getFiles() {
            return files;
        }

        public void setFiles(List<FilesBean> files) {
            this.files = files;
        }

        public static class FilesBean {
            /**
             * ramark : 新建文本文档.txt
             * path : http://localhost:9527/scslsxt/servlet/fileout?id=40998
             */

            private String ramark;
            private String path;

            public String getRamark() {
                return ramark;
            }

            public void setRamark(String ramark) {
                this.ramark = ramark;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }
        }
    }
}
