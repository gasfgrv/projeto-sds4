package com.gusto.dsvendas.controllers

import com.gusto.dsvendas.dto.SaleDTO
import com.gusto.dsvendas.dto.SaleSuccessDTO
import com.gusto.dsvendas.dto.SaleSumDTO
import com.gusto.dsvendas.dto.SellerDTO
import com.gusto.dsvendas.entities.Seller
import com.gusto.dsvendas.services.SaleService
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
internal class SaleControllerTest {

    @Mock
    private lateinit var saleService: SaleService

    @InjectMocks
    private lateinit var saleController: SaleController

    @Autowired
    private var mockMvc: MockMvc? = null

    private var saleList: List<SaleDTO>? = null
    private var saleSumList: List<SaleSumDTO>? = null
    private var saleSuccessList: List<SaleSuccessDTO>? = null

    private var pageable: Pageable? = null
    private var page: Page<SaleDTO>? = null

    @BeforeEach
    fun setUp() {
        val seller = Seller(1, "Logan", emptyList())

        val saleDTO = SaleDTO(
            id = 1L,
            visited = 83,
            deals = 66,
            amount = 5501.0,
            date = LocalDate.of(2021, 4, 1),
            seller = SellerDTO(seller)
        )

        val saleSumDTO = SaleSumDTO(
            seller = seller,
            sum = 220426.0
        )

        val saleSuccessDTO = SaleSuccessDTO(
            seller = seller,
            visited = 1495,
            deals = 684
        )

        saleList = listOf(saleDTO)
        saleSumList = listOf(saleSumDTO)
        saleSuccessList = listOf(saleSuccessDTO)

        pageable = PageRequest.of(0, 1)
        page = PageImpl(saleList!!, pageable!!, 1)

        mockMvc = MockMvcBuilders.standaloneSetup(saleController)
            .setCustomArgumentResolvers(PageableHandlerMethodArgumentResolver())
            .build()
    }

    @AfterEach
    fun tearDown() {
        saleList = null
        saleSumList = null
        saleSuccessList = null

        pageable = null
        page = null

        mockMvc = null
    }

    @Test
    fun `Deve retornar 200 quando buscar todas as vendas`() {
        `when`(saleService.findAll(pageable!!)).thenReturn(page)

        val requestParams = LinkedMultiValueMap<String, String>()
        requestParams.add("page", "0")
        requestParams.add("size", "1")

        mockMvc!!.perform(get("/sales").params(requestParams).contentType(APPLICATION_JSON))
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


    @Test
    fun `Deve retorntar 200 quando buscar todas as vendas por vendendor`() {
        `when`(saleService.amountGroupedBySeller()).thenReturn(saleSumList)

        mockMvc!!.perform(get("/sales/amount-by-seller"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.[0].sellerName", equalTo("Logan")))
            .andExpect(jsonPath("$", notNullValue()))

        verify(saleService).amountGroupedBySeller()
        verify(saleService, times(1)).amountGroupedBySeller()
    }


    @Test
    fun `Deve retorntar 200 quando buscar o percentual de sucesso de vendas por vendendor`() {
        `when`(saleService.successGroupedBySeller()).thenReturn(saleSuccessList)

        mockMvc!!.perform(get("/sales/success-by-seller"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.[0].sellerName", equalTo("Logan")))
            .andExpect(jsonPath("$", notNullValue()))

        verify(saleService).successGroupedBySeller()
        verify(saleService, times(1)).successGroupedBySeller()
    }

}