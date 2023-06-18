package dekiru.simpletask.repository.extend;

import dekiru.simpletask.entity.Task;
import dekiru.simpletask.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The extended repository that manipulates task entities.
 */
@Repository
public class TaskRepositoryExtend {
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Find tasks.
     *
     * @param page     The page number
     * @param pageSize Count of ting practices returned in each page
     * @return List of {@link Task}
     */
    public List<Task> findAll(int page, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);

        return taskRepository.findAll(pageable).toList();
    }
}
