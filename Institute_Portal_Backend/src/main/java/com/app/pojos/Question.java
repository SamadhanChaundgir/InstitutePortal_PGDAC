package com.app.pojos;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String question;
	@ManyToOne(cascade =CascadeType.ALL)
	//@ManyToOne(cascade =CascadeType.MERGE)
	@JoinColumn
	private Subject quizId;
	@OneToMany(mappedBy = "que_id",cascade =CascadeType.ALL)
	//@OneToMany(mappedBy = "que_id",cascade =CascadeType.MERGE)
	private List<Options> options;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Subject getQuizId() {
		return quizId;
	}
	public void setQuizId(Subject quizId) {
		this.quizId = quizId;
	}
	public List<Options> getOptions() {
		return options;
	}
	public void setOptions(List<Options> options) {
		this.options = options;
	}
	
}


