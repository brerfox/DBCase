package services.dbService.dao;

import services.dbService.DBService;
import services.PropertyService;
import services.dbService.executor.Executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsersDAO {

    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

//    manual testing
    public static void main(String[] args) throws Exception {

        PropertyService propertyService = PropertyService.getInstance();
        DBService dbService = new DBService(propertyService);
        UsersDAO usersDAO = new UsersDAO(dbService.getConnection());

        System.out.println(usersDAO.validateUser("alison", "gradprog2016@07")); // true

    }

    public boolean validateUser(String login, String password) throws SQLException {

        String query = "SELECT EXISTS(SELECT * FROM users " +
                "WHERE user_id = ? AND user_pwd = ?)";
        PreparedStatement stmt = executor.getPreparedStatement(query);
        stmt.setString(1, login);
        stmt.setString(2, password);

        return executor.execQuerySafe(stmt, result -> {
                result.next();
                return result.getBoolean(1);
        });
    }

}
