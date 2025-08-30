package com.bookstore.system.userservice.mappers;

import com.bookstore.system.userservice.dtos.RegisterUserDto;
import com.bookstore.system.userservice.dtos.UserDto;
import com.bookstore.system.userservice.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User convertRegisterUsertoUser(RegisterUserDto userDto);

    UserDto convertUserToUserDto(User user);

}
