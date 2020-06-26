package com.spring.swagger.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.xml.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "tasks")
public class Tasks {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(example = "7", notes = "The database generated task ID")
	private Long id;

	@Column(name = "title", length = 256)
	@Size(max = 256, message = "Length of title should not exceed more than 256 characters")
	@NotEmpty(message = "title must not be empty")
	@ApiModelProperty(example = "Task7", notes = "The title for each task")
	private String title;

	@Column(name = "description", length = 1024)
	@Size(max = 1024, message = "Length of description should not exceed more than 1024 characters")
	@ApiModelProperty(example = "This is the description for task 7", notes = "The task description")
	private String description;

	@Column(name = "due_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(example = "2020-04-20", notes = "Due date for each task")
	private Date dueDate;

	@Column(name = "status", length = 10)
	@Size(max = 10, message = "Length of status should not exceed more than 10 characters")
	@ApiModelProperty(example = "created", notes = "Current status of the task")
	private String status;

	@Column(name = "creation_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(example = "2020-04-15", notes = "Creation date of each task")
	private Date creationDate;

	public Tasks() {

	}

	public Tasks(Long id, String title, String description, String status, Date creationDate, Date dueDate) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.creationDate = creationDate;
		this.dueDate = dueDate;
	}

	public Tasks(String title, String description, String status, Date creationDate, Date dueDate) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.creationDate = creationDate;
		this.dueDate = dueDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@AssertTrue(message = "Creation date should be before or equal to the due date")
	private boolean isValid() {
		return this.creationDate.before(getDueDate()) || this.creationDate.equals(getDueDate());
	}

	public String toString() {
		return "{\"id\":\"" + this.getId() + "\",\"title\":\"" + this.getTitle() + "\",\"description\":\""
				+ this.getDescription() + "\",\"status\":\"" + this.getStatus() + "\",\"creationDate\":\""
				+ this.getCreationDate() + "\",\"dueDate\":\"" + this.getDueDate() + "\"";
	}

}
