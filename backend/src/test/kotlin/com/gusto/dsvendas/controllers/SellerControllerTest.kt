package com.gusto.dsvendas.controllers

import com.gusto.dsvendas.dto.SellerDTO
import com.gusto.dsvendas.services.SellerService
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockitoExtension::class)
internal class SellerControllerTest {

    @Mock
    private lateinit var sellerService: SellerService

    private var sellerDTO: SellerDTO? = null
    private var sellerList: List<SellerDTO>? = null

    @InjectMocks
    private lateinit var sellerController: SellerController

    @Autowired
    private var mockMvc: MockMvc? = null

    @BeforeEach
    fun setUp() {
        sellerDTO = SellerDTO(1, "Logan")
        sellerList = listOf(sellerDTO!!)
        mockMvc = MockMvcBuilders.standaloneSetup(sellerController).build()
    }

    @AfterEach
    fun tearDown() {
        sellerDTO = null
        sellerList = null
        mockMvc = null
    }

    @Test
    fun `Deve retornar 200 quando todos os vendedores estiverem cadastrados`() {
        `when`(sellerService.findAll()).thenReturn(sellerList)
        mockMvc!!.perform(get("/sellers"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name", `is`("Logan")))
    }
}