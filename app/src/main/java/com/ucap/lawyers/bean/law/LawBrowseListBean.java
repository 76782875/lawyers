package com.ucap.lawyers.bean.law;

import java.util.List;

/**
 * Created by wekingwang on 2017/2/22.
 */

public class LawBrowseListBean {

    private List<RowBean> row;

    public List<RowBean> getRow() {
        return row;
    }

    public void setRow(List<RowBean> row) {
        this.row = row;
    }

    public static class RowBean {
        /**
         * id : 402880e95a649110015a64c1bf1f0001
         * name : 名称
         * groupname : 泰和泰律师事务所
         * firstname : 程守太
         * size : 2
         */

        private String id;
        private String name;
        private String groupname;
        private String firstname;
        private String size;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
