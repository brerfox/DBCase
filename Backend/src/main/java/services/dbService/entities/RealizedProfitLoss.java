package services.dbService.entities;

public class RealizedProfitLoss {


    private String counterparty_name;
    private String instrument_name;
    private double realized_profit;

    public RealizedProfitLoss(String counterparty_name, String instrument_name, double realized_profit) {
        this.counterparty_name = counterparty_name;
        this.instrument_name = instrument_name;
        this.realized_profit = realized_profit;
    }

    public String getCounterpartyName() {
        return this.counterparty_name;
    }

    public String getInstrumentName() {
        return this.instrument_name;
    }

    public double getRealized_profit() {
        return this.realized_profit;
    }
}
