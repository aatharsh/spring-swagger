package com.spring.swagger.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.swagger.TaskApplication;
import com.spring.swagger.model.Tasks;
import com.spring.swagger.repository.TaskRepository;

import io.swagger.annotations.Api;

@Controller
@Api(value = "taskcontroller", description = "Code task by Solnet")
public class TaskController {
	private static final Logger logger = LogManager.getLogger(TaskController.class);

	@Autowired
	private TaskRepository taskRepo;

	private static final String TASK_NOT_FOUND = "Task not found";
	private static final String NO_OVERDUE_TASK = "No overdue tasks";
	private static final String TASK_ADDED = "Task information has been added";
	private static final String TASK_EXISTING = "Task is already existing";
	private static final String TASK_DELETED = "Task has been deleted";
	private static final String TASK_UPDATED = "Task informattion has been updated";
	private static final String TASK_ADD_ERROR = "Task information has not been added";
	private static final String TASK_UPDATE_ERROR = "Task information has not been updated";

	/**
	 * API that returns all the tasks from backend db.
	 * 
	 * @return List<Tasks>
	 */
	@RequestMapping(value = "/getAllTasks", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAllTasks() {
		logger.info("Accessing API - /getAllTasks");

		List<Tasks> tasks = taskRepo.findAll();
		if (!tasks.isEmpty()) {
			return new ResponseEntity<>(tasks, HttpStatus.OK);
		} else {
			logger.error(TASK_NOT_FOUND);
			return new ResponseEntity<>(TASK_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * API to fetch the details of a particular task by Id
	 * 
	 * @param id
	 * @return Task
	 */
	@RequestMapping(value = "/getTaskById", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getTaskById(@RequestParam(value = "id", required = true) Long id) {
		logger.info("Accessing API - /getTasksById with param id " + id);

		Tasks task = taskRepo.findOne(id);
		if (task != null) {
			return new ResponseEntity<>(task, HttpStatus.OK);
		} else {
			logger.error(TASK_NOT_FOUND);
			return new ResponseEntity<>(TASK_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * API to return overdue tasks (current_date > due_date)
	 * 
	 * @return List<Tasks>
	 */
	@RequestMapping(value = "/getOverdueTasks", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getOverdueTasks() {
		logger.info("Accessing API - /getOverdueTasks");

		java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
		List<Tasks> tasks = taskRepo.findOverdueTasks(sqlDate);
		if (!tasks.isEmpty()) {
			return new ResponseEntity<>(tasks, HttpStatus.OK);
		} else {
			logger.error(NO_OVERDUE_TASK);
			return new ResponseEntity<>(NO_OVERDUE_TASK, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * API to add task to the backend db
	 * 
	 * @param Task
	 * @return String
	 */
	@RequestMapping(value = "/addTask", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addTask(@Valid @RequestBody Tasks task) {
		logger.info("Accessing API - /addTask - " + task.toString());

		if (task.getId() != null) {
			Tasks existingTask = taskRepo.findOne(task.getId());
			if (existingTask != null) {
				logger.error(TASK_EXISTING);
				return new ResponseEntity<>(TASK_EXISTING, HttpStatus.FORBIDDEN);
			}
		}
		Tasks savedTask = taskRepo.save(task);
		if (savedTask != null) {
			logger.info(TASK_ADDED);
			return new ResponseEntity<>(TASK_ADDED, HttpStatus.OK);
		} else {
			logger.error(TASK_ADD_ERROR);
			return new ResponseEntity<>(TASK_ADD_ERROR, HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * API to delete a task in the backend db
	 * 
	 * @param id
	 * @return String
	 */
	@RequestMapping(value = "/deleteTask", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> deleteTaskById(@RequestParam(value = "id", required = true) Long id) {
		logger.info("Accessing API - /deleteTask with param id " + id);

		Tasks task = taskRepo.findOne(id);
		if (task != null) {
			taskRepo.delete(task);
			logger.info(TASK_DELETED);
			return new ResponseEntity<>(TASK_DELETED, HttpStatus.OK);
		} else {
			logger.error(TASK_NOT_FOUND);
			return new ResponseEntity<>(TASK_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * API to update a task in the backend db
	 * 
	 * @param Task
	 * @return String
	 */
	@RequestMapping(value = "/updateTask", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateTaskById(@Valid @RequestBody Tasks task) {
		logger.info("Accessing API - /updateTask - " + task.toString());

		Tasks updateTask = taskRepo.findOne(task.getId());
		if (updateTask != null) {
			Tasks savedTask = taskRepo.save(task);
			if (savedTask != null) {
				logger.info(TASK_UPDATED);
				return new ResponseEntity<>(TASK_UPDATED, HttpStatus.OK);
			} else {
				logger.error(TASK_UPDATE_ERROR);
				return new ResponseEntity<>(TASK_UPDATE_ERROR, HttpStatus.FORBIDDEN);
			}
		} else {
			logger.error(TASK_NOT_FOUND);
			return new ResponseEntity<>(TASK_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}
}
