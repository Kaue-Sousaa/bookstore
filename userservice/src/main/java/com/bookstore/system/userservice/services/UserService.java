package com.bookstore.system.userservice.services;

import com.bookstore.system.userservice.dtos.RegisterUserDto;
import com.bookstore.system.userservice.dtos.UserDto;
import com.bookstore.system.userservice.exceptions.BadRequestException;
import com.bookstore.system.userservice.exceptions.NotFoundException;
import com.bookstore.system.userservice.mappers.UserMapper;
import com.bookstore.system.userservice.models.User;
import com.bookstore.system.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public List<UserDto> findAll(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::convertUserToUserDto).toList();
    }

    public UserDto findById(Long id){
        return userMapper.convertUserToUserDto(getEntityUser(id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(rollbackFor = Exception.class)
    public User createUser(RegisterUserDto registerUser){
        validRegister(registerUser);
        User entity = userMapper.convertRegisterUsertoUser(registerUser);
        entity.setPassword(new BCryptPasswordEncoder().encode(registerUser.password()));
        userRepository.save(entity);

        return entity;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUser(Long id, UserDto userDto){
        var user = getEntityUser(id);
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPhone(userDto.phone());

        userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id){
        userRepository.delete(getEntityUser(id));
    }

    private User getEntityUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));
    }

    private void validRegister(RegisterUserDto registerUserDto){
        if (userRepository.existsByEmail(registerUserDto.email())){
            throw  new BadRequestException("Email already resgistered.");
        }
    }
}
