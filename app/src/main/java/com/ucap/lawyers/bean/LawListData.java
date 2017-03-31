package com.ucap.lawyers.bean;

import java.util.List;

/**
 * Created by wekingwang on 16/10/20.
 */

public class LawListData {

    /**
     * content : http://www.cdjustice.chengdu.gov.cn/ssh23/content/zcfg/lsgzfg_five.html
     * id : 1
     * name : 律师工作法律法规-律师事务所年度检查考核办法
     */

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private String content;
        private int id;
        private String name;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
