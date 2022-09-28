package com.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.NotesDTO;
import com.app.dto.QuestionListDTO;
import com.app.dto.RecordingDTO;
import com.app.dto.ShowScoreDTO;
import com.app.dto.ShowSubjectsDTO;
import com.app.pojos.Notes;
import com.app.pojos.Notices;
import com.app.pojos.RecordingsManagement;
import com.app.pojos.ScoreDetails;
import com.app.pojos.User;
//import com.app.pojos.mongoVideo;
import com.app.repository.StudentRepository;
import com.app.services.StudentServices;
import com.mongodb.client.gridfs.model.GridFSFile;


@RestController
@CrossOrigin
@RequestMapping("/Student")
public class StudentController{

	@Autowired
	StudentRepository stdrepo;
	@Autowired
	StudentServices stdService;
	@Autowired
	GridFsTemplate gridfs;
	@Autowired
	GridFsOperations gridFsop;
	
	EntityManager em;
	
	@PostMapping("/")
	public List<ScoreDetails> dashBoard(@RequestBody User student)
	{
		return stdrepo.dashBoard(student);
	}
	
	@GetMapping("/DisplaySubjects")
	public List<ShowSubjectsDTO> showSubList()
	{
		List <ShowSubjectsDTO> subList = stdService.showSubjects();
		return subList;
	}
	
	@PostMapping("/Notice")
	public List<Notices> showNotices()
	{
		List<Notices> notice = stdrepo.getNoticeList();
		return notice;
	}
	
	@PostMapping("/AddScore")
	public boolean addScore(@RequestParam int userid, @RequestParam int quizid, @RequestParam int score)
	{
		boolean status = stdService.addScore(userid,quizid,score);
		return status;
	}
	
	@GetMapping("/getScore")
	public List<ShowScoreDTO> getScore(@RequestParam int id)
	{
		List<ShowScoreDTO>scoreList = stdrepo.getScores(id);
		return scoreList;
	}
	
	@GetMapping("/getQuestionList")
	public List<QuestionListDTO> getQuiz(@RequestParam int id)
	{
		List<QuestionListDTO> queList = stdService.getQuestionList(id);
		return queList;
	}
	
	@GetMapping("/getNotice")
	public List<Notices> getNotice()
	{
		return stdService.displayNotice();
	}
	
	@GetMapping("/videoView")
	public void getVideo(@RequestParam String id , HttpServletResponse response)throws Exception
	{
		GridFSFile file= gridfs.findOne(new Query(Criteria.where("_id").is(id)));  
		RecordingDTO recording = new RecordingDTO();
		recording.setStream(gridFsop.getResource(file).getInputStream());
		FileCopyUtils.copy(recording.getStream(),response.getOutputStream());
	}
	
//	@GetMapping("/videoView")
//	public void getVideo(@RequestParam String id , HttpServletResponse response)throws Exception
//	{
//		GridFSFile file= gridfs.findOne(new Query(Criteria.where("_id").is(id)));  
//		RecordingDTO recording = new RecordingDTO();
//		recording.setStream(gridFsop.getResource(file).getInputStream());
//		FileCopyUtils.copy(recording.getStream(),response.getOutputStream());
//	}
	
//	@GetMapping(value = "/videoView", produces = MediaType.ALL_VALUE)
//	public byte[] getVideowithMediaType(@RequestParam int recNum) throws IOException
//	{
//		return stdService.getVideo(recNum);
//	}
	
