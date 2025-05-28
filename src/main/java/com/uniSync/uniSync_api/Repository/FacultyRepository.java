package com.uniSync.uniSync_api.Repository;

import com.uniSync.uniSync_api.Model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
}
