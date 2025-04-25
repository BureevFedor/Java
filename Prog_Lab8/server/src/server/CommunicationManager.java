package server;

import communication.Response;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.lang.Thread;

public class CommunicationManager {

    private ServerLogger logger;
    private CommandHandler handler;

    private Receiver receiver;
    private Sender sender;

    private int delay;
    private int task_num = 0;

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public CommunicationManager(CommandHandler handler, Receiver receiver, Sender sender, ServerLogger logger, int delay) {
        this.handler = handler;
        this.receiver = receiver;
        this.sender = sender;
        this.logger = logger;
        this.delay = delay;
    }

    private class CommunicationTask implements Runnable {
        public CommunicationTask() {}

        @Override   
        public void run() {
            logger.info("CommunicationTask : Начало коммуникации");
            RequestInfo info = receiver.receive();
        
            if(info == null) {
                logger.warning("CommunicationTask : Выход из коммуникации по ошибке");
                return;
            }

            if((delay > 0) && (task_num % 2 != 0)) {
                logger.info("CommunicationTask : Задержка " + delay + " секунд");
                try {
                    Thread.sleep(delay * 1000);
                } catch (Exception e) {
                    logger.warning ("CommunicationTask : Ожидание было прервано");
                }
            }

            logger.info("CommunicationTask : Получен запрос : " + info.request.getType());
            Response response = handler.handle(info.request);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    sender.send(response, info.address);
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (Exception e) {
                logger.warning("CommunicationTask : прерывание выполнения потока" + e);
            }

            logger.info("CommunicationTask : Конец коммуникации");
        }    
    }

    public void close() {
        logger.info("CommunicationManager : Подготовка к завершению работы CommunicationManager");
        try {
            cachedThreadPool.shutdown();
            cachedThreadPool.awaitTermination(10, TimeUnit.SECONDS);
            logger.info("CommunicationManager : Работа завершена");
        } catch (InterruptedException e) {
            logger.warning("CommunicationManager : Ошибка завершения работы CommunicationManager : " + e);
        }
    }

    public void run() {
        task_num++;
        cachedThreadPool.submit(new CommunicationTask());
    }
}