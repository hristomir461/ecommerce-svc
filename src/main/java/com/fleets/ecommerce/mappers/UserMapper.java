package com.fleets.ecommerce.mappers;

import com.fleets.ecommerce.entities.User;
import com.fleets.ecommerce.models.Users.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    User toUser(UserDto dto);
}
