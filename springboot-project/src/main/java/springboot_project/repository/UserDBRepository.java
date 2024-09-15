package springboot_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import springboot_project.model.UserDB;

@Repository
public interface UserDBRepository extends JpaRepository<UserDB, Long> {

	@Query(value = "select u from UserDB u where upper(trim(u.name)) like %?1%") //trim/upper here preucacion...
	List<UserDB> searchByName(String name);
	
}
