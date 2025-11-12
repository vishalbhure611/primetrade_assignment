package com.example.service;

import com.example.dto.TaskDto;
import com.example.entity.Task;

import java.util.List;

public interface TaskService {
    Task createTask(TaskDto taskDto, Long userId);
    Task getTaskById(Long id, Long userId);
    Task getTaskByIdForAdmin(Long id); 
    List<Task> getAllTasksByUserId(Long userId);
    Task updateTask(Long id, TaskDto taskDto, Long userId);
    Task updateTaskForAdmin(Long id, TaskDto taskDto);
    void deleteTask(Long id, Long userId);
    void deleteTaskForAdmin(Long id); 
    List<Task> getAllTasks(); 
}

