package client.graphics;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Properties;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class Resources {
    public static String name = "client.resources.client";

    public static String[] languages = {"русский (RU)", "portuguesa (PT)", "español (ES)", "català (CA)", "english (EN)"};

    public static String WINDOW_TITLE = "windowTitle";
    public static String WINDOW_TITLE_USER = "windowTitleUser";
    public static String FILTER_LABEL = "filterLabel";
    public static String BUTTON_HELP = "buttonHelp";
    public static String BUTTON_INFO = "buttonInfo";
    public static String BUTTON_ADD = "buttonAdd";
    public static String BUTTON_UPDATE = "buttonUpdate";
    public static String BUTTON_REMOVE_BY_ID = "buttonRemoveById";
    public static String BUTTON_REMOVE_BY_INDEX = "buttonRemoveByIndex";
    public static String BUTTON_REMOVE_LOWER = "buttonRemoveLower";
    public static String BUTTON_CLEAR = "buttonClear";
    public static String BUTTON_EXECUTE_SCRIPT = "buttonExecuteScript";
    public static String BUTTON_SORT = "buttonSort";
    public static String BUTTON_FILTER_STARTS_WITH_NAME = "buttonFilterStartsWithName";
    public static String BUTTON_PRINT_UNIQUE_FUEL_TYPES = "buttonPrintUniqueFuelTypes";
    public static String BUTTON_PRINT_FIELD_DESCENDING_TYPE = "buttonPrintFieldDescendingType";

    public static String LABEL_VEHICLE_NAME = "labelVehicleName";
    public static String LABEL_VEHICLE_X = "labelVehicleX";
    public static String LABEL_VEHICLE_Y = "labelVehicleY";
    public static String LABEL_VEHICLE_ENGINE_POWER = "labelVehicleEnginePower";
    public static String LABEL_VEHICLE_FUEL_CONSUMPTION = "labelVehicleFuelConsumption";
    public static String LABEL_VEHICLE_TYPE = "labelVehicleType";
    public static String LABEL_VEHICLE_FUEL_TYPE = "labelVehicleFuelType";

    public static String ELEMENT_TITLE = "elementTitle";
    public static String ADD_ELEMENT_TITLE = "addElementTitle";
    public static String UPDATE_ELEMENT_TITLE = "updateElementTitle";
    public static String ERROR_TITLE = "errorTitle";

    public static String AUTH_TITLE = "authTitle";
    public static String LABEL_LOGIN = "labelLogin";
    public static String LABEL_PASSWORD = "labelPassword";
    public static String BUTTON_ENTER = "buttonEnter";
    public static String TEXT_ENTER_LOGIN_AND_PASSWORD = "textEnterLoginAndPassword";

    public static String TEXT_ENTER_ID_UPDATE = "textEnterIdUpdate";
    public static String TEXT_ENTER_ID_REMOVE = "textEnterIdRemove";
    public static String TEXT_ENTER_INDEX_REMOVE = "textEnterIndexRemove";
    public static String TEXT_INVALID_INDEX = "textInvalidIndex";
    public static String TEXT_ENTER_ID_REMOVE_LOWER = "textEnterIdRemoveLower";
    public static String TEXT_ID_IS_NOT_FOUND = "textIdIsNotFound";
    public static String TEXT_INVALID_ID = "textInvalidId";
    public static String TEXT_ENTER_FILE_NAME = "textEnterFileName";

    public static String TEXT_CANT_CONNECT_TO_SERVER = "textCantConnectToServer";
    public static String TEXT_CANT_READ_FROM_SOCKET = "textCantReadFromSocket";

    public static String TEXT_ERROR = "textError";
    public static String TEXT_AUTH_ERROR = "textAuthError";

    public static void setLanguage(int index) {
        switch (index) {
            case 0:
                Locale.setDefault(new Locale("ru", "RU"));
                break;
            case 1:
                Locale.setDefault(new Locale("pt", "PT"));
                break;
            case 2:
                Locale.setDefault(new Locale("es", "SV"));
                break;
            case 3:
                Locale.setDefault(new Locale("ca", "ES"));
                break;
            case 4:
                Locale.setDefault(new Locale("en", "GB"));
                break;
            default:
                break;
        }
        ResourceBundle.clearCache();
    }

    public static String getString(String key) {
        return ResourceBundle.getBundle(Resources.name).getString(key);
    }

    public static void saveProperties() {
        try (OutputStream output = new FileOutputStream("client_ru.properties")) {
            Properties prop = new Properties();
            
            prop.setProperty("windowTitle", "КОЛЛЕКЦИЯ ТРАНСПОРТА");
            prop.setProperty("windowTitleUser", "Пользователь");
            prop.setProperty("filterLabel", "Фильтр");
            prop.setProperty("buttonHelp", "СПРАВКА");
            prop.setProperty("buttonInfo", "ИНФОРМАЦИЯ");
            prop.setProperty("buttonAdd", "ДОБАВИТЬ");
            prop.setProperty("buttonUpdate", "ОБНОВИТЬ");
            prop.setProperty("buttonRemoveById", "УДАЛИТЬ ПО ID");
            prop.setProperty("buttonRemoveByIndex", "УДАЛИТЬ ПО ИНДЕКСУ");
            prop.setProperty("buttonRemoveLower", "УДАЛИТЬ МЕНЬШЕ");
            prop.setProperty("buttonClear", "УДАЛИТЬ ВСЁ");
            prop.setProperty("buttonExecuteScript", "СКРИПТ");
            prop.setProperty("buttonSort", "СОРТИРОВАТЬ");
            prop.setProperty("buttonFilterStartsWithName", "ФИЛЬТР ПО ИМЕНИ");
            prop.setProperty("buttonPrintUniqueFuelTypes", "УНИКАЛЬНЫЕ ТИПЫ ТОПЛИВА");
            prop.setProperty("buttonPrintFieldDescendingType", "ОБРАТНО ПО ТИПУ");

            prop.setProperty( "labelVehicleName", "Название");
            prop.setProperty( "labelVehicleX", "Координата X");
            prop.setProperty( "labelVehicleY", "Координата Y");
            prop.setProperty( "labelVehicleEnginePower", "Мощь двигателя");
            prop.setProperty( "labelVehicleFuelConsumption", "Потребление топлива");
            prop.setProperty( "labelVehicleType", "Тип");
            prop.setProperty( "labelVehicleFuelType", "Тип топлива");
            
            prop.setProperty( "elementTitle", "Элемент");
            prop.setProperty( "addElementTitle", "Добавление элемента");
            prop.setProperty( "updateElementTitle", "Обновление элемента");
            prop.setProperty( "errorTitle", "Ошибка");
        
            prop.setProperty( "authTitle", "Авторизация");
            prop.setProperty( "labelLogin", "Имя" );
            prop.setProperty( "labelPassword", "Пароль" );
            prop.setProperty( "buttonEnter", "Войти" );
            prop.setProperty( "textEnterLoginAndPassword", "Введите имя и пароль !" );
            
            prop.setProperty( "textEnterIdUpdate", "Введите Id обновляемого объекта" );
            prop.setProperty( "textEnterIdRemove", "Введите Id удаляемого объекта" );
            prop.setProperty( "textEnterIndexRemove", "Введите индекс удаляемого объекта" );
            prop.setProperty( "textInvalidIndex", "Неправильное значение индекса" );
            prop.setProperty( "textEnterIdRemoveLower", "Введите id объекта, для которого программа должна удалить все объекты с меньшим значением enginePower, чем у него." );
            prop.setProperty( "textIdIsNotFound", "Элемент не найден." );
            prop.setProperty( "textInvalidId", "Неправильное значение id" );
            prop.setProperty( "textEnterFileName", "Введите имя файла." );

            prop.setProperty( "textCantConnectToServer", "Не удалось подключиться к серверу." );
            prop.setProperty( "textCantReadFromSocket", "Не получилось прочитать ответ из сокета" );

            prop.setProperty( "textError", "Ошибка" );
            prop.setProperty( "textAuthError", "Не удалось авторизоваться" );
            //prop.setProperty( "textNoUniqueFuelType", "В коллекции нет элементов с уникальным типом топлива." );
            //prop.setProperty( "textRemoved", "Данные успешно удалены." );
            // Help
            // info
            // server responces

            prop.store(output, null);
            //System.out.println(prop);
        } catch (Exception e) {
            System.out.println("Save properties error : " + e);
        }

        try (OutputStream output = new FileOutputStream("client_en.properties")) {
            Properties prop = new Properties();
    
            prop.setProperty("windowTitle", "VEHICLES COLLECTION");
            prop.setProperty("windowTitleUser", "User");
            prop.setProperty("filterLabel", "Filter");
            prop.setProperty("buttonHelp", "HELP");
            prop.setProperty("buttonInfo", "INFO");
            prop.setProperty("buttonAdd", "ADD");
            prop.setProperty("buttonUpdate", "UPDATE");
            prop.setProperty("buttonRemoveById", "REMOVE BY ID");
            prop.setProperty("buttonRemoveByIndex", "REMOVE AT INDEX");
            prop.setProperty("buttonRemoveLower", "REMOVE LOWER");
            prop.setProperty("buttonClear", "CLEAR");
            prop.setProperty("buttonExecuteScript", "EXECUTE SCRIPT");
            prop.setProperty("buttonSort", "SORT");
            prop.setProperty("buttonFilterStartsWithName", "Filter starts with name");
            prop.setProperty("buttonPrintUniqueFuelTypes", "Print unique fuel types");
            prop.setProperty("buttonPrintFieldDescendingType", "Print field descending type");

            prop.setProperty( "labelVehicleName", "Name");
            prop.setProperty( "labelVehicleX", "Coordinate X");
            prop.setProperty( "labelVehicleY", "Coordinate Y");
            prop.setProperty( "labelVehicleEnginePower", "Engine power");
            prop.setProperty( "labelVehicleFuelConsumption", "Fuel comsumption");
            prop.setProperty( "labelVehicleType", "Type");
            prop.setProperty( "labelVehicleFuelType", "Fuel type");

            prop.setProperty( "elementTitle", "Element");
            prop.setProperty( "addElementTitle", "Add element");
            prop.setProperty( "updateElementTitle", "Update element");
            prop.setProperty( "errorTitle", "Error");

            prop.setProperty( "authTitle", "Authorization");
            prop.setProperty( "labelLogin", "Login" );
            prop.setProperty( "labelPassword", "Password" );
            prop.setProperty( "buttonEnter", "Enter" );
            prop.setProperty( "textEnterLoginAndPassword", "Enter login and password !" );
            
            prop.setProperty( "textEnterIdUpdate","Enter the Id of the object to be updated" );
            prop.setProperty( "textEnterIdRemove", "Enter the Id of the object to be deleted");
            prop.setProperty( "textEnterIndexRemove", "Enter the index of the object to be deleted");
            prop.setProperty( "textInvalidIndex", "Incorrect index value");
            prop.setProperty( "textEnterIdRemoveLower", "Enter the id of the object for which the program should delete all objects with a lower enginePower value than it.");
            prop.setProperty( "textIdIsNotFound", "Element not found.");
            prop.setProperty( "textInvalidId", "Wrong id value");
            prop.setProperty( "textEnterFileName", "Enter file name.");

            prop.setProperty( "textCantConnectToServer", "Failed to connect to server.");
            prop.setProperty( "textCantReadFromSocket", "Failed to read response from socket");
            
            prop.setProperty( "textError", "Error" );
            prop.setProperty( "textAuthError", "Failed to login" );

            prop.store(output, null);
            //System.out.println(prop);
        } catch (Exception e) {
            System.out.println("Save properties error : " + e);
        }
            
        try (OutputStream output = new FileOutputStream("client_ca.properties")) {
            Properties prop = new Properties();

            prop.setProperty("windowTitle", "RECOLLIDA DE VEHICLES");
            prop.setProperty("windowTitleUser", "Usuari");
            prop.setProperty("filterLabel", "Filtre");
            prop.setProperty("buttonHelp", "AJUDA");
            prop.setProperty("buttonInfo", "INFORMACIÓ");
            prop.setProperty("buttonAdd", "AFEGIR");
            prop.setProperty("buttonUpdate", "ACTUALITZACIÓ");
            prop.setProperty("buttonRemoveById", "ELIMINAR PER ID");
            prop.setProperty("buttonRemoveByIndex", "ELIMINAR A L'ÍNDEX");
            prop.setProperty("buttonRemoveLower", "TREURE INFERIOR");
            prop.setProperty("buttonClear", "CLAR");
            prop.setProperty("buttonExecuteScript", "EXECUTAR EL GUIÓ");
            prop.setProperty("buttonSort", "ORDENAR");
            prop.setProperty("buttonFilterStartsWithName", "El filtre comença amb el nom");
            prop.setProperty("buttonPrintUniqueFuelTypes", "Imprimeix tipus de combustible únics");
            prop.setProperty("buttonPrintFieldDescendingType", "Camp d'impressió de tipus descendent");

            prop.setProperty( "labelVehicleName", "Nom");
            prop.setProperty( "labelVehicleX", "Coordinar X");
            prop.setProperty( "labelVehicleY", "Coordinar Y");
            prop.setProperty( "labelVehicleEnginePower", "Potència del motor");
            prop.setProperty( "labelVehicleFuelConsumption", "Consum de combustible");
            prop.setProperty( "labelVehicleType", "Tipus");
            prop.setProperty( "labelVehicleFuelType", "Tipus de combustible");
            
            prop.setProperty( "elementTitle", "Element");
            prop.setProperty( "addElementTitle", "Afegeix un element");
            prop.setProperty( "updateElementTitle", "Actualitzar l'element");
            prop.setProperty( "errorTitle", "Error");

            prop.setProperty( "authTitle", "Autorització");
            prop.setProperty( "labelLogin", "iniciar Sessió" );
            prop.setProperty( "labelPassword", "Contrasenya" );
            prop.setProperty( "buttonEnter", "Entra" );
            prop.setProperty( "textEnterLoginAndPassword", "Introdueix el login i la contrasenya!" );
            
            prop.setProperty( "textEnterIdUpdate","Introduïu l'identificador de l'objecte que voleu actualitzar" );
            prop.setProperty( "textEnterIdRemove", "Introduïu l'identificador de l'objecte que voleu suprimir");
            prop.setProperty( "textEnterIndexRemove", "Introduïu l'índex de l'objecte que voleu suprimir");
            prop.setProperty( "textInvalidIndex", "Valor de l'índex incorrecte");
            prop.setProperty( "textEnterIdRemoveLower", "Introduïu l'identificador de l'objecte per al qual el programa hauria d'eliminar tots els objectes amb un valor enginePower més baix que ell.");
            prop.setProperty( "textIdIsNotFound", "Element no trobat.");
            prop.setProperty( "textInvalidId", "Valor d'identificador incorrecte");
            prop.setProperty( "textEnterFileName", "Introduïu el nom del fitxer.");

            prop.setProperty( "textCantConnectToServer", "Ha fallat la connexió amb el servidor.");
            prop.setProperty( "textCantReadFromSocket", "No s'ha pogut llegir la resposta del sòcol");

            prop.setProperty( "textError", "Error" );
            prop.setProperty( "textAuthError", "No s'ha pogut iniciar la sessió" );

            prop.store(output, null);
            //System.out.println(prop);
        } catch (Exception e) {
            System.out.println("Save properties error : " + e);
        }

        try (OutputStream output = new FileOutputStream("client_es.properties")) {
            Properties prop = new Properties();

            prop.setProperty("windowTitle", "COLECCIÓN DE VEHÍCULOS");
            prop.setProperty("windowTitleUser", "Usuario");
            prop.setProperty("filterLabel", "Filtrar");
            prop.setProperty("buttonHelp", "AYUDA");
            prop.setProperty("buttonInfo", "INFORMACIÓN");
            prop.setProperty("buttonAdd", "AGREGAR");
            prop.setProperty("buttonUpdate", "ACTUALIZAR");
            prop.setProperty("buttonRemoveById", "ELIMINAR POR ID");
            prop.setProperty("buttonRemoveByIndex", "ELIMINAR EN EL ÍNDICE");
            prop.setProperty("buttonRemoveLower", "QUITAR INFERIOR");
            prop.setProperty("buttonClear", "CLARO");
            prop.setProperty("buttonExecuteScript", "EJECUTAR GUIÓN");
            prop.setProperty("buttonSort", "CLASIFICAR");
            prop.setProperty("buttonFilterStartsWithName", "El filtro comienza con el nombre");
            prop.setProperty("buttonPrintUniqueFuelTypes", "Imprimir tipos de combustible únicos");
            prop.setProperty("buttonPrintFieldDescendingType", "Tipo de campo de impresión descendente");

            prop.setProperty( "labelVehicleName", "Nombre");
            prop.setProperty( "labelVehicleX", "Coordinar X");
            prop.setProperty( "labelVehicleY", "Coordinar Y");
            prop.setProperty( "labelVehicleEnginePower", "Potencia del motor");
            prop.setProperty( "labelVehicleFuelConsumption", "Consumo de combustible");
            prop.setProperty( "labelVehicleType", "Tipo");
            prop.setProperty( "labelVehicleFuelType", "Tipo de combustible");

            prop.setProperty( "elementTitle", "Elemento");
            prop.setProperty( "addElementTitle", "Agregar elemento");
            prop.setProperty( "updateElementTitle", "Actualizar elemento");
            prop.setProperty( "errorTitle", "Error");

            prop.setProperty( "authTitle", "Autorización");
            prop.setProperty( "labelLogin", "Acceso" );
            prop.setProperty( "labelPassword", "Contraseña" );
            prop.setProperty( "buttonEnter", "Ingresar" );
            prop.setProperty( "textEnterLoginAndPassword", "¡Ingrese el nombre de usuario y la contraseña!" );
            
            prop.setProperty( "textEnterIdUpdate","Introduzca el Id del objeto a actualizar" );
            prop.setProperty( "textEnterIdRemove", "Introduzca el Id del objeto a eliminar");
            prop.setProperty( "textEnterIndexRemove", "Introduzca el índice del objeto a eliminar");
            prop.setProperty( "textInvalidIndex", "Valor de índice incorrecto");
            prop.setProperty( "textEnterIdRemoveLower", "Ingrese la identificación del objeto para el cual el programa debe eliminar todos los objetos con un valor de potencia de motor más bajo que él.");
            prop.setProperty( "textIdIsNotFound", "Elemento no encontrado.");
            prop.setProperty( "textInvalidId", "Valor de identificación incorrecto");
            prop.setProperty( "textEnterFileName", "Introduzca el nombre del archivo.");

            prop.setProperty( "textCantConnectToServer", "Error al conectar con el servidor.");
            prop.setProperty( "textCantReadFromSocket", "Error al leer la respuesta del socketа");

            prop.setProperty( "textError", "Error" );
            prop.setProperty( "textAuthError", "Error al iniciar sesión" );

            prop.store(output, null);
            //System.out.println(prop);
        } catch (Exception e) {
            System.out.println("Save properties error : " + e);
        }

        try (OutputStream output = new FileOutputStream("client_pt.properties")) {
            Properties prop = new Properties();

            prop.setProperty("windowTitle", "RECOLHA DE VEÍCULOS");
            prop.setProperty("windowTitleUser", "Do utilizador");
            prop.setProperty("filterLabel", "Filtro");
            prop.setProperty("buttonHelp", "AJUDA");
            prop.setProperty("buttonInfo", "INFORMAÇÃO");
            prop.setProperty("buttonAdd", "ADICIONAR");
            prop.setProperty("buttonUpdate", "ATUALIZAR");
            prop.setProperty("buttonRemoveById", "REMOVER POR ID");
            prop.setProperty("buttonRemoveByIndex", "REMOVER NO ÍNDICE");
            prop.setProperty("buttonRemoveLower", "REMOVER INFERIOR");
            prop.setProperty("buttonClear", "CLARO");
            prop.setProperty("buttonExecuteScript", "EXECUTAR SCRIPT");
            prop.setProperty("buttonSort", "ORGANIZAR");
            prop.setProperty("buttonFilterStartsWithName", "O filtro começa com o nome");
            prop.setProperty("buttonPrintUniqueFuelTypes", "Imprimir tipos de combustível exclusivos");
            prop.setProperty("buttonPrintFieldDescendingType", "Tipo descendente do campo de impressão");

            prop.setProperty( "labelVehicleName", "Nome");
            prop.setProperty( "labelVehicleX", "Coordenada X");
            prop.setProperty( "labelVehicleY", "Coordenada Y");
            prop.setProperty( "labelVehicleEnginePower", "Poder do motor");
            prop.setProperty( "labelVehicleFuelConsumption", "Consumo de combustível");
            prop.setProperty( "labelVehicleType", "Tipo");
            prop.setProperty( "labelVehicleFuelType", "Tipo de combustível");
            
            prop.setProperty( "elementTitle", "Elemento");
            prop.setProperty( "addElementTitle", "Adicionar elemento");
            prop.setProperty( "updateElementTitle", "Atualizar elemento");
            prop.setProperty( "errorTitle", "Erro");

            prop.setProperty( "authTitle", "Autorização");
            prop.setProperty( "labelUser", "Conecte-se" );
            prop.setProperty( "labelPassword", "Senha" );
            prop.setProperty( "buttonEnter", "Digitar" );
            prop.setProperty( "textEnterLoginAndPassword", "Digite login e senha!" );
            
            prop.setProperty( "textEnterIdUpdate","Digite o Id do objeto a ser atualizado" );
            prop.setProperty( "textEnterIdRemove", "Digite o Id do objeto a ser deletado");
            prop.setProperty( "textEnterIndexRemove", "Insira o índice do objeto a ser excluído");
            prop.setProperty( "textInvalidIndex", "Valor de índice incorreto");
            prop.setProperty( "textEnterIdRemoveLower", "Digite o id do objeto para o qual o programa deve excluir todos os objetos com um valor enginePower menor do que ele.");
            prop.setProperty( "textIdIsNotFound", "Elemento não encontrado.");
            prop.setProperty( "textInvalidId", "Valor de id errado");
            prop.setProperty( "textEnterFileName", "Digite o nome do arquivo.");

            prop.setProperty( "textCantConnectToServer", "Falha ao conectar ao servidor.");
            prop.setProperty( "textCantReadFromSocket", "Falha ao ler a resposta do soquete");

            prop.setProperty( "textError", "Erro" );
            prop.setProperty( "textAuthError", "Falha ao fazer login" );

            prop.store(output, null);
            System.out.println(prop);
        } catch (Exception e) {
            System.out.println("Save properties error : " + e);
        }
    }

    public static void init() {
        //saveProperties();
        Locale.setDefault(new Locale("ru", "RU"));
    }
}
