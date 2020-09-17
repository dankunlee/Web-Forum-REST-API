package com.dankunlee.forumapp.interceptor;

import com.dankunlee.forumapp.entity.Comment;
import com.dankunlee.forumapp.repository.CommentRepository;
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
public class CommentAuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    CommentRepository commentRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String httpMethod = request.getMethod();
        if (httpMethod.equals("POST") || httpMethod.equals("DELETE")) {
            String sessionOwner = (String) request.getSession().getAttribute(Session.SESSION_ID);
            Map<?, ?> templateMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            // "/api/post/{postId}/comment/{commentId}"
            Long commentId = Long.parseLong((String) templateMap.get("commentId"));
            Comment comment = commentRepository.findById(commentId).get();
            String originalWriter = comment.getCreatedBy();

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
