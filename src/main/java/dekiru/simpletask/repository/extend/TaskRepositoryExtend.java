package dekiru.simpletask.repository.extend;

import dekiru.simpletask.entity.Task;
import dekiru.simpletask.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepositoryExtend {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll(int page, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);

        return taskRepository.findAll(pageable).toList();
    }
}
