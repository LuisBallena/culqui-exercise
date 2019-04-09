package com.scrap.culqui.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.scrap.culqui.api.dto.ResultDTO;
import com.scrap.culqui.api.dto.TokenDTO;
import com.scrap.culqui.exception.ExceptionResponse;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TokenApiTest.
 *
 * @author Luis Alonso Ballena Garcia
 */

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
@AutoConfigureMockMvc
public class TokenApiTest {

    public static WireMockRule wireMockRule = new WireMockRule(9090);

    @BeforeClass
    public static void setUp() throws Exception {
        wireMockRule.start();
    }

    @AfterClass
    public static void down() throws Exception {
        wireMockRule.stop();
    }

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldGenerateToken() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setPan("4444333322221111");
        tokenDTO.setExpYear(2018);
        tokenDTO.setExpMonth(1);

        wireMockRule.stubFor(WireMock.get(WireMock.urlPathEqualTo("/444433"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"scheme\":\"mastercard\"}")));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(tokenDTO)))
                .andExpect(status().isOk())
                .andReturn();

        ResultDTO resultDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsByteArray(), ResultDTO.class);

        Assert.assertEquals("tkn_live_4444333322221111-2018-1", resultDTO.getToken());
        Assert.assertEquals("mastercard", resultDTO.getBrand());
    }

    @Test
    public void shouldValidNotBlankPan() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        TokenDTO tokenDTO = new TokenDTO();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(tokenDTO)))
                .andExpect(status().is4xxClientError())
                .andReturn();

        ExceptionResponse exceptionResponse = objectMapper
                .readValue(mvcResult.getResponse().getContentAsByteArray(), ExceptionResponse.class);

        Assert.assertEquals("the parameter 'pan' not be blank or empty", exceptionResponse.getMessage());
    }

    @Test
    public void shouldValidMinPan() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setPan("1111");
        tokenDTO.setExpYear(2018);
        tokenDTO.setExpMonth(01);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(tokenDTO)))
                .andExpect(status().is4xxClientError())
                .andReturn();

        ExceptionResponse exceptionResponse = objectMapper
                .readValue(mvcResult.getResponse().getContentAsByteArray(), ExceptionResponse.class);

        Assert.assertEquals("the parameter 'pan' must be at least 6 characters", exceptionResponse.getMessage());
    }

    @Test
    public void shouldValidNotExistCardInformation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setPan("1111111111111111");
        tokenDTO.setExpYear(2018);
        tokenDTO.setExpMonth(01);

        wireMockRule.stubFor(WireMock.get(WireMock.urlPathEqualTo("/444433"))
                .willReturn(WireMock.aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(tokenDTO)))
                .andExpect(status().is5xxServerError())
                .andReturn();

        ExceptionResponse exceptionResponse = objectMapper
                .readValue(mvcResult.getResponse().getContentAsByteArray(), ExceptionResponse.class);

        Assert.assertEquals("is not obtained card information", exceptionResponse.getMessage());
    }


}
