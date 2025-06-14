package com.example.dept;

import com.example.dept.controller.DepartmentController;
import com.example.dept.model.entities.AddressType;
import com.example.dept.model.request.AddressRequest;
import com.example.dept.model.request.DepartmentRequest;
import com.example.dept.model.response.DepartmentResponse;
import com.example.dept.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService departmentService;

    @Test
    void givenDepartmentId_whenGetDepartment_thenReturnDepartmentResponse() throws Exception {
        // Given
        Long departmentId = 1L;
        DepartmentResponse mockResponse = new DepartmentResponse(departmentId, "IT", Set.of(), "IT-001");
        
        when(departmentService.getDepartmentById(departmentId)).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/department/{id}", departmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.departmentName").value("IT"))
                .andExpect(jsonPath("$.departmentCode").value("IT-001"));
    }

    @Test
    void givenDepartmentRequest_whenSaveDepartment_thenReturnSavedDepartmentResponse() throws Exception {
        // Given
        Long departmentId = 1L;
        AddressRequest address = new AddressRequest(1, AddressType.OFFICIAL, "123 Main St", "New York", "10001");
        DepartmentRequest request = new DepartmentRequest(
                departmentId,
                "IT",
                Set.of(address),
                "IT-001"
        );
        
        DepartmentResponse mockResponse = new DepartmentResponse(departmentId, "IT", Set.of(), "IT-001");
        
        when(departmentService.saveDepartment(any(DepartmentRequest.class))).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/department/saveDept")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.departmentName").value("IT"))
                .andExpect(jsonPath("$.departmentCode").value("IT-001"));
    }
}
