package com.uniSync.uniSync_api.Repository;

import com.uniSync.uniSync_api.Model.Society;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocietyRepository extends JpaRepository<Society, Long> {

    Optional<Society> findByName(String name);
    boolean existsByName(String name);

}
