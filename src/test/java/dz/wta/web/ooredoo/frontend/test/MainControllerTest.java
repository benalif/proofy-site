package dz.wta.web.ooredoo.frontend.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import dz.wta.web.ooredoo.frontend.config.WebConfig;



@WebAppConfiguration
@ActiveProfiles("uat")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
public class MainControllerTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testGetPageNotFound() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/NoPage/");
		request.accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
		request.contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(request).andExpect(status().isNotFound());
	}

	@Test
	public void testMain() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/");
		this.mockMvc.perform(request).andExpect(status().isOk());
	}
}
