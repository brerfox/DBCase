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

        System.out.println();
        System.out.println(dealDAO.getDealsPage(1, 3));


    }

    static String queryGetAllDeals =
            "SELECT deal_id, deal_time, deal_type, deal_amount, deal_quantity, deal_instrument_id, counterparty_name,instrument_name\n" +
            "FROM deal\n" +
            "JOIN counterparty c ON deal.deal_counterparty_id = c.counterparty_id\n" +
            "JOIN instrument i ON deal.deal_instrument_id = i.instrument_id";
    public List<Deal> getAllDeals() throws SQLException {
        return getDealList(queryGetAllDeals);
    }

    /**Each page has pageSize records.
     * <p>getDealsPage(1) - will return records from 1 to 100-th </>
     * */
    public List<Deal> getDealsPage(int pageNumber, int pageSize) throws SQLException {

        if (pageNumber <= 0) return new LinkedList<>();

        String query = String.format(queryGetAllDeals + " LIMIT %d, %d",
                pageSize * (pageNumber - 1), pageSize);

        return getDealList(query);
    }

    private List<Deal> getDealList(String query) throws SQLException {
        return executor.execQuery(query, result -> {
            List<Deal> dealList = new LinkedList<>();
            while (result.next()) {
                dealList.add(new Deal(result.getLong(1), result.getString(2), result.getString(3),
                        result.getString(4), result.getLong(5), result.getLong(6), result.getString(7),
                        result.getString(8)));
            }
            return dealList;

        });
    }

    public Deal getDealById(Long deal_id) throws SQLException {

        String query = queryGetAllDeals + " WHERE deal_id = " + deal_id;
        return executor.execQuery(query, result -> {
            if (result.next()) {
                return new Deal(result.getLong(1), result.getString(2), result.getString(3),
                        result.getString(4), result.getLong(5), result.getLong(6), result.getString(7),
                        result.getString(8));
            } else {
                return null;
            }

        });
    }

}