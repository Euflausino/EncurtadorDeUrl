package com.euflausino.encurtaurl.adapter.input;


import com.euflausino.encurtaurl.application.ports.input.ICreateInput;
import com.euflausino.encurtaurl.application.ports.input.IRedirectInput;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICreateInput createInput;

    @MockitoBean
    private IRedirectInput redirectInput;

    @Test
    void deveCriarShortUrl() throws Exception {
        Mockito.when(createInput.createShortUrl("https://google.com"))
                .thenReturn("abc123");

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/url")
                                .param("originalUrl", "https://google.com")
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("abc123"));

        Mockito.verify(createInput).createShortUrl("https://google.com");
    }

    @Test
    void deveRedirecionarParaUrlOriginal() throws Exception {
        Mockito.when(redirectInput.redirectShortUrl("abc123"))
                .thenReturn("https://google.com");

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/url/abc123")
                )
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://google.com"));

        Mockito.verify(redirectInput).redirectShortUrl("abc123");
    }
    @Test
    void deveRetornarErroQuandoParametroNaoInformado() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/url")
                )
                .andExpect(status().isBadRequest());
    }

}
