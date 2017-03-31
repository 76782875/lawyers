package com.ucap.lawyers.bean.userLogin.userLawyer;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/16.
 * 公众咨询列表
 */

public class LawyerPublicConsultItem {
    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 32908385586c57c801586c78ab780001
         * title : 因为最近我妈妈总是咳嗽，一开始自己也不去打针吃药，抗了一周后发烧，然后不知道哪里出来了个自称开天眼的说我妈沾了晦气的东西让发个红包破财免灾我妈真的信了，给了200，然后还是在发烧，那人又说有四个鬼张口要848人民币，因为这件事我跟我妈吵架了我刚刚偷懒了一眼聊天记录，被我妈发现抢走了，不过我看到好像今天又转账4000，我想知道，这种人是不是犯法，诈骗！急！有没有人告诉我
         * AnswerConsultationUrl : http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!AnswerConsultationInfo.action?fifter=0
         * firstname : 程守太
         * photo : http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101199910533886
         * size : 3
         */

        private String id;
        private String title;
        private String AnswerConsultationUrl;
        private String firstname;
        private String photo;
        private int size;

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

        public String getAnswerConsultationUrl() {
            return AnswerConsultationUrl;
        }

        public void setAnswerConsultationUrl(String AnswerConsultationUrl) {
            this.AnswerConsultationUrl = AnswerConsultationUrl;
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

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
