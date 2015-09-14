package com.oceanbank.webapp.restoauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.oceanbank.webapp.restoauth.listener.AbstractAuditEntityTimestamp;


@Entity
@Table(name = "DashboardUpload")
@NamedQueries({
	  @NamedQuery(name="DashboardUpload.findAmlBatchUploadById",
	              query = "from DashboardUpload where id = :id")
	})
public class DashboardUpload extends AbstractAuditEntityTimestamp implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "table_id")
	private int tableId;
	
	@Column(name = "table_name", length = 60)
	private String tableName;
	
	@Column(name = "description", length = 200)
	private String description;
	
	@Column(name = "filename", length = 60)
	private String fileName;
	
	@Column(name = "createdby", length = 45)
	private String createdby;
	
	@Column(name = "modifiedby", length = 45)
	private String modifiedby;
	
	public DashboardUpload(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
