package com.exam.commonbiz.oss;

import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;

public class STSGetter extends OSSFederationCredentialProvider {
    private OSSFederationToken ossFederationToken;
    String ak;
    String sk;
    String token;
    String expiration;

    public STSGetter(TokenBean bean) {
//        this.ak = bean.getCredentials().getAccessKeyId();
//        this.sk = bean.getCredentials().getAccessKeySecret();
//        this.token = bean.getCredentials().getSecurityToken();
//        this.expiration = bean.getCredentials().getExpiration();
    }

    public OSSFederationToken getFederationToken() {
        return new OSSFederationToken(ak, sk, token, expiration);
    }
}


