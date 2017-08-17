package services.dbService.entities;

public class AverageBuySell {
    private String instrument_name;
    private long instrument_id;
    private double average_buy;
    private double average_sell;

    public AverageBuySell(String instrument_name, long instrument_id, double average_buy, double average_sell) {
        this.instrument_name = instrument_name;
        this.instrument_id = instrument_id;
        this.average_buy = average_buy;
        this.average_sell = average_sell;
    }

    public long getInstrumentId() {
        return this.instrument_id;
    }

    public double getAverageBuy() {
        return this.average_buy;
    }

    public double getAverageSell() {
        return this.average_sell;
    }

    public String getInstrumentName() {
        return this.instrument_name;
    }
}

