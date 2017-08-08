package services.dbService.dao;

import services.PropertyService;
import services.dbService.DBService;
import services.dbService.entities.Deal;
import services.dbService.entities.Instrument;
import services.dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class InstrumentDAO {

    public static void main(String[] args) throws SQLException {

        PropertyService propertyService = PropertyService.getInstance();
        DBService dbService = new DBService(propertyService);
        InstrumentDAO instrumentDAO = new InstrumentDAO(dbService.getConnection());

        System.out.println(instrumentDAO.getInstrumentById(1001L));

        instrumentDAO.getAllInstruments().subList(0, 10)
                .forEach(item -> System.out.println(item));

    }

    public InstrumentDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    private Executor executor;

    static String queryGetAllInstrument = "SELECT instrument_id, instrument_name FROM instrument";

    public List<Instrument> getAllInstruments() throws SQLException {
        return getInstrumentList(queryGetAllInstrument);
    }

    private List<Instrument> getInstrumentList(String query) throws SQLException {
        return executor.execQuery(query, result -> {
            List<Instrument> instrumentList = new LinkedList<>();
            while (result.next()) {
                instrumentList.add(new Instrument(result.getLong(1), result.getString(2)));
            }
            return instrumentList;

        });
    }

    public Instrument getInstrumentById(Long deal_id) throws SQLException {

        String query = queryGetAllInstrument + " WHERE instrument_id = " + deal_id;
        return executor.execQuery(query, result -> {
            if (result.next()) {
                return new Instrument(result.getLong(1), result.getString(2));
            } else {
                return null;
            }

        });
    }
}
