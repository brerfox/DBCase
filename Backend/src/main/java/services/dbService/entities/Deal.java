package services.dbService.entities;

public class Deal {

//    deal_id,deal_time,deal_type,deal_amount,deal_quantity,
// deal_instrument_id,counterparty_name,instrument_name

    private long deal_id;
    private String deal_time;
    private String deal_type;
    private String deal_amount;
    private long deal_quantity;
    private long deal_instrument_id;

//    Foreign key
    private String counterparty_name;
    private String instrument_name;

    public Deal(long deal_id, String deal_time, String deal_type, String deal_amount, long deal_quantity, long deal_instrument_id, String counterparty_name, String instrument_name) {
        this.deal_id = deal_id;
        this.deal_time = deal_time;
        this.deal_type = deal_type;
        this.deal_amount = deal_amount;
        this.deal_quantity = deal_quantity;
        this.deal_instrument_id = deal_instrument_id;
        this.counterparty_name = counterparty_name;
        this.instrument_name = instrument_name;
    }

    public String getCounterparty_name() {
        return counterparty_name;
    }

    public void setCounterparty_name(String counterparty_name) {
        this.counterparty_name = counterparty_name;
    }

    public String getInstrument_name() {
        return instrument_name;
    }

    public void setInstrument_name(String instrument_name) {
        this.instrument_name = instrument_name;
    }

    public Deal() {

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
                ", deal_instrument_id=" + deal_instrument_id +
                ", deal_type='" + deal_type + '\'' +
                ", deal_amount='" + deal_amount + '\'' +
                ", deal_quantity=" + deal_quantity +
                ", counterparty_name='" + counterparty_name + '\'' +
                ", instrument_name='" + instrument_name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deal deal = (Deal) o;

        if (deal_id != deal.deal_id) return false;
        if (deal_quantity != deal.deal_quantity) return false;
        if (deal_instrument_id != deal.deal_instrument_id) return false;
        if (deal_time != null ? !deal_time.equals(deal.deal_time) : deal.deal_time != null) return false;
        if (deal_type != null ? !deal_type.equals(deal.deal_type) : deal.deal_type != null) return false;
        if (deal_amount != null ? !deal_amount.equals(deal.deal_amount) : deal.deal_amount != null) return false;
        if (counterparty_name != null ? !counterparty_name.equals(deal.counterparty_name) : deal.counterparty_name != null)
            return false;
        return instrument_name != null ? instrument_name.equals(deal.instrument_name) : deal.instrument_name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (deal_id ^ (deal_id >>> 32));
        result = 31 * result + (deal_time != null ? deal_time.hashCode() : 0);
        result = 31 * result + (deal_type != null ? deal_type.hashCode() : 0);
        result = 31 * result + (deal_amount != null ? deal_amount.hashCode() : 0);
        result = 31 * result + (int) (deal_quantity ^ (deal_quantity >>> 32));
        result = 31 * result + (int) (deal_instrument_id ^ (deal_instrument_id >>> 32));
        result = 31 * result + (counterparty_name != null ? counterparty_name.hashCode() : 0);
        result = 31 * result + (instrument_name != null ? instrument_name.hashCode() : 0);
        return result;
    }
}
