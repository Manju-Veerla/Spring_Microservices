package com.example.user.model.mapper;

import com.example.user.model.entities.User;
import com.example.user.model.request.UserRequest;
import com.example.user.model.response.DepartmentResponse;
import com.example.user.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRequest userToUserRequest(User user);
    User userRequestToUser(UserRequest userRequest);

    @Mapping(target = "department", ignore = true) // We'll set this manually in the service
    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    UserResponse usertoUserResponse(User user);

    default UserResponse toUserResponseWithDepartment(User user, DepartmentResponse department) {
        UserResponse response = usertoUserResponse(user);
        return new UserResponse(
            department,
            response.username(),
            response.firstName(),
            response.lastName(),
            response.email()
        );
    }

    List<UserRequest> toUserDtoList(List<User> users);

    List<UserResponse> toUserResponseList(List<UserRequest> userRequests);
}
