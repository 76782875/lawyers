package com.ucap.lawyers.bean.ranking.answerClassify;

import java.util.List;

/**
 * Created by wekingwang on 2017/2/9.
 */

public class AnswerClassifyListBean {

    private List<RowBean> row;

    public List<RowBean> getRow() {
        return row;
    }

    public void setRow(List<RowBean> row) {
        this.row = row;
    }

    public static class RowBean {
        /**
         * id : 402880f05914d0fd015914e490020001
         * title : 测试标题测试标题测试标题测试标题测试标题测试标题测试标题测试标题测试标题
         * firstname : 程守太
         * photo : http://wx.qlogo.cn/mmopen/fy7p4UZ6O2M3CBsEOib7GXDPL3vDJP0AUx2rzxmGzFL93XmT8ejZLsQIUNJLMY1HxFELdNib59iaELOAJzO532NrYaBa3jcdCWY/0
         */

        private String id;
        private String title;
        private String firstname;
        private String photo;

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

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
