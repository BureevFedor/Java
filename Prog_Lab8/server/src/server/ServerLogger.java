package server;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.nio.charset.StandardCharsets;

public class ServerLogger {
    Logger logger;

    public ServerLogger () {
        logger = Logger.getLogger("server");
        try {
            logger.setUseParentHandlers(false); // Отключает дефолтное логирование в консоль
            FileHandler fh = new FileHandler("log.txt", true); //true обеспечивает добавление к файлу, а не затирание файла
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
            fh.setEncoding(StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            System.out.println("Не удалось настроить запись лога в файл.");
        }            
    }

    public void info (String str) {
        logger.info(Thread.currentThread().getName() + " : " + str);
        System.out.println(Thread.currentThread().getName() + " : " + str);
    }

    public void warning (String str) {
        logger.warning(Thread.currentThread().getName() + " : " + str);
        System.out.println(Thread.currentThread().getName() + " : " + str);
    }
}
