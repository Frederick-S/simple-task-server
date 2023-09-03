package dekiru.simpletask.controller;

import dekiru.simpletask.Constant;
import dekiru.simpletask.annotation.LoginRequired;
import dekiru.simpletask.annotation.Me;
import dekiru.simpletask.dto.ResponseError;
import dekiru.simpletask.dto.TaskDto;
import dekiru.simpletask.dto.UserDto;
import dekiru.simpletask.entity.Task;
import dekiru.simpletask.enums.TaskStatus;
import dekiru.simpletask.repository.TaskRepository;
import dekiru.simpletask.repository.extend.TaskRepositoryExtend;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

/**
 * The api routes for tasks.
 */
@RestController
public class TaskController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskRepositoryExtend taskRepositoryExtend;

    /**
     * Create a new task.
     *
     * @param taskDto The request entity to create a new task
     * @param me      Current user
     * @return The created new task
     */
    @PostMapping("/tasks")
    @LoginRequired
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto, @Me UserDto me) {
        Instant now = Instant.now();
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setLaunchTemplateId(taskDto.getLaunchTemplateId());
        task.setLaunchTemplateVersion(taskDto.getLaunchTemplateVersion());
        task.setStartupScript(taskDto.getStartupScript());
        task.setTimeoutSeconds(taskDto.getTimeoutSeconds());
        task.setSchedule(taskDto.getSchedule());
        task.setStatus(TaskStatus.DISABLED.getValue());
        task.setCreatedBy(me.getId());
        task.setUpdatedBy(me.getId());
        task.setCreatedAt(now);
        task.setUpdatedAt(now);

        taskRepository.save(task);

        taskDto.setId(task.getId());
        taskDto.setStatus(task.getStatus());
        taskDto.setCreatedBy(me.getId());
        taskDto.setUpdatedBy(me.getId());
        taskDto.setCreatedAt(now);
        taskDto.setUpdatedAt(now);

        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    /**
     * Get tasks.
     *
     * @param page     The page number
     * @param pageSize Count of tasks returned in each page
     * @return List of {@link TaskDto}
     */
    @GetMapping("/tasks")
    @LoginRequired
    public ResponseEntity<?> getTasks(@RequestParam int page, @RequestParam int pageSize) {
        if (page <= 0) {
            return new ResponseEntity<>(new ResponseError("Invalid page"), HttpStatus.BAD_REQUEST);
        }

        if (pageSize <= 0) {
            return new ResponseEntity<>(
                    new ResponseError("Invalid pageSize"), HttpStatus.BAD_REQUEST);
        } else if (pageSize > Constant.MAX_PAGE_SIZE) {
            return new ResponseEntity<>(
                    new ResponseError(
                            String.format("pageSize exceeds the limit %d", Constant.MAX_PAGE_SIZE)),
                    HttpStatus.BAD_REQUEST);
        }

        List<TaskDto> taskDtos = taskRepositoryExtend.findAll(page, pageSize)
                .stream()
                .map(task -> {
                    TaskDto taskDto = new TaskDto();
                    taskDto.setId(task.getId());
                    taskDto.setName(task.getName());
                    taskDto.setDescription(task.getDescription());
                    taskDto.setLaunchTemplateId(task.getLaunchTemplateId());
                    taskDto.setLaunchTemplateVersion(task.getLaunchTemplateVersion());
                    taskDto.setStartupScript(task.getStartupScript());
                    taskDto.setTimeoutSeconds(task.getTimeoutSeconds());
                    taskDto.setSchedule(task.getSchedule());
                    taskDto.setStatus(task.getStatus());
                    taskDto.setCreatedBy(task.getCreatedBy());
                    taskDto.setUpdatedBy(task.getUpdatedBy());
                    taskDto.setCreatedAt(task.getCreatedAt());
                    taskDto.setUpdatedAt(task.getUpdatedAt());

                    return taskDto;
                })
                .toList();

        return new ResponseEntity<>(taskDtos, HttpStatus.OK);
    }

    /**
     * Enable a task.
     */
    @PostMapping("/tasks/{id}/enable")
    @LoginRequired
    public ResponseEntity<?> enableTask(@PathVariable long id) {
        return null;
    }

    /**
     * Disable a task.
     */
    @PostMapping("/tasks/{id}/disable")
    @LoginRequired
    public ResponseEntity<?> disableTask(@PathVariable long id) {
        return null;
    }
}
