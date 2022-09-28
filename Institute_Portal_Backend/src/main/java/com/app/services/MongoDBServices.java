package com.app.services;

import java.io.IOException;
import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.pojos.Notes;
import com.app.pojos.RecordingsManagement;
import com.app.pojos.User;
import com.app.repository.FacultyRepository;
import com.app.repository.UserRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service
public class MongoDBServices {
	
	@Autowired
	GridFsTemplate gridfstemplete;
	@Autowired
	GridFsOperations gridop;
	@Autowired
	FacultyRepository facRepo;
	@Autowired
	UserRepository userrepo;

	public boolean addNotes(MultipartFile file, String moduleName, String topicName, int userid) throws IOException {
		DBObject metadata = new BasicDBObject();
		metadata.put("type","pdf");
		metadata.put("title", moduleName+topicName);
		ObjectId id = gridfstemplete.store(file.getInputStream(),file.getName(),metadata);
		Notes note = new Notes();
		note.setBaseId(id.toString());
		note.setTopicName(topicName);
		note.setUploadDate(LocalDate.now());
		User user = userrepo.getUserById(userid);
		note.setUser(user);
		facRepo.addNote(note);
		return true;
		
	}

	public boolean addVideo(MultipartFile videorec, String moduleName, String topicName, int userid) throws IOException {
		DBObject metadata = new BasicDBObject();
		metadata.put("type","video");
		metadata.put("title", moduleName+topicName);
		ObjectId id = gridfstemplete.store(videorec.getInputStream(),videorec.getName(),metadata);
		RecordingsManagement recMgmt = new RecordingsManagement();
		recMgmt.setModuleName(moduleName);
		recMgmt.setObjectId(id.toString());
		recMgmt.setTopicName(topicName);
		recMgmt.setUploadDate(LocalDate.now());
		User user = userrepo.getUserById(userid);
		recMgmt.setUser(user);
		facRepo.addRecording(recMgmt);
		return true;
		
	}

}
