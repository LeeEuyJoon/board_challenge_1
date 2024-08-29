package com.board.board_week1;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public String list(Integer page, Model model) {

        // 페이지핸들러에 필요한 페이지 정보 지정
        if (page == null || page < 1) {
            page = 1;
        }
        int pageSize = 10;
        int totalCnt = boardService.countAll();

        // 페이지핸들러 모델에 담기
        PageHandler ph = new PageHandler(totalCnt, page, pageSize);
        model.addAttribute("ph", ph);

        // 페이징 정보에 맞는 리스트 가져오기
        Map<String, Integer> map = new HashMap();

        map.put("offset", (page - 1) * pageSize);
        map.put("pageSize", pageSize);

        List<PostDto> postList = boardService.getPage(map);

        model.addAttribute("postList", postList);

        return "boardList";
    }

    @GetMapping("/post")
    public String Post(Integer postId, Model model) {

        PostDto post = boardService.getPost(postId);

        model.addAttribute("post", post);

        return "post";
    }
}
