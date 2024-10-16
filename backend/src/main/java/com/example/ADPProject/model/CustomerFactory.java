package com.example.ADPProject.model;

import org.json.JSONObject;

public class CustomerFactory {
	
	public static Customer getCustomer(String json_string){
		JSONObject jobj = new org.json.JSONObject(json_string); 
		Long id = jobj.getLong("id");  // Changed to Long
		String name = jobj.getString("name"); 
		String email = jobj.getString("email"); 
		String password = jobj.getString("password"); 
		
		Customer cust = new Customer();
		cust.setName(name);
		cust.setId(id);  // Ensure this is Long
		cust.setEmail(email);
		cust.setPassword(password);
		return cust;
	}
	
	public static String getCustomerAsJSONString(Customer customer) {
		JSONObject jo = new JSONObject(); 
		jo.put("name", customer.getName()); 
		jo.put("email", customer.getEmail());
		jo.put("password", customer.getPassword());
		jo.put("id", customer.getId());
		return jo.toString();
	}
}
