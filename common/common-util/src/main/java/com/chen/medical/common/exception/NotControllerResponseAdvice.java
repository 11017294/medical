package com.chen.medical.common.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 *  被该注解标注的类或方法 不对其返回的数据进行封装处理
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-13
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotControllerResponseAdvice {

}
