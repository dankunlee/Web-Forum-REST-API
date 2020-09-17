package com.dankunlee.forumapp.interceptor;

import com.dankunlee.forumapp.entity.Post;
import com.dankunlee.forumapp.repository.PostRepository;
import com.dankunlee.forumapp.utils.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class PostAuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    PostRepository postRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String httpMethod = request.getMethod();
        if (httpMethod.equals("POST") || httpMethod.equals("DELETE")) {
            String sessionOwner = (String) request.getSession().getAttribute(Session.SESSION_ID);
            // Name of the HttpServletRequest attribute that contains the URI templates map
            // --> maps the @PathVariable to the Map data structure
            Map<?, ?> templateMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            Long postId = Long.parseLong((String) templateMap.get("id")); // "/api/post/{id}"
            Post post = postRepository.findById(postId).get();
            String originalWriter = post.getCreatedBy();

            if (!originalWriter.equals(sessionOwner)) {
                response.getOutputStream().print("Unauthorized");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
