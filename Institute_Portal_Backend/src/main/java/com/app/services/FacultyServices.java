package com.app.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.FacultyDashboardDTO;
import com.app.dto.NotesDTO;
import com.app.dto.RecordingDTO;
import com.app.pojos.Notes;
import com.app.pojos.Notices;
import com.app.pojos.RecordingsManagement;
import com.app.pojos.User;
import com.app.repository.FacultyRepository;
import com.app.repository.FileData;
import com.app.repository.UserRepository;

@Service
public class FacultyServices {
@Autowired
FacultyRepository facRepo;
@Autowired
UserRepository userReop;
@Autowired
MailService mailService;

@Autowired
FileData fileRepo;
	
private final String filePath = "D:\\CDAC_ Project\\Project_files\\";

	public FacultyDashboardDTO getDashDet(int i)
	{
		FacultyDashboardDTO facDac = new FacultyDashboardDTO();
		
		facDac.setCountNote(facRepo.getCountNotes(i));
		facDac.setCountNotice(facRepo.getCountNotices(i));
		facDac.setCountQuiz(facRepo.getCountQuiz(i));
		facDac.setCountRecording(facRepo.getCountRecording(i));
		return facDac;
	}
	
	public boolean uploadNotice(int id,String subject, String notice)
	{
		Notices newNotice= new Notices();
		newNotice.setNotice(notice);
		newNotice.setSubject(subject);
		User user = userReop.getUserById(id);
		newNotice.setUser(user);
		newNotice.setUploadDate(LocalDate.now());
		
		return facRepo.addNotice(newNotice);
	}
	
	public boolean uploadPrioNotice(int id, String subject, String notice) 
	{
		Notices newNotice= new Notices();
		newNotice.setNotice(notice);
		newNotice.setSubject(subject);
		User user = userReop.getUserById(id);
		newNotice.setUser(user);
		newNotice.setUploadDate(LocalDate.now());
		facRepo.addNotice(newNotice);
		List<String> mailList = facRepo.studentMailList();
		for(String email:mailList)
		{
		mailService.sendNotice(email, subject, notice);
		}
		return true;
	}
	
	//saving in DB without attr. columns
//	public boolean uploadVideo(RecordingDTO newRec) {
//		RecordingsManagement newVid = new RecordingsManagement();
//		String filename = newVid.getTopicName()+Integer.toString(newVid.getRecNo())+".mp4";
//		
//		try {
//			FileCopyUtils.copy(newRec.getRecLink().getInputStream(), new FileOutputStream("F:/Pravin CDAC/Project/ProjectImp/"+filename));
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//		newVid.setRecLink(filename);
//		facRepo.addRecording(newVid);
//		return true;
//	}
	
	
//	public boolean uploadVideo(RecordingDTO newRec, RecordingsManagement newVideo) {
//		RecordingsManagement newVid = new RecordingsManagement();
//		//String filename = newVideo.getTopicName()+Integer.toString(newVideo.getRecNo())+".mp4";
//		String filename = newVideo.getTopicName()+".mp4";
//		
//		try {
//			FileCopyUtils.copy(newRec.getRecLink().getInputStream(), new FileOutputStream("F:/Pravin CDAC/Project/ProjectImp/"+filename));
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//		//newVid.setModuleName(null);
//		newVid.setModuleName(newVideo.getModuleName());
//		newVid.setTopicName(newVideo.getTopicName());
//		newVid.setUser(newVideo.getUser());
//		newVid.setRecLink(filename);
//		newVid.setUploadDate(LocalDate.now());
//		facRepo.addRecording(newVid);
//		return true;
//	}
	
//	public boolean uploadNotes(NotesDTO newFile, Notes note) {
//		String fileName = note.getTopicName()+".pdf";
//		Notes newNotes = new Notes();
//		
//		try {
//			FileCopyUtils.copy(newFile.getFile().getInputStream(), new FileOutputStream("F:\\ProjectFilesData\\Files\\"+fileName));
//			//FileCopyUtils.copy(newFile.getFile().getInputStream(), new FileOutputStream(fileName));
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		
//		//newNotes.setTopicName(note.getTopicName());
//		newNotes.setUser(note.getUser());
//		newNotes.setTopicName(fileName);
//		newNotes.setUploadDate(LocalDate.now());
//		facRepo.addNote(newNotes);
//		return true;
//	}
	
//	public String addNotes(MultipartFile file) throws IOException{
//		String storagePath = filePath+file.getOriginalFilename();
//		Notes newNote = fileRepo.save(Notes.builder()
//				.topicName(storagePath)
//				.uploadDate(LocalDate.now()).build());
//		
//		file.transferTo(new File(storagePath));
//		
//		if (newNote != null) {
//	        return "file uploaded successfully : " + storagePath;
//	    }
//	    return null;
//	}
	
	
	
}
