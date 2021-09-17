package com.gusto.dsvendas.controllers

import com.gusto.dsvendas.dto.SaleDTO
import com.gusto.dsvendas.dto.SellerDTO
import com.gusto.dsvendas.services.SaleService
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate


@ExtendWith(SpringExtension::class)
@WebMvcTest(SaleController::class)
internal class SaleControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var saleService: SaleService

    //findAll
    @Test
    @Throws(Exception::class)
    fun `Deve retornar 200 quando buscar todas as vendas`() {
        val seller = SellerDTO(1, "Logan")
        val date = LocalDate.of(2021, 4, 1)
        val sale = SaleDTO(1L, 83, 66, 5501.0, date, seller)
        val pageable = PageRequest.of(0, 1)
        val page = PageImpl(mutableListOf(sale), pageable, 1)

        `when`(saleService.findAll(pageable)).thenReturn(page)

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/sales")
                .param("page", "0")
                .param("size", "1")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.content.[0].id", equalTo(1)))
            .andExpect(jsonPath("$.totalPages", equalTo(1)))
            .andExpect(jsonPath("$.totalElements", equalTo(1)))
            .andExpect(jsonPath("$.last", equalTo(true)))
            .andExpect(jsonPath("$.number", equalTo(0)))
            .andExpect(jsonPath("$.size", equalTo(1)))
            .andExpect(jsonPath("$.numberOfElements", equalTo(1)))
            .andExpect(jsonPath("$.first", equalTo(true)))
            .andExpect(jsonPath("$.empty", equalTo(false)))
    }

    //amountGroupedBySeller
    //successGroupedBySeller

}