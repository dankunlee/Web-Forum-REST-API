package com.dankunlee.forumapp.config;

import com.dankunlee.forumapp.interceptor.CommentAuthorizationInterceptor;
import com.dankunlee.forumapp.interceptor.LogInInterceptor;
import com.dankunlee.forumapp.interceptor.PostAuthorizationInterceptor;
import com.dankunlee.forumapp.utils.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class WebConfigurer implements WebMvcConfigurer {
    @Autowired
    LogInInterceptor logInInterceptor;

    @Autowired
    PostAuthorizationInterceptor postAuthorizationInterceptor;

    @Autowired
    CommentAuthorizationInterceptor commentAuthorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Applies interceptors to the following urls
        /*
        1. ? matches one character
        2. * matches zero or more characters
        3. ** matches zero or more 'directories' in a path
        */

        registry.addInterceptor(logInInterceptor)
                .excludePathPatterns("/api/post/page/**")
                .addPathPatterns("/api/post/**")
                .addPathPatterns("/api/post/**/comment/**");

        registry.addInterceptor(postAuthorizationInterceptor)
                .excludePathPatterns("/api/post/page")
                .excludePathPatterns("/api/post/**/comment/**")
                .addPathPatterns("/api/post/**");

        registry.addInterceptor(commentAuthorizationInterceptor)
                .addPathPatterns("/api/post/**/comment/**");
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        // If a bean of type AuditorAware is exposed to the ApplicationContext,
        // the auditing infrastructure will pick it up automatically
        // and use it to determine the current user to be set on domain types
        return () -> {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            String user = (String) servletRequestAttributes.getRequest().getSession().getAttribute(Session.SESSION_ID);
            if (user != null) return Optional.of(user); // returns the username of current session
            else return Optional.of("Anonymous"); // should never reach here bc of login interceptor
        };
    }
}
