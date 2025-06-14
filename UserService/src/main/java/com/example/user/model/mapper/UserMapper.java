package com.example.user.model.mapper;

import com.example.user.model.entities.User;
import com.example.user.model.request.UserRequest;
import com.example.user.model.response.DepartmentResponse;
import com.example.user.model.response.UserDeptResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRequest userToUserRequest(User user);
    User userRequestToUser(UserRequest userRequest);

    @Mapping(target = "department", ignore = true) // We'll set this manually in the service
    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    UserDeptResponse usertoUserDeptResponse(User user);

    default UserDeptResponse toUserResponseWithDepartment(User user, DepartmentResponse department) {
        UserDeptResponse response = usertoUserDeptResponse(user);
        return new UserDeptResponse(
            department,
            response.username(),
            response.firstName(),
            response.lastName(),
            response.email()
        );
    }

}
