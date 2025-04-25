package server;

public class Main {
    private static int port = 2000;

    private static String hostname = "localhost";
    private static int pgPort = 5432;
    private static String user = "postgres";
    private static String password = "1234";
    private static String database = "postgres";
    private static int delay = 0;

    private static final String HELP = "help";
    private static final String PG_HOST = "pg_host=";
    private static final String PG_PORT = "pg_port=";
    private static final String PG_USER = "pg_user=";
    private static final String PG_PASS = "pg_pass=";
    private static final String PG_DB = "pg_db=";
    private static final String SERVER_PORT = "srv_port=";
    private static final String DELAY = "delay=";

    public static void main(String[] args) {
        if((args.length == 1) && (args[0].equals(HELP))) {
            System.out.println("Параметры запуска программы : ");
            System.out.println("    help                      вывод справочной информации");
            System.out.println("    pg_host=<hostname>        имя хоста, по умолчанию - localhost");
            System.out.println("    pg_port=<port>            номер порта, по умолчанию - 5432");
            System.out.println("    pg_user=<user>            имя пользователя postgres, по умолчанию - postgres");
            System.out.println("    pg_pass=<password>        пароль пользователя postgres, по умолчанию - 1234");
            System.out.println("    pg_db=<databse>           имя базы данных postgres, по умолчанию - postgres");
            System.out.println("    srv_port=<port>           порт, на котором запущен сервер, по умолчанию - 2000");
            System.out.println("    delay=<delay>             задержка обработки запроса на указанное кол-во секунд, по умолчанию - 0");
            return;
        }

        try {
            for(String str : args) {
                if(str.startsWith(PG_HOST)) hostname = str.substring(PG_HOST.length());
                else if(str.startsWith(PG_PORT)) pgPort = Integer.parseInt(str.substring(PG_PORT.length()));
                else if(str.startsWith(PG_USER)) user = str.substring(PG_USER.length());
                else if(str.startsWith(PG_PASS)) password = str.substring(PG_PASS.length());
                else if(str.startsWith(PG_DB)) database = str.substring(PG_DB.length());
                else if(str.startsWith(SERVER_PORT)) port = Integer.parseInt(str.substring(SERVER_PORT.length()));
                else if(str.startsWith(DELAY)) delay = Integer.parseInt(str.substring(DELAY.length()));
                else {
                    System.out.println("Неправильно заданы аргументы командной строки");
                    return;
                }
            } 
        } catch (Exception e) {
            System.out.println("Неправильно заданы аргументы командной строки");
            return;
        }
                
        ServerLogger logger = new ServerLogger();
        DatabaseManager databaseManager = new DatabaseManager(pgPort, hostname);

        try {
            databaseManager.openConnection(user, password, database);
            System.out.println("Подключение к базе данных установлено.");
        } catch (Exception e) {
            System.out.println("Не удалось подключиться к базе данных." + e);
            return;
        }

        UserManager userManager = new UserManager(databaseManager);

        if(userManager.loadUsers()) {
            System.out.println("Успешно загрузили коллекцию пользователей из базы данных.");
        } else {
            System.out.println("Ошибка загрузки коллекции пользователей из базы данных.");
            databaseManager.closeConnection();
            return;
        }

        VehicleCollection vehicles = new VehicleCollection(databaseManager);

        if(vehicles.loadCollection()) {
            System.out.println("Успешно загрузили коллекцию vehicles из базы данных.");
        } else {
            System.out.println("Ошибка загрузки коллекции vehicles из базы данных.");
            databaseManager.closeConnection();
            return;
        }

        CommandHandler handler = new CommandHandler(vehicles, userManager, logger);

        try {
            System.out.println("Запускаю сервер.");
            Server server = new Server(port, handler, logger, delay);
            server.run();
        } catch (Exception e) {
            System.out.println("Аварийное завершение работы сервера : " + e);
        }

        databaseManager.closeConnection();
    }
}