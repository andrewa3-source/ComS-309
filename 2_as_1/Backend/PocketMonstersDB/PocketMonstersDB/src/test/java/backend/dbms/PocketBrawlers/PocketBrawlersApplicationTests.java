package backend.dbms.PocketBrawlers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.Condition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Andrew Ahrenkiel & Reid Coates
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class PocketBrawlersApplicationTests {

	@LocalServerPort
	int port;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testMyRequest() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/getShopBrawler"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		JSONObject jsonObject = new JSONObject(responseBody);

		assertEquals(true, jsonObject.has("id"));
		assertEquals(true, jsonObject.has("health"));
		assertEquals(true, jsonObject.has("damage"));
		assertEquals(true, jsonObject.has("url"));
		assertEquals(true, jsonObject.has("position"));
		assertEquals(true, jsonObject.has("a_id"));
		assertEquals(true, jsonObject.has("a_time"));


	}

}



