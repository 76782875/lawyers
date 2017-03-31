package com.ucap.lawyers.bean.ranking.answerClassify.ranking;

import java.util.List;

/**
 * Created by wekingwang on 2017/3/15.
 */

public class RankingLawyerAnswerListBean {

    private List<RowBean> row;

    public List<RowBean> getRow() {
        return row;
    }

    public void setRow(List<RowBean> row) {
        this.row = row;
    }

    public static class RowBean {
        /**
         * id : 3290838558e2fc890158e6e5540a0004
         * title : 交通事故
         * firstname : 自娱自乐
         * photo : http://wx.qlogo.cn/mmopen/tV1vWFgUKuOAyNCy4skKicjF9jEvWXdHBLAn1IC6nurib2NmXodlMEu9gQCJCCHXdiapczfP3A1hgoALEBp3ZrRU5JVRSb46Kep/0
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
