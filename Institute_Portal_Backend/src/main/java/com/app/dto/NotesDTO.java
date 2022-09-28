package com.app.dto;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotesDTO {
	
	private int notelink;
	private String topicName;
	private int id;
	private MultipartFile file;
	private InputStream stream;
	public int getNotelink() {
		return notelink;
	}
	public void setNotelink(int notelink) {
		this.notelink = notelink;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public InputStream getStream() {
		return stream;
	}
	public void setStream(InputStream stream) {
		this.stream = stream;
	}
	
}
