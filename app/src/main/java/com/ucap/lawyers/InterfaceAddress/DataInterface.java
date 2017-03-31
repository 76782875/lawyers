package com.ucap.lawyers.InterfaceAddress;


/**
 * Created by wekingwang on 16/9/22.
 * 数据接口地址
 */
public class DataInterface {

    private static String HOME_ROOT = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!";
    /**
     * 版本更新
     */
    public static String VERSION_UPDATE = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!Edition.action?fifter=0";
    /**
     * 首页律师查询地址
     */
    public static String HOME_LAWYER_FIND = HOME_ROOT + "QueryByLawyer.action?fifter=0";
    /**
     * 律师详细页面(荣誉表彰"查看详细")
     */
    public static String LAWYER_COMMEND = HOME_ROOT + "QueryLawyerRybzDetailsByList.action?fifter=0";
    /**
     * 律师详细页面(荣誉表彰"查看详细")参数名称
     */
    public static String LAWYER_COMMEND_KEY = "lawyerid";
    /**
     * 律师详细页面(公益服务"查看详细")
     * ID:律师id
     */
    public static String LAWYER_PUBLIC_SERVICE = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LawyerGyfwInfo.action?fifter=0";
    /**
     * 首页律所查询地址(精确查询)
     */
    public static String HOME_FIRM_FIND = HOME_ROOT + "QueryByLawfirm.action?fifter=0";
    /**
     * 律所详细页面(荣誉表彰"查看详细")
     */
    public static String FIRM_COMMEND = HOME_ROOT + "QueryLawfirmRybzDetailsByList.action?fifter=0";
    /**
     * 律所详细页面(荣誉表彰"查看详细")参数名称
     */
    public static String FIRM_COMMEND_KEY = "lawfirmid";
    /**
     * 律所详细页面(公益服务"查看详细")
     * <p>
     * name:律所名称
     */
    public static String FIRM_PUBLIC_SERVICE = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LawFirmGyfwInfo.action?fifter=0";
    /**
     * 律所详细点（点击律所所在律师名字查询改律师详细）
     * firstname:律师名称
     * id：律所id
     */
    public static String FIRM_DETAILED_LAWYERS = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LawyerList.action?fifter=0";
    /**
     * 首页律师和律所的数据总数
     */
    public static String FIRM_AND_LAWYER_DATA_SIZE = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!statistics.action";

    /**
     * 律所分类查询地址
     */
    public static class Firm {
        private static final String ROOT_URI = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!";
        /**
         * 荣誉表彰
         */
        public static final String FIRM_RYBZ = ROOT_URI + "QueryLawfirmRybzByList.action?fifter=0";
        /**
         * 公益服务
         */
        public static final String FIRM_GYFW = ROOT_URI + "QueryLawfirmGyfwByList.action?fifter=0";
        /**
         * 负面指数
         */
        public static final String FIRM_FMZS = ROOT_URI + "QueryLawfirmFmjlByList.action?fifter=0";
        /**
         * 规范指数
         */
        public static final String FIRM_GFZS = ROOT_URI + "QueryLawfirmFgzsByList.action?fifter=0";

        /**
         * 律所模糊查询
         * 参数
         * name:律所名（可输入非完整名称）
         * groupnum:许可证号（可输入非完整证号）
         */
        public static String FIRM_VAGUE_FIND = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!VagueQueryLawfirmTypeByList.action?fifter=0";
    }

    /**
     * 律师分类查询地址
     */
    public static class Lawyer {
        private static final String ROOT_URI = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!";
        /**
         * 荣誉表彰
         */
        public static final String LAWYER_RYBZ = ROOT_URI + "QueryLawyerRybzByList.action?fifter=0";
        /**
         * 公益服务
         */
        public static final String LAWYER_GYFW = ROOT_URI + "QueryLawyerGyfwByList.action?fifter=0";
        /**
         * 负面指数
         */
        public static final String LAWYER_FMZS = ROOT_URI + "QueryLawyerFmjlByList.action?fifter=0";
        /**
         * 律师模糊查询
         * 参数
         * firstname:律师姓名（可输入非完整名称）
         * contactid:执业证号（可输入非完整证号）
         */
        public static String LAWYERS_VAGUE_FIND = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!VagueQueryLawyerTypeByList.action?fifter=0";
    }


