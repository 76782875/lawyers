package com.ucap.lawyers.bean.userLogin.userOrdinary;

/**
 * Created by wekingwang on 2016/11/30.
 */

public class UserWeiXinUpdateToken {

    /**
     * openid : ouAHSwuu58QtXwJAeBdItBIIDVCI
     * access_token : FbenuaWf51WPm5ciBGWZQiMnYUrGAnA0s0xub6ZASZOp8_F_HO0hAB41P4Q8AJoifJhu01DUt6vohaWtI86cabmMReA5MloyXUR0UCoI1DA
     * expires_in : 7200
     * refresh_token : zlniQm3fh-RDv9cLpjJa5jhGMdQlC084zghYdvOmD3oDbCrOp-1rGaVbDN2rglLlYXOWZb04GZj6vhnbpNSDDNxUM0Sdqv0QUi2NEim78PQ
     * scope : snsapi_base,snsapi_userinfo,
     */

    private String openid;
    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String scope;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
