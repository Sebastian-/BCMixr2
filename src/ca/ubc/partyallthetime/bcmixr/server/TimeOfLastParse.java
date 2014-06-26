package ca.ubc.partyallthetime.bcmixr.server;

import java.io.Serializable;

import javax.persistence.Id;

public class TimeOfLastParse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
    String id; // Can be Long, long, or String
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private long timeOfLastParse;
	
	
	public long getTimeOfLastParse() {
		return timeOfLastParse;
	}
	public void setTimeOfLastParse(long timeOfLastParse) {
		this.timeOfLastParse = timeOfLastParse;
	}

}
