package com.pay.data.interceptors;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.pay.data.entity.Result;
import com.pay.data.enums.ExceptionEnum;
import com.pay.data.utils.FiledUtils;
import com.pay.data.utils.JwtUtil;
import com.pay.data.utils.ResultUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

import static com.pay.data.utils.FiledUtils.AUTHORIZATION;

/**
 * @createTime: 2018/2/11
 * @author: HingLo
 * @description: 登录认证拦截器
 */
@Slf4j
public class LoginInterceptor implements Interceptor {
    private final String OPTIONS = "OPTIONS";
    private final String prefix = "Bearer";
    private String userId = "userId";
    private String subject = "subject";

    @Override
    public void intercept(Invocation inv) {
        //获取controller对象
        Controller controller = inv.getController();
        //获取response对象
        HttpServletResponse response = controller.getResponse();
        //获取请求头
        String authHeader = controller.getHeader(FiledUtils.AUTHORIZATION);
        //判断是是否是option请求
        if (OPTIONS.equals(controller.getRequest().getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            inv.invoke();
        } else {
            if (authHeader == null || !authHeader.startsWith(prefix)) {
                Result result = ResultUtils.error(ExceptionEnum.HEADERERROR);
                controller.renderJson(result);
                return;
            } else {
                try {
                    // 取得token
                    String token = authHeader.substring(prefix.length());
                    //检测token是否有效
                    Claims claims = JwtUtil.parseJWT(token);
                    //获取登录信息的id
                    userId = claims.getId();
                    //登录用户的信息Json格式字符串
                    subject = claims.getSubject();
                    //其他详细认证处理   需要使用subject 与 UserId

                } catch (Exception e) {
                    //token 失效
                    Result result = ResultUtils.error(ExceptionEnum.TOKENINVALID);
                    controller.renderJson(result);
                    return;
                }
            }

        }
        controller.getRequest().setAttribute("userId", userId);
        response.setHeader(AUTHORIZATION, JwtUtil.createJWT(userId, subject));
        inv.invoke();

    }


}
