package com.fang.core.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 和ClassInfo一起使用,用于通过GeneralFile生成文件时在实体上标哪些属性需要生成到文件里,并标明一些信息
 * name        显示名称
 * formType    html页面中的form元素
 * comment     备注
 * isHidden    是否显示,主要用于像ID这样的字段不需要在页面上显示用
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AttributeInfo {
    /**
     * 显示名称
     *
     * @return
     */
    String name()default "";

    /**
     * form元素
     *
     * @return
     */
    String formType();
    
    /**
     * select 选项
     * @return
     */
    String options() default "";

    /**
     * 注释
     *
     * @return
     */
    String comment() default "";

    /**
     * 是否隐藏
     *
     * @return
     */
    boolean isHidden() default false;
}