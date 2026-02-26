package org.nadezhdaPoliaeva.controller;

import org.junit.jupiter.api.Test;
import org.nadezhdaPoliaeva.ItemController;
import org.nadezhdaPoliaeva.model.Item;
import org.nadezhdaPoliaeva.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
class ItemControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    @WithMockUser(username = "nadya")
    void getAll_returnsJson() throws Exception {
        Item a = new Item();
        a.setId(1L);
        a.setName("Apple");
        a.setPrice(10.5);

        when(itemService.getAll()).thenReturn(List.of(a));

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[0].price").value(10.5));

        verify(itemService).getAll();
    }
}