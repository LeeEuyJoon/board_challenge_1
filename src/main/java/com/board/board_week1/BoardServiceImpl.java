package com.board.board_week1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private PostDao postDao;

    @Override
    public int countAll() {
        return postDao.count();
    }

    @Override
    public List<PostDto> getPage(Map map) {
        return postDao.getPage(map);
    }

    @Override
    public PostDto getPost(int id) {
        return postDao.selectById(id);
    }
}
