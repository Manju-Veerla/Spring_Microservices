package com.example.user.model.mapper;

import com.example.user.model.request.UserRequest;
import com.example.user.model.entities.User;
import com.example.user.model.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRequest toUserRequest(User user);
    User toUser(UserRequest userRequest);

    List<UserRequest> toUserDtoList(List<User> users);

    List<UserResponse> toUserResponseList(List<UserRequest> userRequests);
}
