package services.dbService.entities;

public class Deal {

    private long deal_id;
    private String deal_time;
    private long deal_counterparty_id;
    private long deal_instrument_id;
    private String deal_type;
    private String deal_amount;
    private long deal_quantity;

    public Deal() {

    }

    public Deal(long deal_id, String deal_time, long deal_counterparty_id, long deal_instrument_id, String deal_type, String deal_amount, long deal_quantity) {
        this.deal_id = deal_id;
        this.deal_time = deal_time;
        this.deal_counterparty_id = deal_counterparty_id;
        this.deal_instrument_id = deal_instrument_id;
        this.deal_type = deal_type;
        this.deal_amount = deal_amount;
        this.deal_quantity = deal_quantity;
    }

    public long getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(long deal_id) {
        this.deal_id = deal_id;
    }

    public String getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(String deal_time) {
        this.deal_time = deal_time;
    }

    public long getDeal_counterparty_id() {
        return deal_counterparty_id;
    }

    public void setDeal_counterparty_id(long deal_counterparty_id) {
        this.deal_counterparty_id = deal_counterparty_id;
    }

    public long getDeal_instrument_id() {
        return deal_instrument_id;
    }

    public void setDeal_instrument_id(long deal_instrument_id) {
        this.deal_instrument_id = deal_instrument_id;
    }

    public String getDeal_type() {
        return deal_type;
    }

    public void setDeal_type(String deal_type) {
        this.deal_type = deal_type;
    }

    public String getDeal_amount() {
        return deal_amount;
    }

    public void setDeal_amount(String deal_amount) {
        this.deal_amount = deal_amount;
    }

    public long getDeal_quantity() {
        return deal_quantity;
    }

    public void setDeal_quantity(long deal_quantity) {
        this.deal_quantity = deal_quantity;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "deal_id=" + deal_id +
                ", deal_time='" + deal_time + '\'' +
                ", deal_counterparty_id=" + deal_counterparty_id +
                ", deal_instrument_id=" + deal_instrument_id +
                ", deal_type='" + deal_type + '\'' +
                ", deal_amount='" + deal_amount + '\'' +
                ", deal_quantity=" + deal_quantity +
                '}';
    }
}
