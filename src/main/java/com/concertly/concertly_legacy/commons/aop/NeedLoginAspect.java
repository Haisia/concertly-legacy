package com.concertly.concertly_legacy.commons.aop;

import com.concertly.concertly_legacy.commons.exceptions.LoginRequiredException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class NeedLoginAspect {
  @Before("@annotation(com.concertly.concertly_legacy.commons.annotations.NeedLogin)")
  public void checkLogin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated() ||
      !(authentication.getPrincipal() instanceof UserDetails)) {
      throw new LoginRequiredException(getRequestUri());
    }
  }

  private String getRequestUri() {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    String requestUri = "";
    if (attrs != null) {
      HttpServletRequest request = attrs.getRequest();
      requestUri = request.getRequestURI();
    }
    return requestUri;
  }
}