	//@GetMapping(value = "/videoView", produces = MediaType.ALL_VALUE)
//	@GetMapping("/videoView")
//	//public byte[] getVideowithMediaType(@RequestParam int recNum) throws IOException
//	public void getVideo(@RequestParam String id , HttpServletResponse response)throws Exception
//	{
//		System.out.println("in student Controller..."+id+"videoview");
//		//String fileName = (String) em.createQuery("select v from RecordingsManagement v where v.recNo=:id").getSingleResult();
//		
////		RecordingsManagement newVid = new RecordingsManagement();
////		String path = (String) em.createQuery("select v from RecordingsManagement v where v.recNum=:id").getSingleResult();
////		RecordingDTO recording = new RecordingDTO();
////		FileInputStream input = new FileInputStream(path); 
////		recording.setStream(input);
////		FileCopyUtils.copy(recording.getStream(),response.getOutputStream());
//		//File file = new File(fileName);
//		//FileReader read= new FileReader(file);
//		//return stdService.getVideo(recNum);
//		//return fileName;
//	}
	
//	@GetMapping(value = "/videoView", produces = MediaType.ALL_VALUE)
//	public void getVideo(@RequestParam String id , HttpServletResponse response)throws Exception
//	{
//		try {
//			RecordingsManagement newVid = new RecordingsManagement();
//			//String path = "F:\\Pravin CDAC\\Project\\ProjectImp\\"+newVid.getTopicName()+Integer.toString(newVid.getRecNo())+".mp4";
//			//String path = "F:\\Pravin CDAC\\Project\\ProjectImp\\"+newVid.getRecLink()+".mp4";
//			String path = "F:\\Pravin CDAC\\Project\\ProjectImp\\SpringInfo0.mp4";
//			  System.out.println(path);
////			File file = new File(path);
////			System.out.println(file);
////			FileInputStream input = new FileInputStream(file);
////			//FileInputStream input = new FileInputStream(id);
////			RecordingDTO recording = new RecordingDTO();
////			//recording.setStream(gridFsop.getResource(file).getInputStream());
////			recording.setStream(input);
////			FileCopyUtils.copy(recording.getStream(),response.getOutputStream());			
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	
//		
//	}
		
//		FileInputStream input = new FileInputStream("F:/Pravin CDAC/Project/ProjectImp/"+newVid.getRecLink()+".mp4");      //input file
//        byte[] data = input.readAllBytes();
//        FileOutputStream output = new FileOutputStream("F:/Pravin CDAC/Project/ProjectImp/"+newVid.getRecLink()+".mp4");  //output file
//        output.write(data);
//        output.close();
        
//        final int BUFFERSIZE = 40 * 1024;
//        String sourceFilePath = "F:/Pravin CDAC/Project/ProjectImp/"+newVid.getRecLink()+".mp4";
//        String outputFilePath = "F:/Pravin CDAC/Project/ProjectImp/"+newVid.getRecLink()+".mp4";
//
//        int bytesRead;
//        try(
//                FileInputStream fin = new FileInputStream(new File(sourceFilePath));
//                FileOutputStream fout = new FileOutputStream(new File(outputFilePath));
//                ){
//            
//            byte[] buffer = new byte[BUFFERSIZE];
//            
//            while(fin.available() != 0) {
//                bytesRead = fin.read(buffer);
//                fout.write(buffer, 0, bytesRead);
//            }
//            
//        }
//        catch(Exception e) {
//            System.out.println("Something went wrong! Reason: " + e.getMessage());
//        }
		 
//	}
	
	@GetMapping("VideoList")
	public List<RecordingsManagement> getVideoList()
	{
		List<RecordingsManagement> vidList =stdrepo.getVidList();
		return vidList;
	}
	
	@GetMapping("StudyList")
	public List<Notes> getNoteList()
	{
		List<Notes> noteList = stdrepo.getnoteList();
		return noteList;
	}
	
	@GetMapping("/notes.pdf")
	public void getNotes(@RequestParam String id , HttpServletResponse response)throws Exception
	{
		GridFSFile file= gridfs.findOne(new Query(Criteria.where("_id").is(id)));  
		RecordingDTO recording = new RecordingDTO();
		recording.setStream(gridFsop.getResource(file).getInputStream());
		FileCopyUtils.copy(recording.getStream(),response.getOutputStream());
	}
	
//	@GetMapping("/notes.pdf")
//	public void getNotes(@RequestParam String id , HttpServletResponse response)throws Exception
//	{
//		//GridFSFile file= gridfs.findOne(new Query(Criteria.where("_id").is(id)));  
//		//File file = em.createQ
//		Notes newNote = new Notes();		
//		
//		//String path = "F:\\ProjectFilesData\\Files\\JavaTut.pdf";
//		String path = newNote.getTopicName();
//		File file = new File(path);
//		System.out.println(file);
//		FileInputStream input = new FileInputStream(file);
//		//FileInputStream input = new FileInputStream(id);
//		//RecordingDTO recording = new RecordingDTO();
//		//recording.setStream(gridFsop.getResource(file).getInputStream());
//		NotesDTO note = new NotesDTO();
//		note.setStream(input);
//		FileCopyUtils.copy(note.getStream(),response.getOutputStream());
////		RecordingDTO recording = new RecordingDTO();
////		recording.setStream(gridFsop.getResource(file).getInputStream());
////		FileCopyUtils.copy(recording.getStream(),response.getOutputStream());
//	}
	
//	@GetMapping("/notes.pdf")
//	public byte[] getNotes(@RequestParam int id)throws IOException
//	{
//		return stdService.getNotesPdf(id);
//	}
	
//	@GetMapping("/notes.pdf")
//	public ResponseEntity<?> getNotes(@PathVariable String topicName) throws IOException{
//		byte[] notesData = stdService.downloadNotes(topicName);
//		return  ResponseEntity.status(HttpStatus.OK)
//				.contentType(MediaType.valueOf("pdf"))
//				.body(notesData);
//	}
	
}
