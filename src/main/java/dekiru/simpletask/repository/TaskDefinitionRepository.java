package dekiru.simpletask.repository;

import dekiru.simpletask.entity.TaskDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository to manipulate the task definition entity.
 */
@Repository
public interface TaskDefinitionRepository extends JpaRepository<TaskDefinition, Long> {
}
