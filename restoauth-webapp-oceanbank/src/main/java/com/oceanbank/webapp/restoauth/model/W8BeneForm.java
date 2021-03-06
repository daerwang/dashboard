package com.oceanbank.webapp.restoauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name="w8beneform")
@NamedQueries({
	  @NamedQuery(name="W8BeneForm.findByDatatableSearch",
	              query = "from W8BeneForm where cif LIKE :search OR name LIKE :search OR physicalCountryInc LIKE :search"),
	  @NamedQuery(name="W8BeneForm.findByEntityIds", query = "from W8BeneForm where id IN (:search)")  
	})
public class W8BeneForm  implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	
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

   public W8BeneForm() {
   }

	
   public W8BeneForm(int id) {
       this.id = id;
   }
   public W8BeneForm(int id, String cif, String name, String physicalCountryInc, String physicalAddress, String physicalCity, String physicalCountry, String altAddress, String altCity, String altCountry, String account, String labelName, String officer, String branch, String altAddressLabel, String altCityLabel, String altCountryLabel) {
      this.id = id;
      this.cif = cif;
      this.name = name;
      this.physicalCountryInc = physicalCountryInc;
      this.physicalAddress = physicalAddress;
      this.physicalCity = physicalCity;
      this.physicalCountry = physicalCountry;
      this.altAddress = altAddress;
      this.altCity = altCity;
      this.altCountry = altCountry;
      this.account = account;
      this.labelName = labelName;
      this.officer = officer;
      this.branch = branch;
      this.altAddressLabel = altAddressLabel;
      this.altCityLabel = altCityLabel;
      this.altCountryLabel = altCountryLabel;
   }
  
   @Id 
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   @Column(name="id", unique=true, nullable=false)
   public int getId() {
       return this.id;
   }
   
   public void setId(int id) {
       this.id = id;
   }

   
   @Column(name="cif")
   public String getCif() {
       return this.cif;
   }
   
   public void setCif(String cif) {
       this.cif = cif;
   }

   
   @Column(name="name")
   public String getName() {
       return this.name;
   }
   
   public void setName(String name) {
       this.name = name;
   }

   
   @Column(name="physicalCountryInc")
   public String getPhysicalCountryInc() {
       return this.physicalCountryInc;
   }
   
   public void setPhysicalCountryInc(String physicalCountryInc) {
       this.physicalCountryInc = physicalCountryInc;
   }

   
   @Column(name="physicalAddress")
   public String getPhysicalAddress() {
       return this.physicalAddress;
   }
   
   public void setPhysicalAddress(String physicalAddress) {
       this.physicalAddress = physicalAddress;
   }

   
   @Column(name="physicalCity")
   public String getPhysicalCity() {
       return this.physicalCity;
   }
   
   public void setPhysicalCity(String physicalCity) {
       this.physicalCity = physicalCity;
   }

   
   @Column(name="physicalCountry")
   public String getPhysicalCountry() {
       return this.physicalCountry;
   }
   
   public void setPhysicalCountry(String physicalCountry) {
       this.physicalCountry = physicalCountry;
   }

   
   @Column(name="altAddress")
   public String getAltAddress() {
       return this.altAddress;
   }
   
   public void setAltAddress(String altAddress) {
       this.altAddress = altAddress;
   }

   
   @Column(name="altCity")
   public String getAltCity() {
       return this.altCity;
   }
   
   public void setAltCity(String altCity) {
       this.altCity = altCity;
   }

   
   @Column(name="altCountry")
   public String getAltCountry() {
       return this.altCountry;
   }
   
   public void setAltCountry(String altCountry) {
       this.altCountry = altCountry;
   }

   
   @Column(name="account")
   public String getAccount() {
       return this.account;
   }
   
   public void setAccount(String account) {
       this.account = account;
   }

   
   @Column(name="labelName")
   public String getLabelName() {
       return this.labelName;
   }
   
   public void setLabelName(String labelName) {
       this.labelName = labelName;
   }

   
   @Column(name="officer")
   public String getOfficer() {
       return this.officer;
   }
   
   public void setOfficer(String officer) {
       this.officer = officer;
   }

   
   @Column(name="branch")
   public String getBranch() {
       return this.branch;
   }
   
   public void setBranch(String branch) {
       this.branch = branch;
   }

   
   @Column(name="altAddressLabel")
   public String getAltAddressLabel() {
       return this.altAddressLabel;
   }
   
   public void setAltAddressLabel(String altAddressLabel) {
       this.altAddressLabel = altAddressLabel;
   }

   
   @Column(name="altCityLabel")
   public String getAltCityLabel() {
       return this.altCityLabel;
   }
   
   public void setAltCityLabel(String altCityLabel) {
       this.altCityLabel = altCityLabel;
   }

   
   @Column(name="altCountryLabel")
   public String getAltCountryLabel() {
       return this.altCountryLabel;
   }
   
   public void setAltCountryLabel(String altCountryLabel) {
       this.altCountryLabel = altCountryLabel;
   }




}
