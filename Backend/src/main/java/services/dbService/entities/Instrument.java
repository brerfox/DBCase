package services.dbService.entities;

public class Instrument {

    private long instrument_id;
    private String instrument_name;

    public Instrument(long instrument_id, String instrument_name) {
        this.instrument_id = instrument_id;
        this.instrument_name = instrument_name;
    }

    public long getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(long instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getInstrument_name() {
        return instrument_name;
    }

    public void setInstrument_name(String instrument_name) {
        this.instrument_name = instrument_name;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "instrument_id=" + instrument_id +
                ", instrument_name='" + instrument_name + '\'' +
                '}';
    }
}
