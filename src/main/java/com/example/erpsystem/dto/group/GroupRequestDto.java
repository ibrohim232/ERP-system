package com.example.erpsystem.dto.group;

import com.example.erpsystem.entity.CourseEntity;
import com.example.erpsystem.entity.UserEntity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
public class GroupRequestDto {
    private UUID mentor;
    private UUID course;
    private List<UUID> students;
}
