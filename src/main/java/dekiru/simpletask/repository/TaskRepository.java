package dekiru.simpletask.repository;

import dekiru.simpletask.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository to manipulate the task entity.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
