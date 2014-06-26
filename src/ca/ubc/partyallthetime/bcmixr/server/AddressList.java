package ca.ubc.partyallthetime.bcmixr.server;

import java.io.Serializable;
import java.util.ArrayList;
import ca.ubc.partyallthetime.bcmixr.server.Address;
import javax.persistence.Id;
import javax.persistence.Embedded;
public class AddressList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9110184672152524652L;
	
	@Id 
	String id; // Can be Long, long, or String
	@Embedded private ArrayList<Address> addressList = new 
			ArrayList<Address>(); 
	
	public AddressList(){
		this.addressList = new ArrayList<Address>();
	}
	
	public Address get(int i){
		return this.addressList.get(i);
	}
	
	public void add(Address a){
		this.addressList.add(a);
	}
	
	public int size(){
		return this.addressList.size();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
