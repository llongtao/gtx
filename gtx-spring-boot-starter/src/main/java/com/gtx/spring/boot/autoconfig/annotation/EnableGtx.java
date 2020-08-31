package com.gtx.spring.boot.autoconfig.annotation;


import com.gtx.spring.boot.autoconfig.GtxAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author llt
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({GtxAutoConfiguration.class})
public @interface EnableGtx {
}
