package com.ucap.lawyers.bean.userLogin.userOrdinary;

/**
 * Created by wekingwang on 2016/11/30.
 * 使用微信登陆授权后使用code换取的taccess_token   json
 */

public class UserWeXinTaccessToken {

    /**
     * access_token : 5tyLqKvjm0JmuMHIIlUrJbeF7FdoqCZ4qU74R7PAPa4n4juhhYbaU66BJV6W491kqLAnbHTCXGIMQglZ0fJNezVnBkIjAGtXPZqQblJkIaU
     * expires_in : 7200
     * refresh_token : oECU3KoDhh8Gf2oZuLKavGbp4d744eWh9V9MkG_CQbSvnqFSoK5LkyTv_wsAsYvvVXmSCrxKAX-KXmEcovJU9ITqXAH_tHFnXCxoiN9VM-U
     * openid : ouAHSwuu58QtXwJAeBdItBIIDVCI
     * scope : snsapi_userinfo
     * unionid : oXg-ywdeXwgHbkVuu076O0cxEhUc
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
