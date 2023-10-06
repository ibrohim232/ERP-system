package com.example.erpsystem.dto.group;

import com.example.erpsystem.dto.base.BaseDto;
import com.example.erpsystem.entity.CourseEntity;
import com.example.erpsystem.entity.UserEntity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
public class GroupResponseDto extends BaseDto {
    @OneToOne
    private UserEntity mentor;
    @ManyToOne
    private CourseEntity course;
    @OneToMany
    private List<UserEntity> students;
    private int module;
}
