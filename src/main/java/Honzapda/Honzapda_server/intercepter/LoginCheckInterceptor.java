package Honzapda.Honzapda_server.intercepter;

import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;


public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 세션에서 회원 정보 조회
        HttpSession session = request.getSession();
        UserResDto.InfoDto userResDto = (UserResDto.InfoDto) session.getAttribute("user");

        // 2. 회원 정보 체크
        if (userResDto == null) {

            request.getRequestDispatcher("/auth/expired").forward(request,response); // 컨트롤러로 이동해서 에러 발생
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}