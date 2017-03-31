package com.ucap.lawyers.bean.userLogin.userLawyer;

import java.util.List;

/**
 * Created by wekingwang on 2016/11/16.
 */

public class LawyerAnswerProblem {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * createtime : 2016-12-08
         * id : 3290838558b869e40158dd5185df001c
         * title : 朋友的车开出去违章可以拿我的驾照去处理吗？
         * answerurl : http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!AnswerConsultation.action?fifter=0
         * answerConsultationjson : [{"createtime":"2016-12-09","content":"依据《道路交通安全违法行为处理程序规定》的要求，你作为违法行为人只能由你接受扣分处理，不能由他人代履行。","id":"3290838558e26c1c0158e274a4090001","firstname":"章嘉戎","photo":"http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101201310960584","contactid":"15101201310960584"},{"createtime":"2016-12-09","content":"根据你的描述，是不可以的。更多法律知识，欢迎来电来访咨询！","id":"3290838558e18a500158e238e3b60008","firstname":"王兴","photo":"http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101201610917717","contactid":"15101201610917717"},{"createtime":"2016-12-09","content":"违法行为，不能代扣，否则依法追究法律责任","id":"3290838558e18a500158e20dbbfb0007","firstname":"胡江","photo":"http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101201410476819","contactid":"15101201410476819"},{"createtime":"2016-12-09","content":"随着新的交通管理系统的升级，所有的交通违章必须违章驾驶人本人前去处理。","id":"3290838558e18a500158e1e5bb130006","firstname":"段元元","photo":"http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101201511434218","contactid":"15101201511434218"},{"createtime":"2016-12-09","content":"记分是一种行政处罚，针对的是违法相对人，依法不能代扣。","id":"3290838558e18a500158e19e2ebb0003","firstname":"姚楠彬","photo":"http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101201010298649","contactid":"15101201010298649"},{"createtime":"2016-12-09","content":"违法责任的承担是违法人员本人应该履行的义务，且必须是本人亲自接受处罚。驾照代扣分虽在违章处罚中广泛存在，但确属违法行为，应严格禁止，希望你是守法好公民。","id":"3290838558e18a500158e19d0e090002","firstname":"邓全盛","photo":"http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101201310807172","contactid":"15101201310807172"},{"createtime":"2016-12-09","content":"依据交通法规，不允许代扣。","id":"3290838558b869e40158e14871c1001f","firstname":"殷仕梅","photo":"http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101201011809721","contactid":"15101201011809721"},{"createtime":"2016-12-08","content":"根据交通法律法规，是不能代替他人扣分的，否则可能承担相应法律责任。","id":"3290838558b869e40158ddb91d86001d","firstname":"丁波","photo":"http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101201510200833","contactid":"15101201510200833"}]
         * size : 8
         */

        private String createtime;
        private String id;
        private String title;
        private String answerurl;
        private String size;
        private List<AnswerConsultationjsonBean> answerConsultationjson;

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

        public String getAnswerurl() {
            return answerurl;
        }

        public void setAnswerurl(String answerurl) {
            this.answerurl = answerurl;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public List<AnswerConsultationjsonBean> getAnswerConsultationjson() {
            return answerConsultationjson;
        }

        public void setAnswerConsultationjson(List<AnswerConsultationjsonBean> answerConsultationjson) {
            this.answerConsultationjson = answerConsultationjson;
        }

        public static class AnswerConsultationjsonBean {
            /**
             * createtime : 2016-12-09
             * content : 依据《道路交通安全违法行为处理程序规定》的要求，你作为违法行为人只能由你接受扣分处理，不能由他人代履行。
             * id : 3290838558e26c1c0158e274a4090001
             * firstname : 章嘉戎
             * photo : http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/imageout?linktype=lpusermode&linkid=15101201310960584
             * contactid : 15101201310960584
             */

            private String createtime;
            private String content;
            private String id;
            private String firstname;
            private String photo;
            private String contactid;

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

            public String getContactid() {
                return contactid;
            }

            public void setContactid(String contactid) {
                this.contactid = contactid;
            }
        }
    }
}
