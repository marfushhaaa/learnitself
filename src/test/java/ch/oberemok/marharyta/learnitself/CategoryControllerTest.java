package ch.oberemok.marharyta.learnitself;

import ch.oberemok.marharyta.learnitself.category.Category;
import ch.oberemok.marharyta.learnitself.category.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.AutoConfigureDataJpa;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryControllerTest {

    @Autowired
    private MockMvc api;

    @Autowired
    private CategoryRepository categoryRepository;
    private Long createdCategoryId;

    @BeforeAll
    void setup() {
        categoryRepository.save(new Category("Music"));
        categoryRepository.save(new Category("Mathematics"));
    }


    // GET all categories
    @Test
    @Order(1)
    void testGetAllCategories() throws Exception {
        String accessToken = obtainAccessToken(); //get token

        api.perform(get("/api/categories")
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print()) //shows everything in the console, could be removed
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Music")))
                .andExpect(content().string(containsString("Mathematics")));
    }



    // GET one category
    @Test
    @Order(2)
    void testGetOneCategory() throws Exception {
        String accessToken = obtainAccessToken();
        Long id = categoryRepository.findAll().getFirst().getId();

        api.perform(get("/api/categories/" + id)
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Music")));
    }

    // GET not existing - 404
    @Test
    @Order(3)
    void testGetOneCategoryNotFound() throws Exception {
        String accessToken = obtainAccessToken();

        api.perform(get("/api/categories/234213")
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // POST new category
    @Test
    @Order(4)
    void testCreateCategory() throws Exception {
        String accessToken = obtainAccessToken();

        Category category = new Category("Sports");
        String body = new ObjectMapper().writeValueAsString(category);

        String response = api.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Sports")))
                .andReturn().getResponse().getContentAsString();

        // Test category for delete test
        createdCategoryId = new ObjectMapper()
                .readTree(response)
                .get("id")
                .asLong();
    }

    // POST not valid category - 400
    @Test
    @Order(5)
    void testCreateCategoryInvalid() throws Exception {
        String accessToken = obtainAccessToken();

        Category category = new Category("");
        String body = new ObjectMapper().writeValueAsString(category);

        api.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // DELETE category
    @Test
    @Order(6)
    void testDeleteCategory() throws Exception {
        String accessToken = obtainAccessToken();

        api.perform(delete("/api/categories/" + createdCategoryId)
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }


    private String obtainAccessToken() {

        RestTemplate rest = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=learnitself&" +
                "grant_type=password&" +
                "scope=openid profile roles offline_access&" +
                "username=admin&" +
                "password=admin";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> resp = rest.postForEntity("http://localhost:8080/realms/learnitself/protocol/openid-connect/token", entity, String.class);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resp.getBody()).get("access_token").toString();
    }
}
