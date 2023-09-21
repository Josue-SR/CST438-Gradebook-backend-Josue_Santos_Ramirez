package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentDTO;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.GradeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

/* 
 * Example of using Junit 
 * Mockmvc is used to test a simulated REST call to the RestController
 * This test assumes that students test4@csumb.edu, test@csumb.edu are enrolled in course 
 * with assignment with id=1
 */
@SpringBootTest
@AutoConfigureMockMvc
public class JunitTestGradebook {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private AssignmentGradeRepository assignmentGradeRepository;
	
	@Autowired
	private AssignmentRepository assignmentRepository;

	/* 
	 * Enter a new grade for student test4@csumb.edu for assignment id=1
	 */
	@Test
	public void gradeAssignment() throws Exception {

		MockHttpServletResponse response;

		// do an http get request for assignment 1 and test4
		response = mvc.perform(MockMvcRequestBuilders.get("/gradebook/1").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// verify return data with entry for one student without no score
		assertEquals(200, response.getStatus());

		// verify that returned data has non zero primary key
		GradeDTO[] result = fromJsonString(response.getContentAsString(), GradeDTO[].class);
		 
		for (int i=0; i<result.length; i++) {
			GradeDTO g = result[i];
			if (g.email().equals("test4@csumb.edu")) {
				// change grade from null to 80.
				assertNull(g.grade());
				result[i] = new GradeDTO(g.assignmentGradeId(), g.name(), g.email(), 80);
				
			}
		}

		// send updates to server
		response = mvc
				.perform(MockMvcRequestBuilders.put("/gradebook/1").accept(MediaType.APPLICATION_JSON)
						.content(asJsonString(result)).contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// verify that return status = OK (value 200)
		assertEquals(200, response.getStatus());
		// verify that database assignmentGrade table was correctly updated
		AssignmentGrade ag = assignmentGradeRepository.findByAssignmentIdAndStudentEmail(1,  "test4@csumb.edu");
		assertEquals(80, ag.getScore());
		
	}

	/* 
	 * Update existing grade of test@csumb.edu for assignment id=1 from 90 to 88.
	 */
	@Test
	public void updateAssignmentGrade() throws Exception {

		MockHttpServletResponse response;

		// do an http get request for assignment 1
		response = mvc.perform(MockMvcRequestBuilders.get("/gradebook/1").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// verify return data with entry for one student without no score
		assertEquals(200, response.getStatus());

		// verify that returned data has non zero primary key
		GradeDTO[] result = fromJsonString(response.getContentAsString(), GradeDTO[].class);
		// change grade of student test@csumb.edu from 90 to 88
		for (int i=0; i<result.length; i++) {
			GradeDTO g = result[i];
			if (g.email().equals("test@csumb.edu")) {
				assertEquals(90, g.grade());
				result[i] = new GradeDTO(g.assignmentGradeId(), g.name(), g.email(), 88);
				
			}
		}

		// send updates to server
		response = mvc
				.perform(MockMvcRequestBuilders.put("/gradebook/1").accept(MediaType.APPLICATION_JSON)
						.content(asJsonString(result)).contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// verify that return status = OK (value 200)
		assertEquals(200, response.getStatus());
		
		AssignmentGrade ag = assignmentGradeRepository.findByAssignmentIdAndStudentEmail(1,  "test@csumb.edu");
		assertEquals(88, ag.getScore());


	}
	
	/* 
	 * List existing assignments
	 */
	@Test
	public void listAssignments() throws Exception {
		MockHttpServletResponse response;

		response = mvc.perform(MockMvcRequestBuilders.get("/assignment").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
		// There is 4 elements in the database so the length should be returning a list with size 2
		assertEquals(2, new ObjectMapper().readValue(response.getContentAsString(), AssignmentDTO[].class).length);

	}
	
	@Test
	public void createAssignment() throws Exception {
		MockHttpServletResponse response;

		AssignmentDTO asDTO = new AssignmentDTO(4,"Assignment 2", "2023-09-19", "CST 438 - Software Engineering", 31045);
		response = mvc.perform(MockMvcRequestBuilders.post("/assignment").accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(asDTO)).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		
		Assignment as = assignmentRepository.findById(3).get();
		assertEquals(as.getName().contentEquals("Assignment 2"),true);

	}
	
	@Test
	public void updateAssignment() throws Exception {
		MockHttpServletResponse response;

		AssignmentDTO asDTO = new AssignmentDTO(4,"Assignment 3", "2023-09-19", "CST 438 - Software Engineering", 31045);
		response = mvc.perform(MockMvcRequestBuilders.put("/assignment/3").accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(asDTO)).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		
		Assignment as = assignmentRepository.findById(3).get();
		assertEquals(as.getName().contentEquals("Assignment 3"),true);

	}
	
	@Test
	public void deleteAssignment() throws Exception {
		MockHttpServletResponse response;

		response = mvc.perform(MockMvcRequestBuilders.delete("/assignment/2?force=true").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertEquals(200, response.getStatus());
		
		Assignment as = assignmentRepository.findById(2).orElse(null);
		// if {as} is null then the id was deleted
		assertEquals(as, null);

	}
	
	

	private static String asJsonString(final Object obj) {
		try {

			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T fromJsonString(String str, Class<T> valueType) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