    /**
     * 位置分布图，根据用户点击位置坐标查询相应的律所
     */
    public static String LOCATION_CHART = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!QueryPositionMap.action?fifter=0";
    /**
     * 政策法规,地址
     */
    public static String LAW_LIST = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!zcfg.action?fifter=0";

    /**
     * 登陆接口
     */
    public static class UserLogin {
        /**
         * 登陆角色编号，执业律师
         */
        public static String USER_TYPE_LAWYER = "67";
        /**
         * 律师行政主管
         */
        public static String USER_TYPE_XING_ZHENG_ZHU_GUAN = "26";
        /**
         * 管理员
         */
        public static String USER_TYPE_ADMIN = "25";
        /**
         * 普通用户
         */
        public static String USER_TYPE_ORDINARY = "8";
        /**
         * 执业律师登陆
         * username:登录名
         * password:密码
         */
        public static String LAWYER_LOGIN = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LawyerLogin.action?fifter=0";
    }

    /**
     * 普通用户登陆
     * openid：QQid
     * Name:昵称
     * Userid:登录名
     * Password：密码
     * photo：图片
     * Sex：性别
     */
    public static String ORDINARY = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!OrdinaryUsersLogin.action?fifter=0";


    /**
     * 我要咨询，数据提价接口
     * title:标题
     * content：内容
     * userid:申请人ID
     * position:所在地址
     * type:申请类型ID
     * <p>
     * 返回：
     * Size:当日剩余申请条数
     */
    public static String CONSULTATION = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!PublicConsultation.action?fifter=0";

    /**
     * 注册新用户
     */
    public static class NewUser {

        /**
         * 新用户注册获取账号
         */
        public static String NEW_USER_ID = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!GenerateUserId.action?fifter=0";
        /**
         * 上传头像
         */
        public static String UPLOAD = "http://www.cdjustice.chengdu.gov.cn/ssh23/servlet/uploadservlet";
        /**
         * 上传资料
         */
        public static String UPLOAD_INFO = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!OrdinaryUsersRegister.action?fifter=0";
    }

    /**
     * 律师我的案件
     */
    public static String MY_COMMONLY = "";
    /**
     * 我的案件（测试地址）
     * Userid:律师ID
     */
    public static String TEST_MY_COMMONLY = "http://192.168.0.107:9527/scslsxt/lfzxyw/checklflogout!MyCase.action?";
    /**
     * 我的案件，办案日志
     */
    public static String MY_COMMONLY_JOURNAL = "";
    /**
     * 我的案件，办案日志（测试地址)
     * Orderid:我的案件
     * 返回的orderid
     */
    public static String TEST_MY_COMMONLY_JOURNAL = "http://192.168.0.107:9527/scslsxt/lfzxyw/checklflogout!caselog.action?";

    /**
     * 我的案件，案件详细
     */
    public static String MY_COMMONLY_DETAILED = "";
    /**
     * 我的案件，案件详细(测试地址)
     * Sid:案件ID
     */
    public static String TEST_MY_COMMONLY_DETAILED = "http://192.168.0.107:9527/scslsxt/lfzxyw/checklflogout!caseinfo.action?";
    /**
     * 我的案件：保存
     * Orderid:我的案件返回的orderid
     * Description:保存内容
     * Archchoose：案件进展
     * Username：登陆者姓名
     */
    public static String MY_COMMONLY_SAVE = "";
    /**
     * 我的案件：保存（测试地址）
     * <p>
     * Orderid:我的案件返回的orderid
     * Description:保存内容
     * Archchoose：案件进展
     * Username：登陆者姓名
     */
    public static String TEST_MY_COMMONLY_SAVE = "http://192.168.0.107:9527/scslsxt/lfzxyw/checklflogout!examinecase.action?";

