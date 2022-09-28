package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Notes;

public interface FileData extends JpaRepository<Notes, Integer> {

	Optional<Notes> findBytopicName(String fileName);

}
