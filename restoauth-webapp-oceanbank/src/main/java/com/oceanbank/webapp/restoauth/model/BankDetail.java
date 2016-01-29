/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The Class BankDetail.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Entity
@Table(name="TNPARG")
public class BankDetail implements java.io.Serializable{
	
	/**
	 * Instantiates a new {@link BankDetail}.
	 */
	public BankDetail(){}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The fld_id. */
	@Id
	@Column(name="TNGTIN")
	private String fld_id;
	
	/** The fld_12b. */
	@Column(name="TNGSTS3")
	private String fld_12b;
	
	/** The fld_12c. */
	@Column(name="TNGSTS4")
	private String fld_12c;
	
	/** The fld_13b. */
	@Column(name="TNGIIN")
	private String fld_13b;
	
	/** The fld_13c. */
	@Column(name="TNGSTT")
	private String fld_13c;
	
	/** The fld_13d. */
	@Column(name="TNGFTIN")
	private String fld_13d;
	
	/** The fld_14e. */
	@Column(name="TNGNA1")
	private String fld_14e;
	
	/** The fld_14f. */
	@Column(name="TNPRIEIN")
	private String fld_14f;
	
	/** The fld_13a_2. */
	@Column(name="TNGPHO")
	private String fld_13a_2;
	
	/** The fld_14ef_1. */
	@Column(name="TNGADR")
	private String fld_14ef_1;
	
	/** The fld_14ef_2. */
	@Column(name="TNGCTY")
	private String fld_14ef_2;
	
	/** The fld_14ef_3. */
	@Column(name="TNGZIP")
	private String fld_14ef_3;
	
	
	/**
	 * Gets the fld_12b.
	 *
	 * @return the fld_12b
	 */
	public String getFld_12b() {
		return fld_12b;
	}
	
	/**
	 * Sets the fld_12b.
	 *
	 * @param fld_12b the new fld_12b
	 */
	public void setFld_12b(String fld_12b) {
		this.fld_12b = fld_12b;
	}
	
	/**
	 * Gets the fld_12c.
	 *
	 * @return the fld_12c
	 */
	public String getFld_12c() {
		return fld_12c;
	}
	
	/**
	 * Sets the fld_12c.
	 *
	 * @param fld_12c the new fld_12c
	 */
	public void setFld_12c(String fld_12c) {
		this.fld_12c = fld_12c;
	}
	
	/**
	 * Gets the fld_13b.
	 *
	 * @return the fld_13b
	 */
	public String getFld_13b() {
		return fld_13b;
	}
	
	/**
	 * Sets the fld_13b.
	 *
	 * @param fld_13b the new fld_13b
	 */
	public void setFld_13b(String fld_13b) {
		this.fld_13b = fld_13b;
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
	 * Gets the fld_14e.
	 *
	 * @return the fld_14e
	 */
	public String getFld_14e() {
		return fld_14e;
	}
	
	/**
	 * Sets the fld_14e.
	 *
	 * @param fld_14e the new fld_14e
	 */
	public void setFld_14e(String fld_14e) {
		this.fld_14e = fld_14e;
	}
	
	/**
	 * Gets the fld_14f.
	 *
	 * @return the fld_14f
	 */
	public String getFld_14f() {
		return fld_14f;
	}
	
	/**
	 * Sets the fld_14f.
	 *
	 * @param fld_14f the new fld_14f
	 */
	public void setFld_14f(String fld_14f) {
		this.fld_14f = fld_14f;
	}
	
	/**
	 * Gets the fld_13a_2.
	 *
	 * @return the fld_13a_2
	 */
	public String getFld_13a_2() {
		return fld_13a_2;
	}
	
	/**
	 * Sets the fld_13a_2.
	 *
	 * @param fld_13a_2 the new fld_13a_2
	 */
	public void setFld_13a_2(String fld_13a_2) {
		this.fld_13a_2 = fld_13a_2;
	}
	
	/**
	 * Gets the fld_14ef_1.
	 *
	 * @return the fld_14ef_1
	 */
	public String getFld_14ef_1() {
		return fld_14ef_1;
	}
	
	/**
	 * Sets the fld_14ef_1.
	 *
	 * @param fld_14ef_1 the new fld_14ef_1
	 */
	public void setFld_14ef_1(String fld_14ef_1) {
		this.fld_14ef_1 = fld_14ef_1;
	}
	
	/**
	 * Gets the fld_14ef_2.
	 *
	 * @return the fld_14ef_2
	 */
	public String getFld_14ef_2() {
		return fld_14ef_2;
	}
	
	/**
	 * Sets the fld_14ef_2.
	 *
	 * @param fld_14ef_2 the new fld_14ef_2
	 */
	public void setFld_14ef_2(String fld_14ef_2) {
		this.fld_14ef_2 = fld_14ef_2;
	}
	
	/**
	 * Gets the fld_14ef_3.
	 *
	 * @return the fld_14ef_3
	 */
	public String getFld_14ef_3() {
		return fld_14ef_3;
	}
	
	/**
	 * Sets the fld_14ef_3.
	 *
	 * @param fld_14ef_3 the new fld_14ef_3
	 */
	public void setFld_14ef_3(String fld_14ef_3) {
		this.fld_14ef_3 = fld_14ef_3;
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
	
	
	
	
}
