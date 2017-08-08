package services.dbService.dao;

import services.PropertyService;
import services.dbService.DBService;
import services.dbService.entities.Deal;
import services.dbService.executor.Executor;
import services.dbService.executor.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kinmanz on 06.08.17.
 */
public class DealDAO {

    private Executor executor;

    public DealDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    //    manual testing
    public static void main(String[] args) throws Exception {

        PropertyService propertyService = PropertyService.getInstance();
        DBService dbService = new DBService(propertyService);
        DealDAO dealDAO = new DealDAO(dbService.getConnection());

        System.out.println(dealDAO.getDealById(20001L));

        dealDAO.getAllDeals().subList(0, 10)
                .forEach(item -> System.out.println(item));


    }

    public List<Deal> getAllDeals() throws SQLException {
        String query = "SELECT * FROM deal";
        return getDealList(query);
    }

    /**Each page has pageSize records.
     * <p>getDealsPage(1) - will return records from 1 to 100-th </>
     * */
    public List<Deal> getDealsPage(int pageNumber, int pageSize) throws SQLException {

        if (pageNumber <= 0) return new LinkedList<>();

        String query = String.format("SELECT * FROM deal LIMIT %d, %d",
                pageSize * (pageNumber - 1), pageSize);

        return getDealList(query);
    }

    private List<Deal> getDealList(String query) throws SQLException {
        return executor.execQuery(query, result -> {
            List<Deal> dealList = new LinkedList<>();
            while (result.next()) {
                dealList.add(new Deal(result.getLong(1), result.getString(2),
                        result.getLong(3), result.getLong(4), result.getString(5),
                        result.getString(6), result.getLong(7)));
            }
            return dealList;

        });
    }

    public Deal getDealById(Long deal_id) throws SQLException {

        String query = "SELECT * FROM deal WHERE deal_id = " + deal_id;
        return executor.execQuery(query, result -> {
            if (result.next()) {
                return new Deal(result.getLong(1), result.getString(2),
                        result.getLong(3), result.getLong(4), result.getString(5),
                        result.getString(6), result.getLong(7));
            } else {
                return null;
            }

        });
    }

}