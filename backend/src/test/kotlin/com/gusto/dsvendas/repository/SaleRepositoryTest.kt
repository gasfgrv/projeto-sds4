package com.gusto.dsvendas.repository

import com.gusto.dsvendas.repositories.SaleRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataJpaTest
@ExtendWith(SpringExtension::class)
class SaleRepositoryTest {

    @Autowired
    private lateinit var saleRepository: SaleRepository

    @Test
    fun `Deve listar todas as vendas`() {
        val pageable = PageRequest.of(0, 1)
        val list = saleRepository.findAll(pageable)
        assertNotNull(list)
        assertEquals(1, list.size)
        assertEquals(5, list.content[0].seller.id)
    }

    @Test
    fun `Deve listar todas as vendas por vendendor`() {
        val list = saleRepository.amountGroupedBySeller()
        assertNotNull(list)
        assertEquals(5, list.size)
        assertEquals(220426.0, list[0].sum)
    }

    @Test
    fun `Deve listar o percentual de sucesso de vendas por vendendor`() {
        val list = saleRepository.successGroupedBySeller()
        assertNotNull(list)
        assertEquals(5, list.size)
        assertEquals(1495, list[0].visited)
        assertEquals(684, list[0].deals)
    }

}