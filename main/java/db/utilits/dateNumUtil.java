package db.utilits;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class dateNumUtil {
    public static String dateTimeNow() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }

    public static int randomNum(int maxNum) {
        Random random = new Random();
        return random.nextInt(maxNum) + 1;
    }
}
