package com.dta.filter;

import com.alibaba.fastjson.JSONObject;
import com.dta.pojo.Result;
import com.dta.utils.JwtUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 拦截器
 *
 */
@WebFilter(urlPatterns = "/*")
public class LoginCheckedFilter implements Filter {
    /**
     * 拦截方法，只要资源链接被拦截到，就会触发此方法
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1.获取请求路径
        String url = request.getRequestURI().toString();
        //2. 判断请求的资源路径
        if(url.contains("login")){
            //登录功能，放行
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //3.获取请求头token，返回的就是前端携带过来的令牌
        String jwt = request.getHeader("token");
        if(jwt == null){
            // 返回登录界面
            Result notLogin = Result.error("NOT_LOGIN");
            //把notLogin对象转换为JSON字符串返回
            String jsonString = JSONObject.toJSONString(notLogin);
            //按照前端约定好的json格式的数据返回
            response.getWriter().write(jsonString);
            return;
        }
        //4. 解析令牌
        try {
            JwtUtils.parseJWT(jwt);
        }catch (Exception e){
            //令牌存在问题
            //返回登录页面
            Result notLogin = Result.error("NOT_LOGIN");
            //把notLogin对象转换为JSON字符串返回
            String jsonString = JSONObject.toJSONString(notLogin);
            //按照前端约定好的json格式的数据返回
            response.getWriter().write(jsonString);
            return;
        }

        //5 令牌验证成功，放行
        filterChain.doFilter(servletRequest,servletResponse);

    }
}
