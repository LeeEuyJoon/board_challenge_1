package com.board.board_week1;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostDao {
    int count();

    List<PostDto> selectAll();

    PostDto selectById(int postId);

    int deleteAll();

    int deleteById(int postId);

    int insert(PostDto postDto);

    int update(PostDto postDto);

    int plusView(PostDto postDto);

    List<PostDto> getPage(Map map); // offset, pageSize
}
