package com.board.board_week1;

import com.board.board_week1.Exception.NoHaveAuthException;
import com.board.board_week1.Exception.PostCreateException;
import com.board.board_week1.Exception.PostDeleteException;
import com.board.board_week1.Exception.PostUpdateException;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.javassist.tools.reflect.CannotCreateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);  // Logger 선언 및 초기화


    @GetMapping("/list")
    public String list(Integer page,Integer pageSize,  Model model, HttpSession session) {

        // 페이지핸들러에 필요한 페이지 정보 지정
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        int totalCnt = boardService.countAll();

        // 페이지핸들러 모델에  담기
        PageHandler ph = new PageHandler(totalCnt, page, pageSize);
        model.addAttribute("ph", ph);

        // 페이징 정보에 맞는 리스트 가져오기
        Map<String, Integer> map = new HashMap();

        map.put("offset", (page - 1) * pageSize);
        map.put("pageSize", pageSize);

        List<PostDto> postList = boardService.getPage(map);

        model.addAttribute("postList", postList);


        String userId = (String) session.getAttribute("userId");

        model.addAttribute("userId", userId);

        return "boardList";
    }

    @GetMapping("/post")
    public String post(Integer postId, Integer page,Integer pageSize, Model model, HttpSession session) {
        PostDto post = boardService.getPost(postId);
        String sessionId = (String) session.getAttribute("userId");

        model.addAttribute("post", post);
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);

        boardService.plusView(post);

        return "post";
    }

    @GetMapping("/create")
    public String addPost() {
        return "create";
    }

    @PostMapping("/create")
    public String addPost(PostDto post, HttpSession session) {

        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            throw new PostCreateException("비정상적인 방법의 게시물 등록입니다.");
        }

        post.setWriter(userId);
        boardService.insertPost(post);

        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String deletePost(Integer postId, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

//        if (userId == null) {
//            throw new PostDeleteException("비정상적인 방법의 게시물 삭제입니다.");
//        }

        if (userId == null || !userId.equals(boardService.getPost(postId).getWriter())) {
            throw new NoHaveAuthException("권한이 없습니다. 정상적인 경로로 응답을 요청하세요");
        }

        boardService.deletePost(postId);

        return "redirect:/board/list";
    }

    @PostMapping("/edit")
    public String updatePost(@ModelAttribute PostDto post, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            throw new PostUpdateException("비정상적인 방법의 게시물 수정입니다.");
        }

        boardService.updatePost(post);

        return "redirect:/board/post?postId=" + post.getPostId();
    }



    // 예외처리

    @ExceptionHandler({PostCreateException.class, PostDeleteException.class, PostUpdateException.class, NoHaveAuthException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String ForbiddenExceptionsHandler(Exception e, Model model) {
        Map<Class<? extends Exception>, String> logMap = new HashMap<>();

        logMap.put(PostCreateException.class, PostCreateException.class.getName());
        logMap.put(PostDeleteException.class, PostDeleteException.class.getName());
        logMap.put(PostUpdateException.class, PostUpdateException.class.getName());
        logMap.put(NoHaveAuthException.class, NoHaveAuthException.class.getName());


        String logMessage = logMap.getOrDefault(e.getClass(), "알 수 없는 오류: ");
        logger.warn(logMessage + e.getMessage());

        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", HttpStatus.FORBIDDEN.value() + " (" + HttpStatus.FORBIDDEN.getReasonPhrase() + ")");

        return "error";
    }
//
//    @ExceptionHandler(PostCreateException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public String postCreateExceptionCatcher(PostCreateException e, Model model) {
//        logger.warn("게시물 등록 중 오류 : " + e.getMessage());
//
//
//        model.addAttribute("errorMessage", e.getMessage());
//        model.addAttribute("errorCode",
//                HttpStatus.FORBIDDEN.value() +"(" + HttpStatus.FORBIDDEN.getReasonPhrase() + ")");
//
//        return "error";
//    }
//
//    @ExceptionHandler(PostDeleteException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public String postDeleteExceptionCatcher(PostDeleteException e, Model model) {
//        logger.warn("게시물 삭제 중 오류 : " + e.getMessage());
//
//
//        model.addAttribute("errorMessage", e.getMessage());
//        model.addAttribute("errorCode",
//                HttpStatus.FORBIDDEN.value() +"(" + HttpStatus.FORBIDDEN.getReasonPhrase() + ")");
//
//        return "error";
//    }
//
//    @ExceptionHandler(PostUpdateException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public String postUpdateExceptionCatcher(PostUpdateException e, Model model) {
//        logger.warn("게시물 수정 중 오류 : " + e.getMessage());
//
//
//        model.addAttribute("errorMessage", e.getMessage());
//        model.addAttribute("errorCode",
//                HttpStatus.FORBIDDEN.value() +"(" + HttpStatus.FORBIDDEN.getReasonPhrase() + ")");
//
//        return "error";
//    }
//
//
//    @ExceptionHandler(NoHaveAuthException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public String NoHaveAuthExceptionHandler(NoHaveAuthException e, Model model) {
//        logger.warn("권한이 없는 동작 요청 : " + e.getMessage());
//
//        model.addAttribute("errorMessage", e.getMessage());
//        model.addAttribute("errorCode",
//                HttpStatus.FORBIDDEN.value() +"(" + HttpStatus.FORBIDDEN.getReasonPhrase() + ")");
//
//        return "error";
//    }
//

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String generalExceptionHandler(Exception e, Model model) {
        logger.error("알 수 없는 오류 : " + e.getMessage(), e);

        model.addAttribute("errorMessage", "알수 없는 예외가 발생했습니다.");
        model.addAttribute("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value() + " (" + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + ")");

        return "error";
    }


    @GetMapping("/testError")
    public String testError() {
        // 테스트를 위한 예제 강제 발생
        throw new PostCreateException("(테스트) 세션이 만료되어 작업을 수행할 수 없습니다.");
    }
}
