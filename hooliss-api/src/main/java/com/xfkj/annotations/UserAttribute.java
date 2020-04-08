package com.xfkj.annotations;

import com.xfkj.tools.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)      //参数上使用
@Retention(RetentionPolicy.RUNTIME) //运行时注解
public @interface UserAttribute {
    /**
     * session中用户对象的key
     */
    String value() default Constants.USER_SESSION;
}
