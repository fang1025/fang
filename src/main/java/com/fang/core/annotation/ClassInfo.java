package com.fang.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主要用于在通过GeneralFile.java类生成文件时
 * 在相应的实体上标注一些该类的一些信息
 * package         表示生成的java类要放到哪个目录里去
 * moduleName      描述
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassInfo {
    /**
     * 表示生成的java类要放到哪个目录里去
     *
     * @return
     */
    String packageName();

    /**
     * 描述
     *
     * @return
     */
    String moduleName();
}