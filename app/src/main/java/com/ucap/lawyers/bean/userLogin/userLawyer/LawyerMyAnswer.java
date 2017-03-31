package com.ucap.lawyers.bean.userLogin.userLawyer;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/17.
 */

public class LawyerMyAnswer {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * createtime : 2016-11-16
         * content : 侵犯肖像权
         * id : 32908385586caadd01586cc85285000a
         * publicid : 3290838558673c260158673cc32c0001
         */

        private String createtime;
        private String content;
        private String id;
        private String publicid;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPublicid() {
            return publicid;
        }

        public void setPublicid(String publicid) {
            this.publicid = publicid;
        }
    }
}
