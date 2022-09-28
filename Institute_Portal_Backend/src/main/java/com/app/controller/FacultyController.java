package com.app.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.mail.Multipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.AddQuestionDto;
import com.app.dto.FacultyDashboardDTO;

import com.app.dto.NotesDTO;
import com.app.dto.RecordingDTO;
import com.app.pojos.Notes;
import com.app.pojos.Options;
import com.app.pojos.Question;
import com.app.pojos.RecordingsManagement;
import com.app.pojos.Subject;
import com.app.pojos.User;
import com.app.repository.FacultyRepository;
import com.app.repository.UserRepository;
import com.app.services.FacultyServices;
import com.app.services.MongoDBServices;


@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/Faculty")
public class FacultyController {
	@Autowired
	FacultyRepository facRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	FacultyServices facService;
	@Autowired
	MongoDBServices mongoService;
	
	@PostMapping("CreateQuiz")
	public boolean addSubject(@RequestParam String sub,@RequestParam String email)
	{
		Subject subject = new Subject();
		User user =  userRepo.fetchUser(email);//faculty
		subject.setSubject(sub);	
		subject.setUser(user);
		subject.setDate(LocalDate.now());
		facRepo.addSubject(subject);
		return true;
	}
	
	@PostMapping("AddQuestion")
	public boolean addQuestion(@RequestBody AddQuestionDto addques)
	{
		Subject sub = facRepo.fetchSubjectByName(addques.getSubject());
		Question q1 = new Question();
		q1.setQuizId(sub);
		q1.setQuestion(addques.getQuestion());
		List<Options> option =  addques.getOptionlist();
		for(Options opt : option)
		{
			opt.setQue_id(q1);
		}
		q1.setOptions(option);
		facRepo.addQuestion(q1);
		return true;
	}
	
	@GetMapping("dashboard")
	public FacultyDashboardDTO fetchDashboardDet(int portalId)
	{
		FacultyDashboardDTO facDash = facService.getDashDet(portalId);
		return facDash;
	}
	
	@PostMapping("/Notices")
	public boolean sendNotice(@RequestParam int id,String subject, String notice )
	{
		
		return facService.uploadNotice(id, subject, notice);
	}
	
	@PostMapping("/PrioNotices")
	public boolean sendPrioNotice(@RequestParam int id,String subject, String notice)
	{
		 return facService.uploadPrioNotice(id, subject, notice);
	}
	
//	@PostMapping("/AddRec")/*works w/o mod.Name/topicName*/
//	public boolean addRec(@RequestBody MultipartFile videodetails, @RequestParam String moduleName, String topicName, int id)
//	{
//		RecordingDTO newRec = new RecordingDTO();
//		RecordingsManagement newVid = new RecordingsManagement();
//		newRec.setId(id);
//		newRec.setRecLink(videodetails);
//		newRec.setModuleName(moduleName);
//		newRec.setTopicname(topicName);
//		return facService.uploadVideo(newRec);
//	}
	
	
//	@PostMapping("/AddRec")/*trying for perfect solution*/
//	public boolean addRec(@RequestBody MultipartFile videodetails, @RequestParam String moduleName, String topicName, int id)
//	{
//		RecordingDTO newRec = new RecordingDTO();		
//		newRec.setId(id);
//		newRec.setRecLink(videodetails);
//		newRec.setModuleName(moduleName);
//		newRec.setTopicname(topicName);
//		
//		RecordingsManagement newVid = new RecordingsManagement();
//		newVid.setModuleName(moduleName);
//		newVid.setTopicName(topicName);
//		
//		User user = userRepo.getUserById(id);
//		newVid.setUser(user);
//		
//		return facService.uploadVideo(newRec, newVid);
//	}
	
	@PostMapping("/AddRec")
	public boolean addRec(@RequestBody MultipartFile videodetails,@RequestParam String moduleName,String topicName,int id)
	{
		try {
		mongoService.addVideo(videodetails,moduleName, topicName,id);
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	@GetMapping("/getStudyMaterial")
	public List<Notes> getNotes()
	{
		return facRepo.getNotesList();
	}
	
//	@PostMapping("/addStudyMaterial")
//	public boolean addNotes(@RequestBody MultipartFile file,@RequestParam String moduleName,String topicName,int id)
//	{
//		
//		try {
//		//mongoService.addNotes(file,moduleName,topicName,id);
//			NotesDTO newFile = new NotesDTO();
//			newFile.setId(id);
//			newFile.setFile(file);
//			newFile.setTopicName(topicName);
//			
//			Notes note = new Notes();
//			note.setTopicName(topicName);
//			note.setUploadDate(LocalDate.now());
//			
//			User user = userRepo.getUserById(id);
//			note.setUser(user);
//			
//			return facService.uploadNotes(newFile, note);
//		}
//		catch (Exception e) {
//			System.out.println(e);
//			return false;
//		}
//		//return true;
//	}
	
//	@PostMapping("/addStudyMaterial")
//	public ResponseEntity<?> addNotes(@RequestParam MultipartFile file) throws IOException{
//		String NotesData = facService.addNotes(file);
//		return ResponseEntity.status(HttpStatus.OK).body(NotesData);		
//	}
	
	@PostMapping("/addStudyMaterial")
	public boolean addNotes(@RequestBody MultipartFile file,@RequestParam String moduleName,String topicName,int id)
	{		
		try {
		mongoService.addNotes(file,moduleName,topicName,id);
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	@PostMapping ("/deleteMaterial")
	public boolean deleteMaterial(@RequestParam String baseId)
	{
		facRepo.deleteMaterial(baseId);
		return true;
	}
	
}
