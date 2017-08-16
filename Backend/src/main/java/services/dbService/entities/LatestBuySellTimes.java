package services.dbService.entities;

public class LatestBuySellTimes {

    private long deal_instrument_id;
    private String end_buy_time;
    private String end_sell_time;

    public LatestBuySellTimes(long deal_instrument_id, String end_buy_time, String end_sell_time){
        this.deal_instrument_id = deal_instrument_id;
        this.end_buy_time = end_buy_time;
        this.end_sell_time = end_sell_time;
    }

    public long getDeal_instrument_id(){
        return this.deal_instrument_id;
    }

    public String getEnd_buy_time(){
        return this.end_buy_time;
    }

    public String getEnd_sell_time(){
        return this.end_sell_time;
    }

}
