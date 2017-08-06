package services.dbService.dao;

import services.dbService.DBService;
import services.PropertyService;
import services.dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO {

    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

//    manual testing
    public static void main(String[] args) throws Exception {

        PropertyService propertyService = new PropertyService();
        DBService dbService = new DBService(propertyService);
        UsersDAO usersDAO = new UsersDAO(dbService.getConnection());

        System.out.println(usersDAO.validateUser("alison", "gradprog2016@07")); // true

    }

    public boolean validateUser(String login, String password) throws SQLException {

        StringBuilder query = new StringBuilder();
        query.append("SELECT EXISTS(SELECT * FROM users ");
        query.append("WHERE user_id = '" + login + "' and user_pwd = '" + password + "')");

        return executor.execQuery(query.toString(), result -> {
                result.next();
                return result.getBoolean(1);
        });
    }

}
