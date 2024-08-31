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

    @Override
    public int insertPost(PostDto postDto) {
        return postDao.insert(postDto);
    }

    @Override
    public int deletePost(int id) {
        return postDao.deleteById(id);
    }

    @Override
    public int updatePost(PostDto postDto) {
        return postDao.update(postDto);
    }

    @Override
    public int plusView(PostDto postDto) {
        return postDao.plusView(postDto);
    }
}
