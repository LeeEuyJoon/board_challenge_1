package com.board.board_week1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InsertDummy {
    @Autowired
    PostDao postDao;

    @Test
    void InsertDummy() {
        for (int i = 0; i < 500; i++) {
            PostDto postDto = PostDto.builder()
                    .title("title" + i)
                    .writer("writer" + i)
                    .content("content" + i)
                    .build();

            postDao.insert(postDto);
        }
    }
}
