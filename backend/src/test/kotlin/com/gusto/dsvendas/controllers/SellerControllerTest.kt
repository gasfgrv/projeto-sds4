package com.gusto.dsvendas.controllers

import com.gusto.dsvendas.dto.SellerDTO
import com.gusto.dsvendas.services.SellerService
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@ExtendWith(SpringExtension::class)
@WebMvcTest(SellerController::class)
internal class SellerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var sellerService: SellerService

    @Test
    @Throws(Exception::class)
    fun `Deve retornar 200 quando todos os vendedores estiverem cadastrados`() {
        val seller = SellerDTO(1, "Logan")
        `when`(sellerService.findAll()).thenReturn(listOf(seller))

        mockMvc.perform(get("/sellers"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name", `is`("Logan")))
    }
}