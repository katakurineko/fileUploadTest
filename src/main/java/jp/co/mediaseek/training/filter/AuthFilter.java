package jp.co.mediaseek.training.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class AuthFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    HttpSession session = req.getSession();
    String uri = req.getRequestURI();

    // *****cssやjsを読み込む際に、このフィルタの処理を行わないようにする*****
    boolean isResourceFile = uri.matches(".*/css/.*|.*/js/.*");
    if (isResourceFile) {
      chain.doFilter(request, response);
      return;
    }
    // *********************************************************************

    if (!uri.endsWith("/login")) {
      if (session.getAttribute("userId") == null) {
        res.sendRedirect("login");
        return;
      }
    } else {
      if (session.getAttribute("userId") != null) {
        res.sendRedirect("meishiList");
        return;
      }
    }
    chain.doFilter(request, response);
  }
}
