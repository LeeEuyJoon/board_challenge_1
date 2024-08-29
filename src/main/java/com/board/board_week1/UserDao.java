package com.board.board_week1;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    int count();

    List<UserDto> selectAllUser();

    UserDto selectById(String userId);

    int deleteAll();

    int deleteById(String userId);

    int insert(UserDto userDto);

    int update(UserDto userDto);

}
