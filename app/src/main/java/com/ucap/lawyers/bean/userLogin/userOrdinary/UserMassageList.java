package com.ucap.lawyers.bean.userLogin.userOrdinary;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/27.
 */

public class UserMassageList {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * createtime : 2016-11-25
         * id : 3290838558961cc50158996f07fe0002
         * title : 测试
         * MessageAlertInfoUrl : http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!MessageAlertInfo.action?fifter=0
         */

        private String createtime;
        private String id;
        private String title;
        private String MessageAlertInfoUrl;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessageAlertInfoUrl() {
            return MessageAlertInfoUrl;
        }

        public void setMessageAlertInfoUrl(String MessageAlertInfoUrl) {
            this.MessageAlertInfoUrl = MessageAlertInfoUrl;
        }
    }
}