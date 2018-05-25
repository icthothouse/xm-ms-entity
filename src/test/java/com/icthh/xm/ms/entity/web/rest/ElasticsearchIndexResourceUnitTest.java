package com.icthh.xm.ms.entity.web.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.icthh.xm.ms.entity.service.ElasticsearchIndexService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ElasticsearchIndexResource.class)
@ContextConfiguration(classes={ElasticsearchIndexResource.class})
public class ElasticsearchIndexResourceUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ElasticsearchIndexService service;

    @SneakyThrows
    @Test
    public void testReindexCallsService() {
        mockMvc.perform(post("/api/elasticsearch/index"))
            .andExpect(status().isAccepted());

        verify(service).reindexAll();
    }
}