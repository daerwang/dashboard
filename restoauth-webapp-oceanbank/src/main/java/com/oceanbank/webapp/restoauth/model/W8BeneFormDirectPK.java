package com.oceanbank.webapp.restoauth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class W8BeneFormDirectPK implements Serializable{


	public W8BeneFormDirectPK(){}
	
	public W8BeneFormDirectPK(String cif, String name){
		this.cif = cif;
		this.name = name;
	}
	

	private static final long serialVersionUID = 1L;
	
	@Column(name="I1CIF")
	private String cif;

	@Column(name="I1NAME")
	private String name;

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
