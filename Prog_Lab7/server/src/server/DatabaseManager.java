package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.concurrent.ConcurrentLinkedQueue;

import data.Coordinates;
import data.Vehicle;
import data.VehicleType;
import data.FuelType;
import data.User;

import java.time.LocalDateTime;

public class DatabaseManager {
    private Connection connection;
    private int port;
    private String hostname;

    // Tables
    private static final String VEHICLES_TABLE = "vehicles";
    private static final String USERS_TABLE = "users";

    private static final String VEHICLE_SEQUENCE_FOR_ID = "vehicle_sequence";
    private static final String USERS_SEQUENCE_FOR_ID = "users_sequence";
    
    // Table Vehicle fields
    private static final String VEHICLE_ID = "id";
    private static final String VEHICLE_NAME = "name";
    private static final String VEHICLE_COORD_X = "x";
    private static final String VEHICLE_COORD_Y = "y";
    private static final String VEHICLE_CREATION_DATE = "creation_date";
    private static final String VEHICLE_ENGINE_POWER = "engine_power";
    private static final String VEHICLE_FUEL_CONSUMPTION = "fuel_consumption";
    private static final String VEHICLE_TYPE = "vehicle_type";
    private static final String VEHICLE_FUEL_TYPE = "fuel_type";
    private static final String VEHICLE_USER_ID = "user_id";
    
    // Table users fields
    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_PASSWORD = "password";
    
    // Sequence command
    private static final String CREATE_VEHICLES_ID_SEQUENCE = "CREATE SEQUENCE IF NOT EXISTS " + VEHICLE_SEQUENCE_FOR_ID + " START 1 INCREMENT 1";
    private static final String CREATE_USERS_ID_SEQUENCE = "CREATE SEQUENCE IF NOT EXISTS " + USERS_SEQUENCE_FOR_ID + " START 1 INCREMENT 1";

    // Table commands
    private static final String CREATE_TABLE_VEHICLES = "CREATE TABLE IF NOT EXISTS " + VEHICLES_TABLE + " (" + 
        VEHICLE_ID + " integer, " + 
        VEHICLE_NAME + " text, " + 
        VEHICLE_COORD_X + " real, " +
        VEHICLE_COORD_Y + " bigint, " + 
        VEHICLE_CREATION_DATE + " text, " + 
        VEHICLE_ENGINE_POWER + " integer, " + 
        VEHICLE_FUEL_CONSUMPTION + " bigint, " + 
        VEHICLE_TYPE + " text, " + 
        VEHICLE_FUEL_TYPE + " text, " + 
        VEHICLE_USER_ID + " integer);";
    private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE + " (" +
        USER_ID + " integer, " + 
        USER_NAME + " text, " + 
        USER_PASSWORD + " text);";

    private static final String SELECT_TABLE_VEHICLES = "SELECT * FROM " + VEHICLES_TABLE + ";";

    private static final String SELECT_TABLE_USERS = "SELECT * FROM " + USERS_TABLE + ";";

    private static final String INSERT_VEHICLE = "INSERT INTO " +
        VEHICLES_TABLE + " (" +
        VEHICLE_ID + ", " +
        VEHICLE_NAME + ", " +
        VEHICLE_COORD_X + ", " +
        VEHICLE_COORD_Y + ", " +
        VEHICLE_CREATION_DATE + ", " +
        VEHICLE_ENGINE_POWER + ", " +
        VEHICLE_FUEL_CONSUMPTION + ", " +
        VEHICLE_TYPE + ", " +
        VEHICLE_FUEL_TYPE + ", " + 
        VEHICLE_USER_ID + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String INSERT_USER = "INSERT INTO " +
        USERS_TABLE + " (" +
        USER_ID + ", " +
        USER_NAME + ", " +
        USER_PASSWORD + ") VALUES (?, ?, ?);";

    private static final String UPDATE_VEHICLE = "UPDATE " +
        VEHICLES_TABLE + " SET " +
        VEHICLE_NAME + " = ?, " +
        VEHICLE_COORD_X + " = ?, " +
        VEHICLE_COORD_Y + " = ?, " +
        VEHICLE_CREATION_DATE + " = ?, " +
        VEHICLE_ENGINE_POWER + " = ?, " +
        VEHICLE_FUEL_CONSUMPTION + " = ?, " +
        VEHICLE_TYPE + " = ?, " +
        VEHICLE_FUEL_TYPE + " = ? WHERE " +
        VEHICLE_ID + " = ?;";

