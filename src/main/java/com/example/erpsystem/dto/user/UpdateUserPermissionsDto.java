package com.example.erpsystem.dto.user;

import com.example.erpsystem.entity.enums.Permissions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserPermissionsDto {
    private UUID userId;
    private List<Permissions> permissions;
}
