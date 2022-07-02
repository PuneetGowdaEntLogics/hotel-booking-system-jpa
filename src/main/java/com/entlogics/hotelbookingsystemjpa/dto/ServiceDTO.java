package com.entlogics.hotelbookingsystemjpa.dto;

// This class is a DTO to transfer list of service of a hotel
public class ServiceDTO {

	// Define the fields
	private int service_id;
	
	private int service_no;
	
	private String service_name;
	
	private float service_price;

	// Generate getters and setters
	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	public int getService_no() {
		return service_no;
	}

	public void setService_no(int service_no) {
		this.service_no = service_no;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public float getService_price() {
		return service_price;
	}

	public void setService_price(float service_price) {
		this.service_price = service_price;
	}

	// Generate toString method
	@Override
	public String toString() {
		return "ServiceDTO [service_id=" + service_id + ", service_no=" + service_no + ", service_name=" + service_name
				+ ", service_price=" + service_price + "]";
	}
	
	
	
	
}
