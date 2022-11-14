package com.fengling.ggkt.activity.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.fengling.ggkt.activity.mapper")
public class ActivityConfig {

//      import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//      import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


//    为了防止意外不要
//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
//        return interceptor;
//    }

}
