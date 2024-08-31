package com.board.board_week1;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InsertDummy {

    @Autowired
    PostDao postDao;

    @Test
    @Order(1)
    public void deleteAll() {
        postDao.deleteAll();
    }

    @Test
    @Order(2)
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