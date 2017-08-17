package services.dbService.entities;

public class Overview {
    private String instrument_name;
    private String counterparty_name;
    private String TIMEONLY;
    private double total_amount;
    private long total_quantity;

    public Overview(String instrument_name, String counterparty_name, String TIMEONLY, double total_amount, long total_quantity){
        this.instrument_name = instrument_name;
        this.counterparty_name = counterparty_name;
        this.TIMEONLY = TIMEONLY;
        this.total_amount = total_amount;
        this.total_quantity =  total_quantity;
    }

    public String getInstrument_name(){
        return this.instrument_name;
    }

    public String getCounterparty_name(){
        return this.counterparty_name;
    }

    public String getTIMEONLY(){
        return this.TIMEONLY;
    }

    public double getTotal_amount(){
        return this.total_amount;
    }

    public long getTotal_quantity(){
        return this.total_quantity;
    }
}