package dekiru.simpletask.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * A simple generic task scheduler.
 */
@Service
public class TaskSchedulerService {
    private static final Logger logger = LoggerFactory.getLogger(TaskSchedulerService.class);

    private static final TimeZone utcTimeZone = TimeZone.getTimeZone(ZoneId.of("UTC"));

    private Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @Autowired
    private TaskScheduler taskScheduler;

    /**
     * Schedule a generic task with given cron expression.
     *
     * @param taskId         Id of task
     * @param task           Task to run
     * @param cronExpression Cron expression
     */
    public void scheduleTask(String taskId, Runnable task, String cronExpression) {
        if (scheduledTasks.containsKey(taskId)) {
            throw new IllegalArgumentException(
                    String.format("Task %s is already scheduled", taskId));
        }

        synchronized (this) {
            if (scheduledTasks.containsKey(taskId)) {
                return;
            }

            CronTrigger cronTrigger = new CronTrigger(cronExpression, utcTimeZone);
            ScheduledFuture<?> scheduledTask = taskScheduler.schedule(task, cronTrigger);

            if (scheduledTask != null) {
                scheduledTasks.put(taskId, scheduledTask);
            }

            logger.info("Task scheduler - scheduled a task, id={}, run frequency={}",
                    taskId, cronExpression);
        }
    }

    /**
     * Cancel and remove a task.
     *
     * @param taskId Id of task
     */
    public void cancelTask(String taskId) {
        ScheduledFuture<?> scheduledTask = scheduledTasks.remove(taskId);

        if (scheduledTask != null) {
            scheduledTask.cancel(false);

            logger.info("Task scheduler - canceled a scheduled task, task id={}", taskId);
        }
    }
}
