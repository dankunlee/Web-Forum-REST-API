package com.dankunlee.forumapp.interceptor;

import com.dankunlee.forumapp.utils.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LogInInterceptor implements HandlerInterceptor {
    @Override
    // used to perform operations before sending the request to the controller
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(Session.SESSION_ID) == null) {
            // sends msg when urls from WebConfigurer.addInterceptors are accessed without logging in
            response.getOutputStream().print("Login Required");
            return false;
        }
        return true;
    }

    @Override
    // used to perform operations before sending the response to the client
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    // used to perform operations after completing the request and response
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
