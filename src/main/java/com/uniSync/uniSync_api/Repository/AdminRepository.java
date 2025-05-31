package com.uniSync.uniSync_api.Repository;

import com.uniSync.uniSync_api.Common.AdminType;
import com.uniSync.uniSync_api.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

    Set<Admin> findAllByAdminType(AdminType adminType);

}
