package com.app.pojos;

import java.time.LocalDate;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Entity

public class RecordingsManagement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int recNo;
	@Column(length = 100)
	@Type(type = "text")
	private String moduleName;
	private String recLink;
	private String topicName;
	private String objectId;
	private LocalDate uploadDate;
	@OneToOne
	private User user;
	public int getRecNo() {
		return recNo;
	}
	public void setRecNo(int recNo) {
		this.recNo = recNo;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getRecLink() {
		return recLink;
	}
	public void setRecLink(String recLink) {
		this.recLink = recLink;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public LocalDate getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(LocalDate uploadDate) {
		this.uploadDate = uploadDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
