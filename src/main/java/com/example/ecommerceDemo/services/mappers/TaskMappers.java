package com.example.ecommerceDemo.services.mappers;

import com.example.ecommerceDemo.DTO.SectionDTO;
import com.example.ecommerceDemo.DTO.TaskDTO;
import com.example.ecommerceDemo.entities.UserEntity;
import com.example.ecommerceDemo.entities.app.TaskCommentEntity;
import com.example.ecommerceDemo.entities.app.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMappers {


    public static TaskEntity toEntity(TaskDTO taskDTO) {
//        taskEntity.setTaskId(taskDTO.getTaskId());
//        taskEntity.setTaskName(taskDTO.getTaskName());
//        taskEntity.setTaskDescription(taskDTO.getTaskDescription());
//        taskEntity.setTaskStatus(taskDTO.isTaskStatus());
//        taskEntity.setTaskPrioritization(taskDTO.getTaskPrioritization());
//        taskEntity.setStartTime(taskDTO.getStartTime());
//        taskEntity.setEndTime(taskDTO.getEndTime());
//        taskEntity.setTaskImage(taskDTO.getTaskImage());
//        taskEntity.setTaskImagePath(taskDTO.getTaskImagePath());

        TaskEntity.Builder builder = new TaskEntity.Builder()
                .taskId(taskDTO.getTaskId())
                .taskName(taskDTO.getTaskName())
                .taskDescription(taskDTO.getTaskDescription())
                .taskStatus(taskDTO.isTaskStatus())
                .taskPrioritization(taskDTO.getTaskPrioritization())
                .startTime(taskDTO.getStartTime())
                .endTime(taskDTO.getEndTime())
                .taskImage(taskDTO.getTaskImage())
                .taskImagePath(taskDTO.getTaskImagePath());

        TaskEntity taskEntity = builder.build();


        if (taskDTO.getSectionDTO() != null) {
            taskEntity.setSectionEntity(SectionMappers.toEntity(taskDTO.getSectionDTO()));
        }

        if (taskDTO.getTaskComments() != null) {
            List<TaskCommentEntity> taskCommentEntities = taskDTO.getTaskComments().stream()
                    .map(taskCommentDTO -> {
                        TaskCommentEntity taskCommentEntity = new TaskCommentEntity();
                        taskCommentEntity.setCommentText(taskCommentDTO.getCommentText());
                        taskCommentEntity.setTask(taskEntity);
                        return taskCommentEntity;
                    })
                    .collect(Collectors.toList());
            taskEntity.setTaskComments(taskCommentEntities);
        }

        if (taskDTO.getAssignedUsers() != null) {
            taskEntity.setAssignedUsers(taskDTO.getAssignedUsers().stream().map(id -> {
                UserEntity user = new UserEntity();
                user.setUserId(id);
                return user;
            }).collect(Collectors.toList()));
        }
        return taskEntity;
    }

    public static TaskDTO toDTO(TaskEntity taskEntity) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(taskEntity.getTaskId());
        taskDTO.setTaskName(taskEntity.getTaskName());
        taskDTO.setTaskDescription(taskEntity.getTaskDescription());
        taskDTO.setTaskStatus(taskEntity.isTaskStatus());
        taskDTO.setTaskPrioritization(taskEntity.getTaskPrioritization());
        taskDTO.setStartTime(taskEntity.getStartTime());
        taskDTO.setEndTime(taskEntity.getEndTime());
        taskDTO.setTaskImage(taskEntity.getTaskImage());
        taskDTO.setTaskImagePath(taskEntity.getTaskImagePath());

        if (taskEntity.getSectionEntity() != null) {
            SectionDTO sectionDTO = new SectionDTO();
            sectionDTO.setSectionId(taskEntity.getSectionEntity().getSectionId());
            sectionDTO.setSectionName(taskEntity.getSectionEntity().getSectionName());
            taskDTO.setSectionDTO(sectionDTO);
        }
//        if (taskEntity.getTaskComments() != null) {
//            List<TaskCommentDTO> taskCommentDTOs = taskEntity.getTaskComments().stream()
//                    .map(TaskCommentService::toDTO)
//                    .collect(Collectors.toList());
//            taskDTO.setTaskComments(taskCommentDTOs);
//        }
        if (taskEntity.getTaskComments() != null) {
            taskDTO.setTaskComments(taskEntity.getTaskComments().stream()
                    .map(TaskCommentMappers::toDTO)
                    .collect(Collectors.toList()));
        } else {
            taskDTO.setTaskComments(new ArrayList<>());
    }

        if (taskEntity.getAssignedUsers() != null) {
            taskDTO.setAssignedUsers(taskEntity.getAssignedUsers()
                    .stream()
                    .map(UserEntity::getUserId)
                    .collect(Collectors.toList()));
        } else {
            taskDTO.setSectionDTO(null);
        }
        return taskDTO;
    }


}
