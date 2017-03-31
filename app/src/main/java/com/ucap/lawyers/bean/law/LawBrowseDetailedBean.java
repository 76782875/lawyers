package com.ucap.lawyers.bean.law;

import java.util.List;

/**
 * Created by wekingwang on 2017/2/23.
 */

public class LawBrowseDetailedBean {

    private List<RowBean> row;

    public List<RowBean> getRow() {
        return row;
    }

    public void setRow(List<RowBean> row) {
        this.row = row;
    }

    public static class RowBean {
        /**
         * content : 撒打算的撒的撒的撒谎
         * name : 名称
         * groupname : 泰和泰律师事务所
         * firstname : 程守太
         * contactid : 15101199910533886
         */

        private String content;
        private String name;
        private String groupname;
        private String firstname;
        private String contactid;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getContactid() {
            return contactid;
        }

        public void setContactid(String contactid) {
            this.contactid = contactid;
        }
    }
}
