package com.gusto.dsvendas.services

import com.gusto.dsvendas.dto.SellerDTO
import com.gusto.dsvendas.entities.Seller
import com.gusto.dsvendas.repositories.SellerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired

@ExtendWith(MockitoExtension::class)
internal class SellerServiceTest {

    @Mock
    private lateinit var sellerRepository: SellerRepository

    @Autowired
    @InjectMocks
    private lateinit var sellerService: SellerService

    private var sellerDTO: SellerDTO? = null
    private var sellerList: List<SellerDTO>? = null

    @BeforeEach
    fun setUp() {
        sellerDTO = SellerDTO(0, "Teste")
        sellerList = listOf(sellerDTO!!)
    }

    @AfterEach
    fun tearDown() {
        sellerRepository.delete(Seller(sellerDTO!!.id, sellerDTO!!.name, emptyList()))
        sellerDTO = null
        sellerList = null
    }

    @Test
    fun `Deve retornar a lista de vendedores`() {
        val seller = Seller(0, "Teste", emptyList())
        sellerRepository.save(seller)

        `when`(sellerRepository.findAll()).thenReturn(listOf(seller))

        val returnList = sellerService.findAll()
        val lastItem = returnList[returnList.lastIndex]

        assertNotEquals(sellerList, returnList)
        assertEquals(seller.id, lastItem.id)
        assertEquals(seller.name, lastItem.name)

        verify(sellerRepository).findAll()
        verify(sellerRepository, times(1)).findAll()
    }
}