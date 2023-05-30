package com.example.restful.model.mapper;

import com.example.restful.model.dto.UserDto;
import com.example.restful.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto toUserDto (User user);
    List<UserDto> toUserDtoList(List<User> userList);
}
