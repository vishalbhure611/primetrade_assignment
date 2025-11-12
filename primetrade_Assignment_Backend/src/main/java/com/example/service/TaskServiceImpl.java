package com.example.service;

import com.example.dao.TaskRepository;
import com.example.dto.TaskDto;
import com.example.entity.Task;
import com.example.entity.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(TaskDto taskDto, Long userId) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus() != null ? taskDto.getStatus() : TaskStatus.PENDING);
        task.setUserId(userId);
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id, Long userId) {
        return taskRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Task not found or access denied"));
    }

    @Override
    public List<Task> getAllTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    @Override
    public Task updateTask(Long id, TaskDto taskDto, Long userId) {
        Task task = getTaskById(id, userId);
        if (taskDto.getTitle() != null) {
            task.setTitle(taskDto.getTitle());
        }
        if (taskDto.getDescription() != null) {
            task.setDescription(taskDto.getDescription());
        }
        if (taskDto.getStatus() != null) {
            task.setStatus(taskDto.getStatus());
        }
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id, Long userId) {
        if (!taskRepository.findByIdAndUserId(id, userId).isPresent()) {
            throw new RuntimeException("Task not found or access denied");
        }
        taskRepository.deleteByIdAndUserId(id, userId);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskByIdForAdmin(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public Task updateTaskForAdmin(Long id, TaskDto taskDto) {
        Task task = getTaskByIdForAdmin(id);
        if (taskDto.getTitle() != null) {
            task.setTitle(taskDto.getTitle());
        }
        if (taskDto.getDescription() != null) {
            task.setDescription(taskDto.getDescription());
        }
        if (taskDto.getStatus() != null) {
            task.setStatus(taskDto.getStatus());
        }
        return taskRepository.save(task);
    }

    @Override
    public void deleteTaskForAdmin(Long id) {
        if (!taskRepository.findById(id).isPresent()) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(id);
    }
}

