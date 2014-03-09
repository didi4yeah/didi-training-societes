package com.recherchetaff.db.entities;

public class Contact {
	
	private String name;
	private String job;	
	private int societe_id;
	
	public Contact(String name, String job, int societe_id) {
		super();
		this.name = name;
		this.job = job;
		this.societe_id = societe_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getSociete_id() {
		return societe_id;
	}

	public void setSociete_id(int societe_id) {
		this.societe_id = societe_id;
	}	
}
