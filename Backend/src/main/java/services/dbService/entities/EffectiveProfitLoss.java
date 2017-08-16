package services.dbService.entities;

public class EffectiveProfitLoss {

    private String counterparty_name;
    private String instrument_name;
    private double effective_profit;

    public EffectiveProfitLoss(String counterparty_name, String instrument_name, double effective_profit) {
        this.counterparty_name = counterparty_name;
        this.instrument_name = instrument_name;
        this.effective_profit = effective_profit;
    }

    public String getCounterpartyName() {
        return this.counterparty_name;
    }

    public String getInstrumentName() {
        return this.instrument_name;
    }

    public double getEffective_profit() {
        return this.effective_profit;
    }
}
