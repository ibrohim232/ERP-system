package com.example.erpsystem.service;

import com.example.erpsystem.dto.group.GroupRequestDto;
import com.example.erpsystem.dto.group.GroupResponseDto;
import com.example.erpsystem.entity.GroupEntity;
import com.example.erpsystem.repository.GroupRepository;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GroupService extends BaseService<GroupEntity, UUID, GroupRepository, GroupResponseDto, GroupRequestDto> {
    public GroupService(GroupRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
    }

    @Override
    protected GroupResponseDto mapEntityToRES(GroupEntity entity) {
        return null;
    }

    @Override
    protected GroupEntity mapCRToEntity(GroupRequestDto createReq) {
        return null;
    }
}
