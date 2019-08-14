package club.tempvs.profile.controller;

import club.tempvs.profile.dto.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ProfileControllerIntegrationTest {

    private static final String USER_INFO_HEADER = "User-Info";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN = "df41895b9f26094d0b1d39b7bdd9849e"; //security_token as MD5

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testCreateUserProfile() throws Exception {
        File createProfileFile = ResourceUtils.getFile("classpath:profile/create.json");
        String createProfileJson = new String(Files.readAllBytes(createProfileFile.toPath()));
        String userInfoValue = buildUserInfoValue(1L);

        mvc.perform(post("/api/profile")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(createProfileJson)
                .header(USER_INFO_HEADER, userInfoValue)
                .header(AUTHORIZATION_HEADER, TOKEN))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("firstName", is("first name")))
                    .andExpect(jsonPath("lastName", is("last name")))
                    .andExpect(jsonPath("type", is("USER")))
                    .andExpect(jsonPath("location", is("gotham city")));
    }

    @Test
    public void testCreateSecondUserProfile() throws Exception {
        File createProfileFile = ResourceUtils.getFile("classpath:profile/create.json");
        String createProfileJson = new String(Files.readAllBytes(createProfileFile.toPath()));
        String userInfoValue = buildUserInfoValue(1L);

        mvc.perform(post("/api/profile")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(createProfileJson)
                .header(USER_INFO_HEADER, userInfoValue)
                .header(AUTHORIZATION_HEADER, TOKEN));

        mvc.perform(post("/api/profile")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(createProfileJson)
                .header(USER_INFO_HEADER, userInfoValue)
                .header(AUTHORIZATION_HEADER, TOKEN))
                .andExpect(status().isConflict());
    }

    @SneakyThrows
    private String buildUserInfoValue(Long id) {
        UserInfoDto userInfo = new UserInfoDto();
        userInfo.setUserId(id);
        return objectMapper.writeValueAsString(userInfo);
    }
}
