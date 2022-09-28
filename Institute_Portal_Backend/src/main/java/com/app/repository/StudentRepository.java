package com.app.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.transaction.Transactional;

import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Repository;

import com.app.dto.ShowScoreDTO;
import com.app.dto.StudentDashboardDTO;
import com.app.pojos.Notes;
import com.app.pojos.Notices;
import com.app.pojos.Question;
import com.app.pojos.RecordingsManagement;
import com.app.pojos.ScoreDetails;
import com.app.pojos.Subject;
import com.app.pojos.User;

@Repository
public class StudentRepository {

	@PersistenceContext
	EntityManager em;
	
	public List<ScoreDetails> dashBoard(User student)
	{
		List<ScoreDetails> scoreList =  new ArrayList<>();
		scoreList = em.createQuery("select s from ScoreDetails s where s.userID.userId=:id").setParameter("id", student.getUserId()).getResultList();
		return scoreList;
	}

	public List<Subject> getSubjectList() 
	{		
		List <Subject> subList= em.createQuery("select s from Subject s").getResultList(); 
		return subList;
	}

	public List<Notices> getNoticeList() 
	{
		List<Notices> noticeList = em.createQuery("From Notices n ORDER BY n.uploadDate").getResultList();
		return noticeList;
	}

	@Transactional
	public boolean addScore(ScoreDetails score) 
	{
		try {
		em.persist(score);
		}
		catch(Exception e) {
		return false;
		}
		return true;
	}

	public List<ShowScoreDTO> getScores(int id) 
	{
		List<ScoreDetails> scoreList = em.createQuery("select s from ScoreDetails s where s.userID.userId=:ids").setParameter("ids", id).getResultList();
        List<ShowScoreDTO> scoreDtoList = new ArrayList<ShowScoreDTO>();
		
		for(ScoreDetails score: scoreList)
		{
			ShowScoreDTO sco = new ShowScoreDTO();
			sco.setName(score.getQuizId().getSubject());
			sco.setDate(score.getDate());
			sco.setScore(score.getScore());
			scoreDtoList.add(sco);
		}
		return scoreDtoList;
	}
	
	public List<Question> getQuiz(int id)
	{
		List<Question> queList = em.createQuery("select q from Question q where q.quizId.id = :id").setParameter("id",id).getResultList();
		return queList;
	}

	public Subject geSubject(int quizid) 
	{
		Subject subject = em.find(Subject.class, quizid);
		return subject;
	}

	public List<Notices> getNotices() 
	{
		List<Notices> notices = em.createQuery("select n from Notices n").getResultList();
		return notices;
	}

	public List<RecordingsManagement> getVidList() 
	{
		 List<RecordingsManagement> list = em.createQuery("select r from RecordingsManagement r").getResultList();
		 List<RecordingsManagement> newList = new ArrayList<RecordingsManagement>();
		 for(RecordingsManagement record : list)
		 {
			 record.setUser(null);
			 newList.add(record);
		 }
		 return newList;
	}

	public List<Notes> getnoteList() 
	{
		List<Notes>  list = em.createQuery("select n from Notes n").getResultList();
		return list;
	}

//	public Notes getNotesById(int id) 
//	{
//		Notes note = em.find(Notes.class, id);
//		return note;
//	}
	
}
