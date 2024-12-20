package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@DisplayName("GitHub Authentication Integration Tests")
class GitHubAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAllowLoginWithGitHubOAuth2() throws Exception {
        mockMvc.perform(get("/api/v1/products")
                        .with(oauth2Login()
                                .attributes(attrs -> {
                                    attrs.put("name", "mock_github_user");
                                    attrs.put("email", "mock_user@example.com");
                                })
                                .authorities(() -> "ROLE_USER"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String contentType = result.getResponse().getContentType();
                    assert contentType != null && contentType.equals(MediaType.APPLICATION_JSON_VALUE);
                })
                .andExpect(result -> {
                    String body = result.getResponse().getContentAsString();
                    assert !body.isEmpty();
                });
    }
}
