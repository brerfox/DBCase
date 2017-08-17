package services.dbService.entities;

public class EndingPosition {
	
    private String counterparty_name;
    private String instrument_name;
    private long ending_position;

    public EndingPosition(String counterparty_name, String instrument_name, long ending_position) {
        this.counterparty_name = counterparty_name;
        this.instrument_name = instrument_name;
        this.ending_position = ending_position;
    }

    public String getCounterpartyName() {
        return this.counterparty_name;
    }

    public String getInstrumentName() {
        return this.instrument_name;
    }

    public long getEndingPosition() {
        return this.ending_position;
    }
}

