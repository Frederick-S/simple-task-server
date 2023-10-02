package dekiru.simpletask.util;

import org.springframework.scheduling.support.CronExpression;

/**
 * Cron utils.
 */
public class CronUtil {

    /**
     * Check if the given cron expression is valid.
     *
     * @param cronExpression The cron expression to check
     * @return True if valid, false otherwise
     */
    public static boolean isValidCronExpression(String cronExpression) {
        try {
            CronExpression.parse(cronExpression);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
