package com.board.board_week1;

import java.util.List;
import java.util.Map;

public interface BoardService {
    int countAll();

    List<PostDto> getPage(Map map);

    PostDto getPost(int id);
}
