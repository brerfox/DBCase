package services.dbService.executor;

import java.sql.*;


public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(String update) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(update);
        stmt.close();
    }

    public <T> T execQuery(String query,
                           ResultHandler<T> handler)
            throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        T value = handler.handle(result);
        result.close();
        stmt.close();

        return value;
    }

//    Anti SQL injection version
    public <T> T execQuerySafe(PreparedStatement stmt,
                           ResultHandler<T> handler)
            throws SQLException {
        ResultSet result = stmt.executeQuery();
        T value = handler.handle(result);
        result.close();
        stmt.close();
        return value;
    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException{
        return connection.prepareStatement(query);
    }

}
