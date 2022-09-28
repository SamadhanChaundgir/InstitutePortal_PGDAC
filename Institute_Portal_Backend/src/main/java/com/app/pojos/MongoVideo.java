package com.app.pojos;

import java.io.InputStream;

import lombok.Getter;
import lombok.Setter;

public class MongoVideo {
	
	private String title;
	private InputStream stream;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public InputStream getStream() {
		return stream;
	}
	public void setStream(InputStream stream) {
		this.stream = stream;
	}

}
