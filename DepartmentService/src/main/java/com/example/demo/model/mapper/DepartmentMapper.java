package com.example.demo.model.mapper;

import com.example.demo.model.request.AddressRequest;
import com.example.demo.model.request.DepartmentRequest;
import com.example.demo.model.entities.Address;
import com.example.demo.model.entities.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "departmentName", target = "departmentName")
    @Mapping(source = "departmentCode", target = "departmentCode")
    @Mapping(target = "address", source = "address")
    DepartmentRequest departmentToDepartmentDto(Department department);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "departmentName", target = "departmentName")
    @Mapping(source = "departmentCode", target = "departmentCode")
    @Mapping(target = "address", source = "address")
    Department departmentDtoToDepartment(DepartmentRequest departmentDto);

    Set<AddressRequest> addressSetToAddressDTOSet(Set<Address> addresses);

    Set<Address> addressDTOSetToAddressSet(Set<AddressRequest> addressDTOS);

    AddressRequest addressToAddressDTO(Address address);

    Address addressDTOtoAddress(AddressRequest addressDTO);

}
