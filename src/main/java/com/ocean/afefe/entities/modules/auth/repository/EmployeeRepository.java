package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.Employee;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByUserAndOrg(User user, Organization org);

    List<Employee> findByOrgAndUser_IdIn(Organization org, Collection<UUID> userIds);
}
