package com.spring.swagger;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.spring.swagger.model.Tasks;
import com.spring.swagger.repository.TaskRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TaskRepository taskRepo;

	/**
	 * Test method to test the status of /getTaskById?{id} API
	 * @throws Exception
	 */
	@Test
	public void getOneTaskStatusTest() throws Exception {
		long id = 10;
		Tasks task = new Tasks(id, "Task10", "Description of task 10", "Created",
				new java.sql.Date(new Date().getTime()), new java.sql.Date(new Date().getTime()));
		when(taskRepo.findOne(id)).thenReturn(task);
		mvc.perform(get("/getTaskById?id=10").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	/**
	 * Test method to test the return value of /getTaskById?{id} API
	 * @throws Exception
	 */
	@Test
	public void getOneTaskValueEqualTest() throws Exception {
		long id = 10;
		Tasks task = new Tasks(id, "Task10", "Description of task 10", "Created",
				new java.sql.Date(new Date().getTime()), new java.sql.Date(new Date().getTime()));
		when(taskRepo.findOne(id)).thenReturn(task);

		MvcResult result = mvc.perform(get("/getTaskById?id=10").contentType(MediaType.APPLICATION_JSON)).andReturn();
		JSONObject content = (JSONObject) new JSONParser().parse(result.getResponse().getContentAsString());
		assertEquals("Description of task 10", content.get("description"));
	}
	
	/**
	 * Test method to test the status of /getTaskById?{id} API (masquerade)
	 * @throws Exception
	 */
	@Test
	public void getOneTaskValueNotEqualTest() throws Exception {
		long id = 10;
		Tasks task = new Tasks(id, "Task10", "Description of task 10", "Created",
				new java.sql.Date(new Date().getTime()), new java.sql.Date(new Date().getTime()));
		when(taskRepo.findOne(id)).thenReturn(task);

		MvcResult result = mvc.perform(get("/getTaskById?id=10").contentType(MediaType.APPLICATION_JSON)).andReturn();
		JSONObject content = (JSONObject) new JSONParser().parse(result.getResponse().getContentAsString());
		assertNotEquals("scheduled", content.get("status"));
	}

	/**
	 * Test method to test the status of /getAllTasks API
	 * @throws Exception
	 */
	@Test
	public void getAllTasksTest() throws Exception {
		long id = 11;
		Tasks task = new Tasks(id, "Task11", "Description of task 11", "Created",
				new java.sql.Date(new Date().getTime()), new java.sql.Date(new Date().getTime()));
		List<Tasks> tasks = Arrays.asList(task);
		when(taskRepo.findAll()).thenReturn(tasks);
		mvc.perform(get("/getAllTasks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	/**
	 * Test method to test the return value of /getAllTasks API
	 * @throws Exception
	 */
	@Test
	public void getAllTasksValueEqualTest() throws Exception {
		long id = 11;
		Tasks task = new Tasks(id, "Task11", "Description of task 11", "Created",
				new java.sql.Date(new Date().getTime()), new java.sql.Date(new Date().getTime()));
		List<Tasks> tasks = Arrays.asList(task);
		when(taskRepo.findAll()).thenReturn(tasks);

		MvcResult result = mvc.perform(get("/getAllTasks").contentType(MediaType.APPLICATION_JSON)).andReturn();
		JSONArray content = (JSONArray) new JSONParser().parse(result.getResponse().getContentAsString());
		assertEquals("Description of task 11", ((JSONObject) content.get(0)).get("description"));

	}
	
	/**
	 * Test method to test the return value of /getAllTasks API (masquerade)
	 * @throws Exception
	 */
	@Test
	public void getAllTasksValueNotEqualTest() throws Exception {
		long id = 11;
		Tasks task = new Tasks(id, "Task11", "Description of task 11", "Created",
				new java.sql.Date(new Date().getTime()), new java.sql.Date(new Date().getTime()));
		List<Tasks> tasks = Arrays.asList(task);
		when(taskRepo.findAll()).thenReturn(tasks);

		MvcResult result = mvc.perform(get("/getAllTasks").contentType(MediaType.APPLICATION_JSON)).andReturn();
		JSONArray content = (JSONArray) new JSONParser().parse(result.getResponse().getContentAsString());
		assertNotEquals("Task12", ((JSONObject) content.get(0)).get("title"));

	}

}
