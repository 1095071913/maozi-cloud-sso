package com.maozi.oauth.token.error.code;

import com.maozi.base.AbstractBaseCode;
import com.maozi.base.CodeData;
import org.springframework.stereotype.Component;

@Component
public class TokenErrorCode extends AbstractBaseCode {

    public final static CodeData PASSWORD_ERROR = new CodeData(1000,"密码错误");

}
