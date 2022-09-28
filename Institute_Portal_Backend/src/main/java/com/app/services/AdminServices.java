package com.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.app.dto.AdminDashboardDTO;
import com.app.pojos.User;
import com.app.repository.AdminRepository;
import com.app.repository.UserRepository;

@Service
public class AdminServices {

	//@Autowired
	@Autowired
	AdminRepository adminRepo;
	@Autowired
	UserRepository userRepo;
	
	public AdminDashboardDTO getDashDetails() {
		AdminDashboardDTO admindash = new AdminDashboardDTO();
		
		admindash.setCountfaculty(adminRepo.getFacCount());
		admindash.setCountstudent(adminRepo.getStudentCount());
    	admindash.setCountrecordings(adminRepo.getNotesCount());
		admindash.setCountstudymatrial(adminRepo.getRecordingCount());
		
		return admindash;
	}
	
	public List<User> getfacList() {		
		return adminRepo.getFacList();
	}
	
	public List<User> getStudList() {		
		return adminRepo.getStudList();
	}
	
	public void disableAcc(int id) {
	User u1 = userRepo.getUserById(id);	
	u1.setActive(false);
	adminRepo.updateUser(u1);	
	}
	
	public void enableAcc(int id) {
	User u1 = userRepo.getUserById(id);
	u1.setActive(true);
	adminRepo.updateUser(u1);
	
}

}
