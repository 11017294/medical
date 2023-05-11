package com.chen.gen;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * <p>
 *
 * </p>
 *
 * @author：MaybeBin
 * @Date: 2023-05-11 09:20
 */
public class GenApplication {
    private static final String AUTHOR = "MaybeBin";
    private static final String URL = "jdbc:mysql://120.24.48.80:3306/medical?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "333";
    // 以medical为根目录
    private static final String CATALOGUE = "/service/service-hosp/src/main/java";
    private static final String PARENT_PACKAGE = "com.chen.medical.hosp";
    private static final String[] tables = {"hospital_set"};

    public static void main(String[] args) {
        all();
    }

    private static void all(){
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                //全局配置
                .globalConfig(builder -> {
                    builder.author("MaybeBin")                          // 设置作者
                            .enableSwagger()                            // 开启 swagger 模式
                            .fileOverride()                             // 覆盖已生成文件
                            .disableOpenDir()                           // 禁止打开输出目录
                            .dateType(DateType.TIME_PACK)               // 时间策略
                            .commentDate("yyyy-MM-dd")                  // 注释日期
                            .outputDir(System.getProperty("user.dir") + CATALOGUE); // 指定输出目录
                })
                //包配置
                .packageConfig(builder -> {
                    builder.entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .parent(PARENT_PACKAGE)                           // 设置父包名
                            //.moduleName("system")                            // 设置父包模块名
                            .mapper("mapper")                                  // Mapper 包名
                            .xml("mapper/xml");                                     // Mapper XML 包名
                })
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tables)                            // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_")                         // 表前缀过滤
                            .entityBuilder()                              // 切换至Entity设置
                            .versionColumnName("version")                 // 乐观锁字段名(数据库)
                            .logicDeleteColumnName("isDeleted")           // 逻辑删除字段名(数据库)
                            .enableLombok()                               // lombok生效
                            .enableTableFieldAnnotation()                 // 所有实体类加注解
                            .serviceBuilder()                             // 切换至Service层设置
                            .formatServiceFileName("%sService")           // 设定后缀名
                            .formatServiceImplFileName("%sServiceImpl");  // 设定后缀名
                })
                //模板配置
                .templateEngine(new FreemarkerTemplateEngine())           // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
