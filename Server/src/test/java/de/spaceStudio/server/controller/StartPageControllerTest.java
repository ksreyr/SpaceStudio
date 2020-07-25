package de.spaceStudio.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import static de.spaceStudio.server.utils.ServerUrlsPath.HOME;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Junit Test for StartPageController with SpringBootTest
 * REST API test
 *
 * @author Miguel Caceres
 * created on 2020/06/15
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StartPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testStartPage() throws Exception {
        this.mockMvc.perform(get(HOME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Spacestudio Server is running...")));
    }

}
