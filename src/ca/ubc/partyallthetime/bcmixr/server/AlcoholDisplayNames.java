package ca.ubc.partyallthetime.bcmixr.server;

import java.io.Serializable;
import java.util.ArrayList;


import javax.persistence.Id;

public class AlcoholDisplayNames implements Serializable {
	
	private static final long serialVersionUID = -3385451170364578223L;
	
	
	@Id
	long id;

	ArrayList<String> displayNames = new ArrayList<String>();
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public void addDisplayName(String name) {
		displayNames.add(name);
	}
	
	public ArrayList<String> getDisplayNames() {
		return displayNames;
	}

}
