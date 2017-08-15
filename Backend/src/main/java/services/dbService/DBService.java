package services.dbService;


import services.PropertyService;
import services.dbService.dao.DealDAO;
import services.dbService.dao.InstrumentDAO;
import services.dbService.dao.UsersDAO;
import services.dbService.entities.Deal;
import services.dbService.entities.Instrument;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class DBService {

    final Connection connection;

    public Connection getConnection() {
        return connection;
    }

//    Manual test
    public static void main(String[] args) {

        DBService dbService = new DBService(PropertyService.getInstance());
        dbService.printConnectInfo();
        System.out.println(dbService.validateConnection());
        List<Deal> deal = new LinkedList<>();
        try{
            deal = dbService.getAllDeals();
        }
        catch(DBException e){}
        for (Deal d : deal){
            System.out.println(d.getDeal_time());
        }
    }

    public DBService(PropertyService propertyService) {
        this.connection = getMysqlConnection(propertyService);
    }

    public DBService() {
        this.connection = getMysqlConnection(PropertyService.getInstance());
    }

    public boolean validateUser(String login, String password) throws DBException  {
        try {
            return new UsersDAO(connection).validateUser(login, password);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public Deal getDealById(Long deal_id) throws DBException {
        try {
            return new DealDAO(connection).getDealById(deal_id);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public List<Deal> getAllDeals() throws DBException {
        try {
            return new DealDAO(connection).getAllDeals();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public List<Instrument> getAllInstruments() throws DBException {
        try {
            return new InstrumentDAO(connection).getAllInstruments();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public Instrument getInstrumentByID(long instrument_id) throws DBException {
        try {
            return new InstrumentDAO(connection).getInstrumentById(instrument_id);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public List<Deal> getDealsPage(int pageNumber, int pageSize) throws DBException {
        try {
            return new DealDAO(connection).getDealsPage(pageNumber, pageSize);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public List<Deal> getDealsPage(int pageNumber) throws DBException {
        return getDealsPage(pageNumber, 1000);
    }

    public boolean validateConnection() {
        return connection != null;
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getMysqlConnection(PropertyService propertyService) {
        try {
            DriverManager.registerDriver((Driver) Class.forName(propertyService.getProperty("dbDriver")).newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append(propertyService.getProperty("dbPath")).                //port
                    append(propertyService.getProperty("dbName") + "?").          //db name
                    append("user=" + propertyService.getProperty("dbUser") + "&").          //login
                    append("password=" + propertyService.getProperty("dbPwd"));       //password

            System.out.println("MySQL Connection URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("DB fail to connect.");
        }
        return null;
    }

}