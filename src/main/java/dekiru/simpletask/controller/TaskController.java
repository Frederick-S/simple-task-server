package dekiru.simpletask.controller;

import dekiru.simpletask.Constant;
import dekiru.simpletask.annotation.LoginRequired;
import dekiru.simpletask.annotation.Me;
import dekiru.simpletask.dto.Response;
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
import java.util.Optional;

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
    public ResponseEntity<Response<TaskDto>> createTask(
            @Valid @RequestBody TaskDto taskDto, @Me UserDto me) {
        // TODO: schedule validation
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

        return new ResponseEntity<>(new Response<>(taskDto), HttpStatus.CREATED);
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
    public ResponseEntity<Response<List<TaskDto>>> getTasks(
            @RequestParam int page, @RequestParam int pageSize) {
        if (page <= 0) {
            return new ResponseEntity<>(
                    new Response<>(new ResponseError("Invalid page")), HttpStatus.BAD_REQUEST);
        }

        if (pageSize <= 0) {
            return new ResponseEntity<>(
                    new Response<>(new ResponseError("Invalid pageSize")), HttpStatus.BAD_REQUEST);
        } else if (pageSize > Constant.MAX_PAGE_SIZE) {
            String message = String.format("pageSize exceeds the limit %d", Constant.MAX_PAGE_SIZE);

            return new ResponseEntity<>(
                    new Response<>(new ResponseError(message)), HttpStatus.BAD_REQUEST);
        }

        long count = taskRepository.count();
        int totalPages = Double.valueOf(Math.ceil((double) count / pageSize)).intValue();
        List<TaskDto> taskDtos = taskRepositoryExtend.findAll(page, pageSize)
                .stream()
                .map(TaskDto::fromEntity)
                .toList();
        Response<List<TaskDto>> response = new Response<>(taskDtos);
        response.getMeta().put("totalPages", totalPages);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get task by id.
     *
     * @param id Id of task.
     * @return {@link TaskDto}
     */
    @GetMapping("/tasks/{id}")
    @LoginRequired
    public ResponseEntity<Response<TaskDto>> getTask(@PathVariable long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isEmpty()) {
            return new ResponseEntity<>(new Response<>(new ResponseError("Task not found")),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new Response<>(TaskDto.fromEntity(optionalTask.get())),
                HttpStatus.OK);
    }

    /**
     * Enable a task.
     *
     * @param id Id of task.
     * @return {@link Void}
     */
    @PostMapping("/tasks/{id}/enable")
    @LoginRequired
    public ResponseEntity<Response<Void>> enableTask(@PathVariable long id) {
        return null;
    }

    /**
     * Disable a task.
     *
     * @param id Id of task.
     * @return {@link Void}
     */
    @PostMapping("/tasks/{id}/disable")
    @LoginRequired
    public ResponseEntity<Response<Void>> disableTask(@PathVariable long id) {
        return null;
    }
}
