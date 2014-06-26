package ca.ubc.partyallthetime.bcmixr.server;



import javax.persistence.Id;

import java.io.Serializable;



public class Address implements Serializable{



	private static final long serialVersionUID = 1L;



	@Id Long id; // Can be Long, long, or String

	private String name;

	private String address;

	private String city;

	private String postalCode;

	private Float lat, lng;

	private String tlatlng;



	



	public Address (){

		this.name = "init";

		this.address = "init";

		this.city = "init";

		this.postalCode = "init";

	}



	



	public void setAll(String name, String address, String city, String postalCode){

		this.name = name;

		this.address = address;

		this.city = city;

		this.postalCode = postalCode;

	}



	public String getName() {

		return name;

	}



	public void setName(String name) {

		this.name = name;

	}



	public String getFullAddress() {

		return address + ", " + city + ", " + postalCode;

	}



	public void setLngLat(Float latti, Float longi){

		this.lat = latti;

		this.lng = longi;

	}



	



	public void setTlatlng(String latlng){

		this.tlatlng = latlng;

	}



	public String getTlatlng(){

		return this.tlatlng;

	}



	public Float getLattitude() {

		return this.lat;

	}



	public Float getLongitude() {

		return this.lng;

	}

}

