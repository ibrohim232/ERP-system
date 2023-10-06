package com.example.erpsystem.controller;

import com.example.erpsystem.dto.group.AddStudentInGroupDto;
import com.example.erpsystem.dto.group.GroupRequestDto;
import com.example.erpsystem.dto.group.GroupResponseDto;
import com.example.erpsystem.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('GROUP_CREATE')")
    @PostMapping("/group/create")
    public GroupResponseDto create(@RequestBody GroupRequestDto dto) {
        return groupService.create(dto);
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('GROUP_GET')")
    @GetMapping("/group/get-all")
    public List<GroupResponseDto> getAll(@RequestParam(defaultValue = "1") int size, @RequestParam(defaultValue = "0") int page) {
        return groupService.getAll(page, size);
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('GROUP_UPDATE')")
    @PostMapping("/group/add-student")
    public void addStudent(@RequestBody AddStudentInGroupDto dto) {
        groupService.addStudent(dto);
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('GROUP_UPDATE')")
    @GetMapping("/group/start-group")
    public void startGroup(@RequestParam UUID groupId) {
        groupService.startGroup(groupId);
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('GROUP_UPDATE')")
    @GetMapping("/group/finish-group")
    public void finishGroup(@RequestParam UUID groupId) {
        groupService.finishGroup(groupId);
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('GROUP_UPDATE')")
    @GetMapping("/group/change-module")
    public void changeModule(@RequestParam UUID groupId) {
        groupService.changeModule(groupId);
    }
}
