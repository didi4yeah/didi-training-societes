package com.recherchetaff.db.entities;

public class Societe {
	
	public enum type_societe {   
		SSII, CLIENT_FINAL
	}
	
	private int id;
	private String name;
	private type_societe type;  
	private String adresse;
	private String url_logo;
	private String url_site;	
	
	public Societe() {
	}
		
	public Societe(String name, type_societe type, String adresse, 
			String url_logo, String url_site) {
		super();		
		this.name = name;
		this.type = type;
		this.adresse = adresse;
		this.url_site = url_site;
		this.url_logo = url_logo;
	}
	
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public type_societe getType() {
		return type;
	}

	public void setType(type_societe type) {
		this.type = type;
	}

	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public String getUrl_site() {
		return url_site;
	}

	public void setUrl_site(String url_site) {
		this.url_site = url_site;
	}

	public String getUrl_logo() {
		return url_logo;
	}

	public void setUrl_logo(String urlm_logo) {
		this.url_logo = urlm_logo;
	}
	
	
}
