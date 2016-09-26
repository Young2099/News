package com.demo.panguso.mvp_mode.inject.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by ${yangfang} on 2016/9/26.
 */

@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApp {
}
