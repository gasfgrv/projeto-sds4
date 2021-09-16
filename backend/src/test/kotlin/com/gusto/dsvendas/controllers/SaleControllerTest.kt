package com.gusto.dsvendas.controllers

import com.gusto.dsvendas.dto.SellerDTO
import com.gusto.dsvendas.services.SaleService
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class SaleControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var saleService: SaleService


    //findAll
    @Test
    @Throws(Exception::class)
    fun `Deve retornar 200 quando buscar todas as vendas`() {
        Mockito.`when`(saleService.findAll(PageRequest.of(0, 1)))
            .thenReturn(Page.empty())

        mockMvc.perform(MockMvcRequestBuilders.get("/sales").param("page", "0").param("size", "1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.equalTo(1)))
    }
//amountGroupedBySeller
    //successGroupedBySeller

}