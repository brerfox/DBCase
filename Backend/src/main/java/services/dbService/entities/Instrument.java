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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instrument that = (Instrument) o;

        if (instrument_id != that.instrument_id) return false;
        return instrument_name != null ? instrument_name.equals(that.instrument_name) : that.instrument_name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (instrument_id ^ (instrument_id >>> 32));
        result = 31 * result + (instrument_name != null ? instrument_name.hashCode() : 0);
        return result;
    }
}
