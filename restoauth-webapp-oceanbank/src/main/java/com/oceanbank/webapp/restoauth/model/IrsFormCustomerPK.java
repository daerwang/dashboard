/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The Class IrsFormCustomerPK.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Embeddable
public class IrsFormCustomerPK implements Serializable{

	
	/**
	 * Instantiates a new {@link IrsFormCustomerPK}.
	 */
	public IrsFormCustomerPK(){}
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The fld_14acd_1. */
	@Column(name="TNFNM1")
	private String fld_14acd_1;

	/** The fld_19. */
	@Column(name="TNACCT")
	private String fld_19;
	
	/** The fld_12a. */
	@Column(name="TNFEIN")
	private String fld_12a;
	
	/** The fld_2. */
	@Column(name="TNFGRS")
	private String fld_2;
	
	
	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fld_14acd_1 == null) ? 0 : fld_14acd_1.hashCode());
        result = prime * result + ((fld_19 == null) ? 0 : fld_19.hashCode());
        result = prime * result + ((fld_12a == null) ? 0 : fld_12a.hashCode());
        return result;
    }
 
    /**
     * Equals.
     *
     * @param obj the obj
     * @return true, if successful
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof IrsFormCustomerPK)) {
            return false;
        }
        final IrsFormCustomerPK other = (IrsFormCustomerPK) obj;
        if (fld_14acd_1 == null) {
            if (other.fld_14acd_1 != null) {
                return false;
            }
        } else if (!fld_14acd_1.equals(other.fld_14acd_1)) {
            return false;
        }
        if (fld_19 == null) {
            if (other.fld_19 != null) {
                return false;
            }
        } else if (!fld_19.equals(other.fld_19)) {
            return false;
        }
        if (fld_12a == null) {
            if (other.fld_12a != null) {
                return false;
            }
        } else if (!fld_12a.equals(other.fld_12a)) {
            return false;
        }
        return true;
    }

	/**
	 * Gets the fld_14acd_1.
	 *
	 * @return the fld_14acd_1
	 */
	public String getFld_14acd_1() {
		return fld_14acd_1;
	}

	/**
	 * Sets the fld_14acd_1.
	 *
	 * @param fld_14acd_1 the new fld_14acd_1
	 */
	public void setFld_14acd_1(String fld_14acd_1) {
		this.fld_14acd_1 = fld_14acd_1;
	}

	
	
	/**
	 * Gets the fld_19.
	 *
	 * @return the fld_19
	 */
	public String getFld_19() {
		return fld_19;
	}
	
	/**
	 * Sets the fld_19.
	 *
	 * @param fld_19 the new fld_19
	 */
	public void setFld_19(String fld_19) {
		this.fld_19 = fld_19;
	}
	
	/**
	 * Gets the fld_12a.
	 *
	 * @return the fld_12a
	 */
	public String getFld_12a() {
		return fld_12a;
	}
	
	/**
	 * Sets the fld_12a.
	 *
	 * @param fld_12a the new fld_12a
	 */
	public void setFld_12a(String fld_12a) {
		this.fld_12a = fld_12a;
	}
	
	/**
	 * Gets the fld_2.
	 *
	 * @return the fld_2
	 */
	public String getFld_2() {
		return fld_2;
	}
	
	/**
	 * Sets the fld_2.
	 *
	 * @param fld_2 the new fld_2
	 */
	public void setFld_2(String fld_2) {
		this.fld_2 = fld_2;
	}
}
