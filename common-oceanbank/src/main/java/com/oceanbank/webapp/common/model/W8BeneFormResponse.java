package com.oceanbank.webapp.common.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class W8BeneFormResponse {

	private int id;
    private String cif;
    private String name;
    private String physicalCountryInc;
    private String physicalAddress;
    private String physicalCity;
    private String physicalCountry;
    private String altAddress;
    private String altCity;
    private String altCountry;
    private String account;
    private String labelName;
    private String officer;
    private String branch;
    private String altAddressLabel;
    private String altCityLabel;
    private String altCountryLabel;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getPhysicalCountryInc() {
		return physicalCountryInc;
	}
	public void setPhysicalCountryInc(String physicalCountryInc) {
		this.physicalCountryInc = physicalCountryInc;
	}
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
	public String getAltCountry() {
		return altCountry;
	}
	public void setAltCountry(String altCountry) {
		this.altCountry = altCountry;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
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
	public String getAltAddressLabel() {
		return altAddressLabel;
	}
	public void setAltAddressLabel(String altAddressLabel) {
		this.altAddressLabel = altAddressLabel;
	}
	public String getAltCityLabel() {
		return altCityLabel;
	}
	public void setAltCityLabel(String altCityLabel) {
		this.altCityLabel = altCityLabel;
	}
	public String getAltCountryLabel() {
		return altCountryLabel;
	}
	public void setAltCountryLabel(String altCountryLabel) {
		this.altCountryLabel = altCountryLabel;
	}
}
