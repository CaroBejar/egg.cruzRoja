package com.example.demo.repositories;

import com.example.demo.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query("SELECT o FROM Organization o WHERE o.uploadUser.id = :id")
    public List<Organization> listByUser(@Param("id") String id);

}
