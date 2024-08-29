package com.board.board_week1;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserDao userDao;

    @GetMapping("/login")
    public String loginForm(HttpServletRequest request, Model model) {
        // 쿠키 배열을 받아옴 (쿠키는 브라우저에서 자동으로 전송함 (로그인폼 html에서 쿠키를 서버로 전송하는 작업 필요 x))
        Cookie[] cookies = request.getCookies();

        // 쿠키가 있는 경우
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // "userId"라는 이름의 쿠키를 찾음
                if (cookie.getName().equals("userId")) {
                    // 쿠키의 값을 모델에 추가해 로그인 폼에 아이디를 미리 채움
                    model.addAttribute("userId", cookie.getValue());
                    break;
                }
            }
        }

        return "loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션 종료
        session.invalidate();

        // 로그인폼으로 리다이렉트
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute
            UserDto userDto,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes rattr,
            boolean rememberId) {

        String userId = userDto.getUserId();
        String pwd = userDto.getPwd();

        // id pwd 일치하는지 확인
        if (!loginCheck(userId, pwd)) {
            rattr.addFlashAttribute("errorMessage", "ID 또는 비밀번호가 일치하지 않습니다.");

            return "redirect:/login/login";
        }

        // 세션 생성 후 세션에 userId 정보 담기
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);

        Cookie cookie;
        if (rememberId) {
            // "아이디 기억하기" 체크된 경우 쿠키 생성
            cookie = new Cookie("userId", userId);
            cookie.setMaxAge(60 * 60 * 24);
        } else {
            // "아이디 기억하기" 체크 안된 경우 기존 쿠키 삭제
            cookie = new Cookie("userId", null);
            cookie.setMaxAge(0);
        }
        cookie.setPath("/");
        response.addCookie(cookie);

        // 게시판 화면으로 이동
        return "redirect:/board/list";
    }

    private boolean loginCheck(String userId, String pwd) {
        UserDto userDto = userDao.selectById(userId);

        if (userDto == null) return false;

        return userDto.getPwd().equals(pwd);
    }


}
