package com.ucap.lawyers.bean;

import java.util.List;

/**
 * Created by wekingwang on 16/10/21.
 */

public class LocationData {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * gyzs : 无
         * phone : 13982939256
         * weburl : 无
         * lawyerlist : ["唐德银","李旭"]
         * groupname : 简阳市司法局
         * yearcheck : 无
         * gfzs : 无
         * id : 979
         * typesettings : 唐德银
         * groupnum : 25101201610359079
         * address : 四川省简阳市雄州大道南段389—391号
         * email : 无
         * hhrlist : []
         * description : 四川品鑫律师事务所为新所，但合伙人都为老律师。发起人唐德银、李旭、罗克等合伙人均长期执业，谓业界的姣姣者。组成了专职律师及辅助人员八余人的法律服务专业团队。我所非合伙律师都为法律本科以上学历、通过国家司法考试，理论功底深厚，办案经验丰富，业界口碑良好。
         * 目前，四川品鑫律师事务所正努力向律师团队专业、案件来源恒稳、运营管理规范、服务质量上乘、财务状况良好的综合性律师事务所迈进。
         * <p>
         * showname : 合伙人:(0)
         * name : 四川品鑫律师事务所
         * ryzs : 无
         * fmzs : 无
         */

        private String gyzs;
        private String phone;
        private String weburl;
        private String groupname;
        private String yearcheck;
        private String gfzs;
        private String id;
        private String typesettings;
        private String groupnum;
        private String address;
        private String email;
        private String description;
        private String showname;
        private String name;
        private String ryzs;
        private String fmzs;
        private List<String> lawyerlist;
        private List<String> hhrlist;

        public String getGyzs() {
            return gyzs;
        }

        public void setGyzs(String gyzs) {
            this.gyzs = gyzs;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getWeburl() {
            return weburl;
        }

        public void setWeburl(String weburl) {
            this.weburl = weburl;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getYearcheck() {
            return yearcheck;
        }

        public void setYearcheck(String yearcheck) {
            this.yearcheck = yearcheck;
        }

        public String getGfzs() {
            return gfzs;
        }

        public void setGfzs(String gfzs) {
            this.gfzs = gfzs;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTypesettings() {
            return typesettings;
        }

        public void setTypesettings(String typesettings) {
            this.typesettings = typesettings;
        }

        public String getGroupnum() {
            return groupnum;
        }

        public void setGroupnum(String groupnum) {
            this.groupnum = groupnum;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getShowname() {
            return showname;
        }

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRyzs() {
            return ryzs;
        }

        public void setRyzs(String ryzs) {
            this.ryzs = ryzs;
        }

        public String getFmzs() {
            return fmzs;
        }

        public void setFmzs(String fmzs) {
            this.fmzs = fmzs;
        }

        public List<String> getLawyerlist() {
            return lawyerlist;
        }

        public void setLawyerlist(List<String> lawyerlist) {
            this.lawyerlist = lawyerlist;
        }

        public List<String> getHhrlist() {
            return hhrlist;
        }

        public void setHhrlist(List<String> hhrlist) {
            this.hhrlist = hhrlist;
        }
    }
}
