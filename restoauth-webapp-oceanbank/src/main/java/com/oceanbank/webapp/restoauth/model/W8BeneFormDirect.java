package com.oceanbank.webapp.restoauth.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name="IBTN007F1")
@NamedQueries({
	  @NamedQuery(name="W8BeneFormDirect.findByW8BeneFormDirectPK", query = "from W8BeneFormDirect w where pkId.cif = :cif and pkId.name = :name"),
	  @NamedQuery(name="W8BeneFormDirect.findByCifLike", query = "from W8BeneFormDirect where pkId.cif LIKE :search"),
	  @NamedQuery(name="W8BeneFormDirect.findByCif", query = "from W8BeneFormDirect w where pkId.cif = :cif")
	})
public class W8BeneFormDirect  implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private W8BeneFormDirectPK pkId;
    
	@Column(name="I1NA2")
    private String physicalAddress;
    
	@Column(name="I1NA3")
	private String physicalCity;
    
	@Column(name="I1NA4")
	private String physicalCountryInc;
    
	@Column(name="I1FORC")
	private String physicalCountry;

	@Column(name="I1NA2_A")
    private String altAddress;
    
	@Column(name="I1NA3_A")
	private String altCity;
    
	@Column(name="I1NA4_A")
	private String altCountryInc;
	
	@Column(name="I1FORC_A")
    private String altCountry;

	@Column(name="I1OFFR")
    private String officer;
    
    @Column(name="I1BRNN")
    private String branch;

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	public String getPhysicalCity() {
		return physicalCity;
	}

	public void setPhysicalCity(String physicalCity) {
		this.physicalCity = physicalCity;
	}

	public String getPhysicalCountryInc() {
		return physicalCountryInc;
	}

	public void setPhysicalCountryInc(String physicalCountryInc) {
		this.physicalCountryInc = physicalCountryInc;
	}

	public String getPhysicalCountry() {
		return physicalCountry;
	}

	public void setPhysicalCountry(String physicalCountry) {
		this.physicalCountry = physicalCountry;
	}

	public String getAltAddress() {
		return altAddress;
	}

	public void setAltAddress(String altAddress) {
		this.altAddress = altAddress;
	}

	public String getAltCity() {
		return altCity;
	}

	public void setAltCity(String altCity) {
		this.altCity = altCity;
	}

	public String getAltCountryInc() {
		return altCountryInc;
	}

	public void setAltCountryInc(String altCountryInc) {
		this.altCountryInc = altCountryInc;
	}

	public String getAltCountry() {
		return altCountry;
	}

	public void setAltCountry(String altCountry) {
		this.altCountry = altCountry;
	}

	public String getOfficer() {
		return officer;
	}

	public void setOfficer(String officer) {
		this.officer = officer;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public W8BeneFormDirectPK getPkId() {
		return pkId;
	}

	public void setPkId(W8BeneFormDirectPK pkId) {
		this.pkId = pkId;
	}

}
