package com.hqyj.erp_sys.util;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;

public class CodeGenerator {
    public static void main(String[] args) {
        //***数据库的url, 用户名和密码
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/erpdb?serverTimezone=Asia/Shanghai",
                        "root", "root")
                .globalConfig(builder -> {
                    builder.author("老茶") // 设置作者，生成文件的注释里的作者
                            .outputDir("D:\\FILEAPP\\idearIJ\\erp_sys\\src\\main\\java"); //***指定输出目录
                }).packageConfig(builder -> {
                    builder.parent("com.hqyj.erp_sys"); //***设置父包名
                    // .controller("controller") //控制层包名
                    // .service("service") //service接口包名
                    // .serviceImpl("service.impl")  //service接口实现包名
                    // .mapper("mapper")  //mapper接口包名
                    // .xml("mapper.xml") //映射文件的包名(如果要生成的话，加上这句，去掉下面的.xml方法)
                    // .entity("entity"); //实体类的包名
                }).strategyConfig(builder -> {
                    builder
                            //.addInclude("customer","orders","record")  //***设置需要生成的表名
                            .addExclude()//设置不需要生成的表名
                            .controllerBuilder();//只生成Controller
                    //.enableRestStyle(); //生成的Controller类添加@RestController;
                }).templateConfig(builder -> {
                    builder.xml(null); //禁止生成xml映射文件
                }).execute();
    }
}
