package com.uniSync.uniSync_api.Repository;

import com.uniSync.uniSync_api.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository {

    List<Item> findByFaculty_Id(Long facultyId);
    List<Item> findByDepartment_Id(Long departmentId);
    List<Item> findBySociety_Id(Long societyId);
}
