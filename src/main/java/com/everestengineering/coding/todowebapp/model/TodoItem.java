/**
 * 
 */
package com.everestengineering.coding.todowebapp.model;

import com.google.cloud.Timestamp;


/**
 * @author Srinivas.Pakala
 *
 */


public class TodoItem {

	private String id;
	private String text;
	private Boolean completed;
	private Timestamp createdOn;
	
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

	public TodoItem(){};

	public TodoItem(String id, String text, boolean completed) {
		this.id= id;
		this.text = text;
		this.completed = completed;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
