package dekiru.simpletask.service;

import dekiru.simpletask.dto.UserDto;
import dekiru.simpletask.entity.Task;
import dekiru.simpletask.enums.TaskStatus;
import dekiru.simpletask.repository.TaskRepository;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * The service that manages tasks.
 */
@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Enable a task.
     *
     * @param id The task id
     * @param userDto The current user
     */
    @Transactional
    public void enable(long id, UserDto userDto) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        Validate.isTrue(optionalTask.isPresent(), "Task not found");

        // TODO: schedule task
        Task task = optionalTask.get();
        Validate.isTrue(task.getStatus() == TaskStatus.DISABLED.getValue(),
                "Task is already enabled");

        task.setStatus(TaskStatus.ENABLED.getValue());
        task.setUpdatedBy(userDto.getId());
        task.setUpdatedAt(Instant.now());
        taskRepository.save(task);
    }

    /**
     * Disable a task.
     *
     * @param id The task id
     * @param userDto The current user
     */
    @Transactional
    public void disable(long id, UserDto userDto) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        Validate.isTrue(optionalTask.isPresent(), "Task not found");

        // TODO: remove task schedule
        Task task = optionalTask.get();
        Validate.isTrue(task.getStatus() == TaskStatus.ENABLED.getValue(),
                "Task is already disabled");

        task.setStatus(TaskStatus.DISABLED.getValue());
        task.setUpdatedBy(userDto.getId());
        task.setUpdatedAt(Instant.now());
        taskRepository.save(task);
    }
}
