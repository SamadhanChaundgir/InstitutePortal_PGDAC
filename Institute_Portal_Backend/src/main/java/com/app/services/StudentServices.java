package com.app.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.app.dto.NotesDTO;
import com.app.dto.OptionsDTO;
import com.app.dto.QuestionListDTO;
import com.app.dto.ShowSubjectsDTO;
import com.app.pojos.Notes;
import com.app.pojos.Notices;
import com.app.pojos.Options;
import com.app.pojos.Question;
import com.app.pojos.RecordingsManagement;
import com.app.pojos.ScoreDetails;
import com.app.pojos.Subject;
import com.app.pojos.User;
import com.app.repository.FileData;
import com.app.repository.StudentRepository;
import com.app.repository.UserRepository;

@Service
public class StudentServices {
	@Autowired
	StudentRepository stdRepo;
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	FileData fileRepo;
	
	public List <ShowSubjectsDTO> showSubjects()
	{
		List<Subject> subList = stdRepo.getSubjectList();
		List<ShowSubjectsDTO> subDtoList = new ArrayList<ShowSubjectsDTO>();
		for(Subject sub : subList)
		{
			ShowSubjectsDTO subdto= new ShowSubjectsDTO();
			subdto.setDate(sub.getDate());
			subdto.setFacname(sub.getUser().getName());
			subdto.setQuizId(sub.getQuizId());
			subdto.setSubject(sub.getSubject());
			subDtoList.add(subdto);
		}
		return subDtoList;
	}
	
	@Transactional
	public List<QuestionListDTO> getQuestionList(int id)
	{
		List<Question> listQuestion = stdRepo.getQuiz(id);
		List<QuestionListDTO> questionList = new ArrayList<QuestionListDTO>();
		for(Question question : listQuestion)
		{
			QuestionListDTO quest = new QuestionListDTO();
			quest.setId(id);
			quest.setQuizId(question.getQuizId().getQuizId());
			quest.setQuestion(question.getQuestion());
			List<OptionsDTO> listOptDto = new ArrayList<OptionsDTO>();
			for(Options option : question.getOptions() )
			{
				OptionsDTO opt = new OptionsDTO();
				opt.setOptionString(option.getOptionString());
				opt.setQue_id(question.getId());
				opt.setCorrect(option.isCorrect());
				listOptDto.add(opt);
			}
			quest.setOptions(listOptDto);
		  questionList.add(quest);
		}
		return questionList;
	}

	public boolean addScore(int userid, int quizid, int score) 
	{		
		User u1 =  userRepo.getUserById(userid);
	    Subject sub = stdRepo.geSubject(quizid);
		ScoreDetails scoredet = new ScoreDetails();
		scoredet.setDate(LocalDate.now());
		scoredet.setQuizId(sub);
		scoredet.setScore(score);
		scoredet.setUserID(u1);
		return stdRepo.addScore(scoredet);
	}
	
	public List<Notices> displayNotice()
	{
		List<Notices> noticeList = stdRepo.getNotices();
		return noticeList;
	}
	
//	public byte[] getVideo(int recNum) throws IOException{
//		RecordingsManagement newVid = new RecordingsManagement();
//		String path = "F:/Pravin CDAC/Project/ProjectImp/"+newVid.getRecLink();
//		try {
//			//BufferedImage bufferimage = ImageIO.read(new File(path));
//			File file = new File(path);
//			FileInputStream input = new FileInputStream(file);
//			byte[] data = input.readAllBytes();
//			FileOutputStream output = new FileOutputStream(file);
//			output.write(data);
//			//byte[] data = newVid.getRecLink()
//			//FileOutputStream output = new FileOutputStream(file);
//		    //ByteArrayOutputStream str = new ByteArrayOutputStream(recNum);
//		   // ImageIO.write(bufferimage, "mp4", output );
//		    
//		    
//		    //return output.write(data);
//		}
//		catch (Exception e) {
//			return null;
//		}
		
//	}
	//@Override
//	public byte[] downloadNotes(String fileName) throws IOException {
//		Optional<Notes> notesData = fileRepo.findBytopicName(fileName);
//		String filePath = notesData.get().getTopicName();
//		byte[] pdf = Files.readAllBytes(new File(filePath).toPath());
//		return pdf;
//	}
//
//	public byte[] getNotesPdf(int id) {
//		Notes note = stdrepo.getNotesById(id);
//		String path = "F:\\ProjectFilesData\\Files\\"+note.getTopicName();
//		try {
//			File file = new File(path);
//			FileInputStream input = new FileInputStream(file);
//			NotesDTO note1 = new NotesDTO();
//			note1.setStream(input);
//			ByteArrayOutputStream output = new ByteArrayOutputStream();
//			
//			//FileCopyUtils.copy(note1.getStream(),response.getOutputStream());
//			return output.toByteArray();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		// TODO Auto-generated method stub
//		return null;
//	}

}
