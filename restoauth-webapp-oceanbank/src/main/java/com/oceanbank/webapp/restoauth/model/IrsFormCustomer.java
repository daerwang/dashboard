/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


/**
 * The Class IrsFormCustomer.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Entity
@Table(name="CIS7349F1", schema="FALLIB")
@NamedQueries({
	  @NamedQuery(name="IrsFormCustomer.findByCustomerPk",
	              query = "from IrsFormCustomer where irsFormId.fld_14acd_1 = :name and irsFormId.fld_19 = :account and irsFormId.fld_12a = :tin and irsFormId.fld_2 = :gross"),
	  @NamedQuery(name="IrsFormCustomer.findByCustomerByNameLike",
	              query = "from IrsFormCustomer where irsFormId.fld_14acd_1 LIKE :search"), 
	  @NamedQuery(name="IrsFormCustomer.findByDatatableSearch",
	              query = "from IrsFormCustomer where irsFormId.fld_14acd_1 LIKE :search OR irsFormId.fld_19 LIKE :search OR irsFormId.fld_12a LIKE :search OR fld_14acd_5 LIKE :search"),
	  @NamedQuery(name="IrsFormCustomer.findByMailCodeDistinct",
	              query = "select DISTINCT c.mailCode from IrsFormCustomer c ORDER BY c.mailCode asc"),
	  @NamedQuery(name="IrsFormCustomer.findByMailCode",
      query = "from IrsFormCustomer where mailCode IN (:search)")  
	})
public class IrsFormCustomer implements java.io.Serializable{
	
	/**
	 * Instantiates a new {@link IrsFormCustomer}.
	 */
	public IrsFormCustomer(){}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	/** The irs form id. */
	@EmbeddedId
	private IrsFormCustomerPK irsFormId = new IrsFormCustomerPK();
	
	/** The fld_3. */
	@Column(name="TNCHAPT")
	private String fld_3;
	
	/** The mail code. */
	@Column(name="CIMAIL")
	private String mailCode;
	
	/** The fld_15b. */
	@Column(name="TNESTS3")
	private String fld_15b;
	
	/** The fld_15c. */
	@Column(name="TNESTS4")
	private String fld_15c;
	
	/** The fld_16b. */
	@Column(name="TNEGIIN")
	private String fld_16b;
	
	
	/** The fld_to_1. */
	@Column(name="CINA1")
	private String fld_to_1;
	
	/** The fld_to_2. */
	@Column(name="CINA2")
	private String fld_to_2;
	
	/** The fld_to_3. */
	@Column(name="CINA3")
	private String fld_to_3;
	
	/** The fld_to_4. */
	@Column(name="CINA4")
	private String fld_to_4;
	
	/** The fld_to_5. */
	@Column(name="CINA5")
	private String fld_to_5;
	
	/** The fld_to_6. */
	@Column(name="CINA6")
	private String fld_to_6;
	
	/** The fld_to_7. */
	@Column(name="CINA7")
	private String fld_to_7;
	
	

	/** The fld_13d. */
	@Column(name="TNEFTIN")
	private String fld_13d;

	/** The fld_9. */
	@Column(name="TNTXASM")
	private String fld_9;
	
	/** The fld_14acd_4. */
	@Column(name="TNFCSZ")
	private String fld_14acd_4;
	
	/** The fld_14acd_5. */
	@Column(name="TNFORC")
	private String fld_14acd_5;
	
	/** The fld_20. */
	@Column(name="TNRDOB6")
	private String fld_20;
	
	/** The fld_14b. */
	@Column(name="TNFCNT")
	private String fld_14b; 
	
	
	/** The account type. */
	@Column(name="TNACTP")
	private String accountType;

	/** The short name. */
	@Column(name="TNSNME")
	private String shortName;
	

	/** The fld_1. */
	@Column(name="TNFINC")
	private String fld_1;
	
	
	
	/** The fld_3a. */
	@Column(name="TNEXMP")
	private String fld_3a;
	
	/** The fld_3b. */
	@Column(name="TNTAXR")
	private String fld_3b;
	
	/** The fld_4a. */
	@Column(name="TNEXMP4")
	private String fld_4a;
	
	/** The fld_4b. */
	@Column(name="TNTAXR4")
	private String fld_4b;

	/** The fld_5. */
	@Column(name="TNFSTD")
	private String fld_5;
	
	/** The fld_6. */
	@Column(name="TNFNET")
	private String fld_6;
	
	/** The fld_8. */
	@Column(name="TNFWBO")
	private String fld_8;
	
	/** The fld_7. */
	@Column(name="TNFFWH")
	private String fld_7;
	
	/** The fld_11. */
	@Column(name="TNFARE")
	private String fld_11;
	
	/** The fld_13c. */
	@Column(name="TNFQCC")
	private String fld_13c;
	
	/** The fld_16d. */
	@Column(name="TNFQUS")
	private String fld_16d;
	
	/** The fld_13h. */
	@Column(name="TNFRCP")
	private String fld_13h;
	
	/** The fld_13i. */
	@Column(name="TNRSTS4")
	private String fld_13i;
	
	/** The fld_17. */
	@Column(name="TNRGIIN")
	private String fld_17;
	
	/** The fld_18. */
	@Column(name="TNFFTI")
	private String fld_18;
	
	/** The fld_14acd_2. */
	@Column(name="TNFADR")
	private String fld_14acd_2;
	
	/** The fld_14acd_3. */
	@Column(name="TNFAD2")
	private String fld_14acd_3;

	/** The fld_21. */
	@Column(name="TNFPRN")
	private String fld_21;
	
	/** The fld_22. */
	@Column(name="TNFPRT")
	private String fld_22;
	

	
	
	/**
	 * Gets the short name.
	 *
	 * @return the short name
	 */
	public String getShortName() {
		return shortName;
	}
	
	/**
	 * Sets the short name.
	 *
	 * @param shortName the new short name
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/** The fld_24. */
	@Column(name="TNFSWH")
	private String fld_24;
	
	
	
	/**
	 * Gets the fld_1.
	 *
	 * @return the fld_1
	 */
	public String getFld_1() {
		return fld_1;
	}
	
	/**
	 * Gets the fld_3a.
	 *
	 * @return the fld_3a
	 */
	public String getFld_3a() {
		return fld_3a;
	}
	
	/**
	 * Sets the fld_3a.
	 *
	 * @param fld_3a the new fld_3a
	 */
	public void setFld_3a(String fld_3a) {
		this.fld_3a = fld_3a;
	}
	
	/**
	 * Gets the fld_3b.
	 *
	 * @return the fld_3b
	 */
	public String getFld_3b() {
		return fld_3b;
	}
	
	/**
	 * Sets the fld_3b.
	 *
	 * @param fld_3b the new fld_3b
	 */
	public void setFld_3b(String fld_3b) {
		this.fld_3b = fld_3b;
	}
	
	/**
	 * Gets the fld_4a.
	 *
	 * @return the fld_4a
	 */
	public String getFld_4a() {
		return fld_4a;
	}
	
	/**
	 * Sets the fld_4a.
	 *
	 * @param fld_4a the new fld_4a
	 */
	public void setFld_4a(String fld_4a) {
		this.fld_4a = fld_4a;
	}
	
	/**
	 * Gets the fld_4b.
	 *
	 * @return the fld_4b
	 */
	public String getFld_4b() {
		return fld_4b;
	}
	
	/**
	 * Sets the fld_4b.
	 *
	 * @param fld_4b the new fld_4b
	 */
	public void setFld_4b(String fld_4b) {
		this.fld_4b = fld_4b;
	}
	
	/**
	 * Gets the fld_5.
	 *
	 * @return the fld_5
	 */
	public String getFld_5() {
		return fld_5;
	}
	
	/**
	 * Sets the fld_5.
	 *
	 * @param fld_5 the new fld_5
	 */
	public void setFld_5(String fld_5) {
		this.fld_5 = fld_5;
	}
	
	/**
	 * Gets the fld_6.
	 *
	 * @return the fld_6
	 */
	public String getFld_6() {
		return fld_6;
	}
	
	/**
	 * Sets the fld_6.
	 *
	 * @param fld_6 the new fld_6
	 */
	public void setFld_6(String fld_6) {
		this.fld_6 = fld_6;
	}
	
	/**
	 * Gets the fld_8.
	 *
	 * @return the fld_8
	 */
	public String getFld_8() {
		return fld_8;
	}
	
	/**
	 * Sets the fld_8.
	 *
	 * @param fld_8 the new fld_8
	 */
	public void setFld_8(String fld_8) {
		this.fld_8 = fld_8;
	}
	
	/**
	 * Gets the fld_7.
	 *
	 * @return the fld_7
	 */
	public String getFld_7() {
		return fld_7;
	}
	
	/**
	 * Sets the fld_7.
	 *
	 * @param fld_7 the new fld_7
	 */
	public void setFld_7(String fld_7) {
		this.fld_7 = fld_7;
	}
	
	/**
	 * Gets the fld_11.
	 *
	 * @return the fld_11
	 */
	public String getFld_11() {
		return fld_11;
	}
	
	/**
	 * Sets the fld_11.
	 *
	 * @param fld_11 the new fld_11
	 */
	public void setFld_11(String fld_11) {
		this.fld_11 = fld_11;
	}
	
	/**
	 * Gets the fld_13c.
	 *
	 * @return the fld_13c
	 */
	public String getFld_13c() {
		return fld_13c;
	}
	
	/**
	 * Sets the fld_13c.
	 *
	 * @param fld_13c the new fld_13c
	 */
	public void setFld_13c(String fld_13c) {
		this.fld_13c = fld_13c;
	}
	
	/**
	 * Gets the fld_16d.
	 *
	 * @return the fld_16d
	 */
	public String getFld_16d() {
		return fld_16d;
	}
	
	/**
	 * Sets the fld_16d.
	 *
	 * @param fld_16d the new fld_16d
	 */
	public void setFld_16d(String fld_16d) {
		this.fld_16d = fld_16d;
	}
	
	/**
	 * Gets the fld_13h.
	 *
	 * @return the fld_13h
	 */
	public String getFld_13h() {
		return fld_13h;
	}
	
	/**
	 * Sets the fld_13h.
	 *
	 * @param fld_13h the new fld_13h
	 */
	public void setFld_13h(String fld_13h) {
		this.fld_13h = fld_13h;
	}
	
	/**
	 * Gets the fld_13i.
	 *
	 * @return the fld_13i
	 */
	public String getFld_13i() {
		return fld_13i;
	}
	
	/**
	 * Sets the fld_13i.
	 *
	 * @param fld_13i the new fld_13i
	 */
	public void setFld_13i(String fld_13i) {
		this.fld_13i = fld_13i;
	}
	
	/**
	 * Gets the fld_17.
	 *
	 * @return the fld_17
	 */
	public String getFld_17() {
		return fld_17;
	}
	
	/**
	 * Sets the fld_17.
	 *
	 * @param fld_17 the new fld_17
	 */
	public void setFld_17(String fld_17) {
		this.fld_17 = fld_17;
	}
	
	/**
	 * Gets the fld_18.
	 *
	 * @return the fld_18
	 */
	public String getFld_18() {
		return fld_18;
	}
	
	/**
	 * Sets the fld_18.
	 *
	 * @param fld_18 the new fld_18
	 */
	public void setFld_18(String fld_18) {
		this.fld_18 = fld_18;
	}
	
	/**
	 * Gets the fld_14acd_2.
	 *
	 * @return the fld_14acd_2
	 */
	public String getFld_14acd_2() {
		return fld_14acd_2;
	}
	
	/**
	 * Sets the fld_14acd_2.
	 *
	 * @param fld_14acd_2 the new fld_14acd_2
	 */
	public void setFld_14acd_2(String fld_14acd_2) {
		this.fld_14acd_2 = fld_14acd_2;
	}
	
	/**
	 * Gets the fld_14acd_3.
	 *
	 * @return the fld_14acd_3
	 */
	public String getFld_14acd_3() {
		return fld_14acd_3;
	}
	
	/**
	 * Sets the fld_14acd_3.
	 *
	 * @param fld_14acd_3 the new fld_14acd_3
	 */
	public void setFld_14acd_3(String fld_14acd_3) {
		this.fld_14acd_3 = fld_14acd_3;
	}
	
	/**
	 * Gets the fld_21.
	 *
	 * @return the fld_21
	 */
	public String getFld_21() {
		return fld_21;
	}
	
	/**
	 * Sets the fld_21.
	 *
	 * @param fld_21 the new fld_21
	 */
	public void setFld_21(String fld_21) {
		this.fld_21 = fld_21;
	}
	
	/**
	 * Gets the fld_22.
	 *
	 * @return the fld_22
	 */
	public String getFld_22() {
		return fld_22;
	}
	
	/**
	 * Sets the fld_22.
	 *
	 * @param fld_22 the new fld_22
	 */
	public void setFld_22(String fld_22) {
		this.fld_22 = fld_22;
	}
	
	/**
	 * Gets the fld_24.
	 *
	 * @return the fld_24
	 */
	public String getFld_24() {
		return fld_24;
	}
	
	/**
	 * Sets the fld_24.
	 *
	 * @param fld_24 the new fld_24
	 */
	public void setFld_24(String fld_24) {
		this.fld_24 = fld_24;
	}
	
	/**
	 * Sets the fld_1.
	 *
	 * @param fld_1 the new fld_1
	 */
	public void setFld_1(String fld_1) {
		this.fld_1 = fld_1;
	}
	
	/**
	 * Gets the irs form id.
	 *
	 * @return the irs form id
	 */
	public IrsFormCustomerPK getIrsFormId() {
		return irsFormId;
	}
	
	/**
	 * Sets the irs form id.
	 *
	 * @param irsFormId the new irs form id
	 */
	public void setIrsFormId(IrsFormCustomerPK irsFormId) {
		this.irsFormId = irsFormId;
	}
	
	/**
	 * Gets the account type.
	 *
	 * @return the account type
	 */
	public String getAccountType() {
		return accountType;
	}
	
	/**
	 * Sets the account type.
	 *
	 * @param accountType the new account type
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	/**
	 * Gets the fld_13d.
	 *
	 * @return the fld_13d
	 */
	public String getFld_13d() {
		return fld_13d;
	}
	
	/**
	 * Sets the fld_13d.
	 *
	 * @param fld_13d the new fld_13d
	 */
	public void setFld_13d(String fld_13d) {
		this.fld_13d = fld_13d;
	}

	/**
	 * Gets the fld_9.
	 *
	 * @return the fld_9
	 */
	public String getFld_9() {
		return fld_9;
	}
	
	/**
	 * Sets the fld_9.
	 *
	 * @param fld_9 the new fld_9
	 */
	public void setFld_9(String fld_9) {
		this.fld_9 = fld_9;
	}
	
	/**
	 * Gets the fld_14b.
	 *
	 * @return the fld_14b
	 */
	public String getFld_14b() {
		return fld_14b;
	}
	
	/**
	 * Sets the fld_14b.
	 *
	 * @param fld_14b the new fld_14b
	 */
	public void setFld_14b(String fld_14b) {
		this.fld_14b = fld_14b;
	}
	
	/**
	 * Gets the fld_14acd_4.
	 *
	 * @return the fld_14acd_4
	 */
	public String getFld_14acd_4() {
		return fld_14acd_4;
	}
	
	/**
	 * Sets the fld_14acd_4.
	 *
	 * @param fld_14acd_4 the new fld_14acd_4
	 */
	public void setFld_14acd_4(String fld_14acd_4) {
		this.fld_14acd_4 = fld_14acd_4;
	}
	
	/**
	 * Gets the fld_14acd_5.
	 *
	 * @return the fld_14acd_5
	 */
	public String getFld_14acd_5() {
		return fld_14acd_5;
	}
	
	/**
	 * Sets the fld_14acd_5.
	 *
	 * @param fld_14acd_5 the new fld_14acd_5
	 */
	public void setFld_14acd_5(String fld_14acd_5) {
		this.fld_14acd_5 = fld_14acd_5;
	}
	
	/**
	 * Gets the fld_20.
	 *
	 * @return the fld_20
	 */
	public String getFld_20() {
		return fld_20;
	}
	
	/**
	 * Sets the fld_20.
	 *
	 * @param fld_20 the new fld_20
	 */
	public void setFld_20(String fld_20) {
		this.fld_20 = fld_20;
	}
	
	/**
	 * Gets the fld_to_1.
	 *
	 * @return the fld_to_1
	 */
	public String getFld_to_1() {
		return fld_to_1;
	}
	
	/**
	 * Sets the fld_to_1.
	 *
	 * @param fld_to_1 the new fld_to_1
	 */
	public void setFld_to_1(String fld_to_1) {
		this.fld_to_1 = fld_to_1;
	}
	
	/**
	 * Gets the fld_to_2.
	 *
	 * @return the fld_to_2
	 */
	public String getFld_to_2() {
		return fld_to_2;
	}
	
	/**
	 * Sets the fld_to_2.
	 *
	 * @param fld_to_2 the new fld_to_2
	 */
	public void setFld_to_2(String fld_to_2) {
		this.fld_to_2 = fld_to_2;
	}
	
	/**
	 * Gets the fld_to_3.
	 *
	 * @return the fld_to_3
	 */
	public String getFld_to_3() {
		return fld_to_3;
	}
	
	/**
	 * Sets the fld_to_3.
	 *
	 * @param fld_to_3 the new fld_to_3
	 */
	public void setFld_to_3(String fld_to_3) {
		this.fld_to_3 = fld_to_3;
	}
	
	/**
	 * Gets the fld_to_4.
	 *
	 * @return the fld_to_4
	 */
	public String getFld_to_4() {
		return fld_to_4;
	}
	
	/**
	 * Sets the fld_to_4.
	 *
	 * @param fld_to_4 the new fld_to_4
	 */
	public void setFld_to_4(String fld_to_4) {
		this.fld_to_4 = fld_to_4;
	}
	
	/**
	 * Gets the fld_to_5.
	 *
	 * @return the fld_to_5
	 */
	public String getFld_to_5() {
		return fld_to_5;
	}
	
	/**
	 * Sets the fld_to_5.
	 *
	 * @param fld_to_5 the new fld_to_5
	 */
	public void setFld_to_5(String fld_to_5) {
		this.fld_to_5 = fld_to_5;
	}
	
	/**
	 * Gets the fld_to_6.
	 *
	 * @return the fld_to_6
	 */
	public String getFld_to_6() {
		return fld_to_6;
	}
	
	/**
	 * Sets the fld_to_6.
	 *
	 * @param fld_to_6 the new fld_to_6
	 */
	public void setFld_to_6(String fld_to_6) {
		this.fld_to_6 = fld_to_6;
	}
	
	/**
	 * Gets the fld_to_7.
	 *
	 * @return the fld_to_7
	 */
	public String getFld_to_7() {
		return fld_to_7;
	}
	
	/**
	 * Sets the fld_to_7.
	 *
	 * @param fld_to_7 the new fld_to_7
	 */
	public void setFld_to_7(String fld_to_7) {
		this.fld_to_7 = fld_to_7;
	}
	
	/**
	 * Gets the fld_15b.
	 *
	 * @return the fld_15b
	 */
	public String getFld_15b() {
		return fld_15b;
	}
	
	/**
	 * Sets the fld_15b.
	 *
	 * @param fld_15b the new fld_15b
	 */
	public void setFld_15b(String fld_15b) {
		this.fld_15b = fld_15b;
	}
	
	/**
	 * Gets the fld_15c.
	 *
	 * @return the fld_15c
	 */
	public String getFld_15c() {
		return fld_15c;
	}
	
	/**
	 * Sets the fld_15c.
	 *
	 * @param fld_15c the new fld_15c
	 */
	public void setFld_15c(String fld_15c) {
		this.fld_15c = fld_15c;
	}
	
	/**
	 * Gets the fld_16b.
	 *
	 * @return the fld_16b
	 */
	public String getFld_16b() {
		return fld_16b;
	}
	
	/**
	 * Sets the fld_16b.
	 *
	 * @param fld_16b the new fld_16b
	 */
	public void setFld_16b(String fld_16b) {
		this.fld_16b = fld_16b;
	}
	
	/**
	 * Gets the mail code.
	 *
	 * @return the mail code
	 */
	public String getMailCode() {
		return mailCode;
	}
	
	/**
	 * Sets the mail code.
	 *
	 * @param mailCode the new mail code
	 */
	public void setMailCode(String mailCode) {
		this.mailCode = mailCode;
	}
	
	/**
	 * Gets the fld_3.
	 *
	 * @return the fld_3
	 */
	public String getFld_3() {
		return fld_3;
	}
	
	/**
	 * Sets the fld_3.
	 *
	 * @param fld_3 the new fld_3
	 */
	public void setFld_3(String fld_3) {
		this.fld_3 = fld_3;
	}
}
