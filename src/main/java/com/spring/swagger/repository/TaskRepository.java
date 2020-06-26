package com.spring.swagger.repository;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.spring.swagger.model.Tasks;

@Transactional
public interface TaskRepository extends PagingAndSortingRepository<Tasks, Long> {
	
	// fetch all the available tasks
    List<Tasks> findAll();
            
    // custom query to fetch overdue tasks
    @Query(value = "select * from tasks where due_date < ?1", nativeQuery = true)
    List<Tasks> findOverdueTasks(@Param("currentDate") Date currentDate);
    
}