    /**
     * 已解答问题，列表
     */
    public static String ANSWER_LIST = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!ConsultationType.action?";
    /**
     * 已解答问题，列表，测试地址
     * 参数：
     * typeid:咨询（分类ID）
     * /**
     * 分类对于id
     * 213	刑事法务
     * 214	行政法务
     * 215	经济纠纷
     * 216	婚姻家庭
     * 217	金融保险
     * 218	社保劳资
     * 219	交通医疗
     * 220	其他法务
     *
     * @param id
     */
    public static String TEST_ANSWER_LIST = "http://192.168.0.107:9527/scslsxt/lfzxyw/checklflogout!ConsultationType.action?";
    /**
     * 已解答问题，详细
     */
    public static String ANSWER_DETAILED = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!ConsultationTypeInfo.action?";
    /**
     * 以解答问题，详细（测试地址）
     * <p>
     * orderid:咨询分类返回的ID字段
     */
    public static String TEST_ANSWER_DETAILED = "http://192.168.0.107:9527/scslsxt/lfzxyw/checklflogout!ConsultationTypeInfo.action?";

    /**
     * 法律众筹，数据提交测试接口
     */
    public static String TEST_LAW_UPLOAD = "http://192.168.0.105:9527/scslsxt/lfzxyw/checklflogout!LegalCongregation.action?";
    /**
     * 法律众筹,数据提交
     * name：名称
     * bumen:部门
     * type:类别
     * nature:性质
     * content：内容
     * userid:律师ID
     */
    public static String LAW_UPLOAD = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LegalCongregation.action?";
    /**
     * 百姓法宝测试地址，列表
     * <p>
     * type:类型名称
     */
    public static String TEST_LAW_BROWSE = "http://192.168.0.105:9527/scslsxt/lfzxyw/checklflogout!LegalCongregationByType.action?";
    /**
     * 百姓法宝列表
     * <p>
     * type:类型名称
     */
    public static String LAW_BROWSE_LIST = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LegalCongregationByType.action?";
    /**
     * 百姓法宝，分类大小，测试地址
     */
    public static String TEST_LAW_BROWSE_SIZE = "http://192.168.0.105:9527/scslsxt/lfzxyw/checklflogout!LegalCongregationSize.action";
    /**
     * 百姓法宝，分类大小
     */
    public static String LAW_BROWSE_SIZE = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LegalCongregationSize.action?";
    /**
     * 百姓法宝，详细页面,本地测试地址
     * <p>
     * lid:法律众筹(按分类查询列表)返回的ID
     */
    public static String TEST_LAW_BROWSE_DETAILED = "http://192.168.0.105:9527/scslsxt/lfzxyw/checklflogout!LegalCongregationByTypeInfo.action";
    /**
     * 百姓法宝，详细页面
     * <p>
     * lid:法律众筹(按分类查询列表)返回的ID
     */
    public static String LAW_BROWSE_DETAILED = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LegalCongregationByTypeInfo.action?";

    /**
     * 百姓法宝，查询，测试地址
     * <p>
     * name:查询条件
     */
    public static String TEST_LAW_BROWSE_FIND = "http://192.168.0.105:9527/scslsxt/lfzxyw/checklflogout!LegalCongregationSearch.action";
    /**
     * 百姓法宝，查询
     * <p>
     * name:查询条件
     */
    public static String LAW_BROWSE_FIND = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LegalCongregationSearch.action?";
    /**
     * 普通用户，我的提问详细页面，点赞
     * <p>
     * pointpraisepeople:点赞人ID
     * amanofpraise：被点赞人ID
     * publicconsulttionid：公众咨询ID
     */
    public static String ORDINARY_PROBLEM_DETAILED_NICE = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!PublicConsultationNice.action?";

    /**
     * 律师列表解答排行榜
     * <p>
     * day:排行天数(all是总排行榜,传多少就是排的多少天)
     */
    public static String RANKING_LAWYERS_LIST = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LawyersRanking.action?";
    /**
     * 律师解答排行问题列表
     * <p>
     * id:排行榜律师的ID
     */
    public static String RANKING_LAWYERS_ANSWER_LIST = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LawyersRankingList.action?";
    /**
     * 律师解答排行问题详细
     * <p>
     * orderid:咨询ID
     */
    public static String RANKING_LAWYER_ANSWER_DETAILED = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LawyersRankingInfo.action?";
    /**
     * 律所解答排行律所列表
     */
    public static String RANKING_FIRM_LIST = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LawfirmRanking.action?";
    /**
     * 律所解答排行律师列表
     * <p>
     * id:律所ID(律所排行榜返回的groupid)
     */
    public static String RANKING_FIRM_LAWYER_LIST = "http://www.cdjustice.chengdu.gov.cn/ssh23/lfzxyw/checklflogout!LawfirmRankingList.action?";
}