    /*private static final String UPDATE_USER = "UPDATE " +
        USERS_TABLE + " SET " +
        USER_ID + " = ?," +
        USER_NAME + " = ?," +
        USER_PASSWORD + " ? WHERE " + USER_ID + " = ?;";
    */

    private static final String DELETE_VEHICLE = "DELETE FROM " +
        VEHICLES_TABLE + " WHERE " + VEHICLE_ID + " = ?;";

    /*private static final String DELETE_USER = "DELETE FROM " +
        USERS_TABLE + " WHERE " + USER_ID + " = ?;";
    */

    //private static final String CLEAR_VEHICLES = "TRUNCATE TABLE " + VEHICLES_TABLE;
    private static final String DELETE_VEHICLES_WITH_ID = "DELETE FROM " + VEHICLES_TABLE + " WHERE " + VEHICLE_USER_ID + " = ?;";

    private static final String DELETE_VEHICLE_IF_LOWER = "DELETE FROM " +
        VEHICLES_TABLE + " WHERE " + VEHICLE_ENGINE_POWER + " < ? AND " + VEHICLE_USER_ID + " = ?;";

    public DatabaseManager(int port, String hostname) {
        this.port = port;
        this.hostname = hostname;
    }

    public void openConnection(String user, String password, String database) throws SQLException, ClassNotFoundException {        
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://" + hostname + ":" + port + "/" + database, user, password);
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Возникла ошибка во время закрытия соединения с базой данных.");
        }
    }

    public ConcurrentLinkedQueue<Vehicle> getVehicles() throws Exception{
        executeCommand(CREATE_TABLE_VEHICLES);
        executeCommand(CREATE_VEHICLES_ID_SEQUENCE);
        try {
            ConcurrentLinkedQueue<Vehicle> vehicles = new ConcurrentLinkedQueue<Vehicle>();
            Statement statement = connection.createStatement();

            ResultSet res = statement.executeQuery(SELECT_TABLE_VEHICLES);
            while(res.next()) {
                Vehicle vehicle = createVehicle(res);
                vehicles.add(vehicle);
            }
            res.close();
            statement.close();
            return vehicles;
        } catch (Exception e) {
            throw new Exception("Ошибка чтения коллекции vehicles из базы данных : " + e);
        }
    }

    public ConcurrentLinkedQueue<User> getUsers() throws Exception{
        executeCommand(CREATE_TABLE_USERS);
        executeCommand(CREATE_USERS_ID_SEQUENCE);
        try {
            ConcurrentLinkedQueue<User> users = new ConcurrentLinkedQueue<User>();
            Statement statement = connection.createStatement();

            ResultSet res = statement.executeQuery(SELECT_TABLE_USERS);
            while(res.next()) {
                User user = createUser(res);
                users.add(user);
            }
            res.close();
            statement.close();
            return users;
        } catch (Exception e) {
            throw new Exception("Ошибка чтения коллекции users из базы данных : " + e);
        }
    }
    
    private Vehicle createVehicle(ResultSet res) throws Exception{
        try {
            int id = res.getInt(VEHICLE_ID);
            String name = res.getString(VEHICLE_NAME);
            Float x = res.getFloat(VEHICLE_COORD_X);
            Long y = res.getLong(VEHICLE_COORD_Y);
            Coordinates coordinates = new Coordinates(x, y);
            LocalDateTime creationDate = LocalDateTime.parse(res.getString(VEHICLE_CREATION_DATE));
            int enginePower = res.getInt(VEHICLE_ENGINE_POWER);
            long fuelConsumption = res.getLong(VEHICLE_FUEL_CONSUMPTION);

            String value;
            value = res.getString(VEHICLE_TYPE);
            VehicleType type = (value.isEmpty()) ? null : VehicleType.valueOf(value);
            value = res.getString(VEHICLE_FUEL_TYPE);
            FuelType fuelType = (value.isEmpty()) ? null : FuelType.valueOf(value);

            int user_id = res.getInt(VEHICLE_USER_ID);

            return new Vehicle(id, name, coordinates, creationDate, enginePower, fuelConsumption, type, fuelType, user_id);
        } catch (Exception e) {
            throw new Exception("Ошибка создания объекта Vehicle : " + e);
        }
    }

    private User createUser(ResultSet res) throws Exception{
        try {
            int id = res.getInt(USER_ID);
            String username = res.getString(USER_NAME);
            String password = res.getString(USER_PASSWORD);

            return new User(id, username, password);
        } catch (Exception e) {
            throw new Exception("Ошибка создания объекта User : " + e);
        }
    }

    private void executeCommand(String command) throws Exception{
        try {
            Statement statement = connection.createStatement();
            statement.execute(command);
            statement.close();
        } catch (Exception e) {
            throw new Exception("Ошибка выполнения команды : " + command + " " + e);
        }
    }

    public int vehiclesGetNextSequenceNumber() throws Exception{
        try{
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT nextval('" + VEHICLE_SEQUENCE_FOR_ID + "');");
            
            res.next();
            int value = res.getInt("nextval");
        
            res.close();
            statement.close();
            return value;
        } catch (Exception e) {
            throw new Exception("Не удалось получить следующий элемент последовательности для базы данных vehicles : " + e);
        }
    }

    public int usersGetNextSequenceNumber() throws Exception{
        try{
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT nextval('" + USERS_SEQUENCE_FOR_ID + "');");
            
            res.next();
            int value = res.getInt("nextval");
        
            res.close();
            statement.close();
            return value;
        } catch (Exception e) {
            throw new Exception("Не удалось получить следующий элемент последовательности для базы данных users: " + e);
        }
    }

    public void insertVehicle(Vehicle vehicle) throws Exception {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_VEHICLE);
            
            statement.setInt(1, vehicle.getId());
            statement.setString(2, vehicle.getName());
            statement.setFloat(3, vehicle.getCoordinates().getX());
            statement.setLong(4, vehicle.getCoordinates().getY());
            statement.setString(5, vehicle.getCreationDate().toString());
            statement.setInt(6, vehicle.getEnginePower());
            statement.setLong(7, vehicle.getFuelConsumption());
            statement.setString(8, (vehicle.getType() == null) ? "" : vehicle.getType().toString());
            statement.setString(9, (vehicle.getFuelType() == null) ? "" : vehicle.getFuelType().toString());
            statement.setInt(10, vehicle.getUserId());

            //System.out.println(statement);
            statement.execute();
        } catch (Exception e) {
            throw new Exception("Не удалось добавить Vehicle в базу данных : " + e);
        }
    }

    public void insertUser(User user) throws Exception {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_USER);
            
            statement.setInt(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());

            //System.out.println(statement);
            statement.execute();
        } catch (Exception e) {
            throw new Exception("Не удалось добавить User в базу данных : " + e);
        }
    }

    public void updateVehicle(int id, Vehicle vehicle) throws Exception {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_VEHICLE);

            statement.setString(1, vehicle.getName());
            statement.setFloat(2, vehicle.getCoordinates().getX());
            statement.setLong(3, vehicle.getCoordinates().getY());
            statement.setString(4, vehicle.getCreationDate().toString());
            statement.setInt(5, vehicle.getEnginePower());
            statement.setLong(6, vehicle.getFuelConsumption());
            statement.setString(7, (vehicle.getType() == null) ? "" : vehicle.getType().toString());
            statement.setString(8, (vehicle.getFuelType() == null) ? "" : vehicle.getFuelType().toString());
            statement.setInt(9, id);

            //System.out.println(statement);
            statement.execute();
        } catch (Exception e) {
            throw new Exception("Не удалось обновить Vehicle в базу данных : " + e);
        }
    }

    public void deleteVehicle(int id) throws Exception {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_VEHICLE);
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception e) {
            throw new Exception("Не удалось удалить Vehicle из базы данных : " + e);
        }
    }

    public void deleteVehiclesIfLower(Vehicle vehicle, int userId) throws Exception {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_VEHICLE_IF_LOWER);
            statement.setInt(1, vehicle.getEnginePower());
            statement.setInt(2, userId);
            statement.execute();
        } catch (Exception e) {
            throw new Exception("Не удалось удалить Vehicle из базы данных : " + e);
        }
    }

    public void clearVehicles(int userId) throws Exception {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_VEHICLES_WITH_ID);
            statement.setInt(1, userId);
            statement.execute();
        } catch (Exception e) {
            throw new Exception("Не удалось удалить vehicles данного пользователя из базы данных : " + e);
        }
    }
}
