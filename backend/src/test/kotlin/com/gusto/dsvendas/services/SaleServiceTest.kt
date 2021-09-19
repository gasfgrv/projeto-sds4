package com.gusto.dsvendas.services

import com.gusto.dsvendas.dto.SaleDTO
import com.gusto.dsvendas.dto.SaleSuccessDTO
import com.gusto.dsvendas.dto.SaleSumDTO
import com.gusto.dsvendas.dto.SellerDTO
import com.gusto.dsvendas.entities.Sale
import com.gusto.dsvendas.entities.Seller
import com.gusto.dsvendas.repositories.SaleRepository
import com.gusto.dsvendas.repositories.SellerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
internal class SaleServiceTest {

    @Mock
    private lateinit var saleRepository: SaleRepository

    @Mock
    private lateinit var sellerRepository: SellerRepository

    @Autowired
    @InjectMocks
    private lateinit var saleService: SaleService

    private var pageable: Pageable? = null
    private var page: Page<SaleDTO>? = null

    private var seller: Seller? = null
    private var sale: Sale? = null
    private var saleDTO: SaleDTO? = null

    private var saleList: List<SaleDTO>? = null
    private var saleSumList: List<SaleSumDTO>? = null
    private var saleSuccessList: List<SaleSuccessDTO>? = null

    @BeforeEach
    fun setUp() {
        seller = Seller(1, "Logan", emptyList())

        saleDTO = SaleDTO(
            id = 1L,
            visited = 83,
            deals = 66,
            amount = 5501.0,
            date = LocalDate.of(2021, 4, 1),
            seller = SellerDTO(seller!!)
        )

        sale = Sale(
            id = saleDTO!!.id,
            visited = saleDTO!!.visited,
            deals = saleDTO!!.deals,
            amount = saleDTO!!.amount,
            date = saleDTO!!.date,
            seller = seller!!
        )

        val saleSumDTO = SaleSumDTO(
            seller = seller!!,
            sum = 220426.0
        )

        val saleSuccessDTO = SaleSuccessDTO(
            seller = seller!!,
            visited = 1495,
            deals = 684
        )

        saleList = listOf(saleDTO!!)
        saleSumList = listOf(saleSumDTO)
        saleSuccessList = listOf(saleSuccessDTO)

        pageable = PageRequest.of(0, 1)
        page = PageImpl(saleList!!, pageable!!, 1)
    }

    @AfterEach
    fun tearDown() {
        saleRepository.delete(sale!!)
        sellerRepository.delete(seller!!)

        sale = null
        saleDTO = null

        seller = null

        saleList = null
        saleSumList = null
        saleSuccessList = null

        pageable = null
        page = null
    }

    @Test
    fun `Deve retornar a lista de vendas`() {
        sellerRepository.save(seller!!)

        saleRepository.save(sale!!)

        Mockito.`when`(sellerRepository.findAll()).thenReturn(listOf(seller))

        val salePage = PageImpl(listOf(sale), pageable!!, 1)
        Mockito.`when`(saleRepository.findAll(pageable!!)).thenReturn(salePage)

        val repositoryReturn = saleRepository.findAll(pageable!!).map(::SaleDTO)
        val repositoryLastItem = repositoryReturn.content[repositoryReturn.content.lastIndex]

        val returnList = saleService.findAll(pageable!!)
        val lastItem = returnList.content[returnList.content.lastIndex]

        assertEquals(repositoryReturn.content.size, returnList.content.size)
        assertEquals(repositoryLastItem.id, lastItem.id)
        assertEquals(repositoryLastItem.visited, lastItem.visited)
        assertEquals(repositoryLastItem.deals, lastItem.deals)
        assertEquals(repositoryLastItem.amount, lastItem.amount)
        assertEquals(repositoryLastItem.date, lastItem.date)
        assertEquals(repositoryLastItem.seller.id, lastItem.seller.id)
        assertEquals(repositoryLastItem.seller.name, lastItem.seller.name)

        Mockito.verify(sellerRepository).findAll()
        Mockito.verify(sellerRepository, times(1)).findAll()
    }

    @Test
    fun `Deve retornar o total de vendas por vendedor`() {
        Mockito.`when`(saleRepository.amountGroupedBySeller()).thenReturn(saleSumList)

        val returnList = saleService.amountGroupedBySeller()
        val firstItem = returnList[0]

        assertEquals(saleSumList, returnList)
        assertEquals(220426.0, firstItem.sum)
        assertEquals("Logan", firstItem.sellerName)

        Mockito.verify(saleRepository).amountGroupedBySeller()
        Mockito.verify(saleRepository, times(1)).amountGroupedBySeller()
    }

    @Test
    fun `Deve retornar o percentual de sucesso de cada vendedor`() {
        Mockito.`when`(saleRepository.successGroupedBySeller()).thenReturn(saleSuccessList)

        val returnList = saleService.successGroupedBySeller()
        val firstItem = returnList[0]

        assertEquals(saleSuccessList, returnList)
        assertEquals(1495, firstItem.visited)
        assertEquals(684, firstItem.deals)
        assertEquals("Logan", firstItem.sellerName)

        Mockito.verify(saleRepository).successGroupedBySeller()
        Mockito.verify(saleRepository, times(1)).successGroupedBySeller()
    }
}