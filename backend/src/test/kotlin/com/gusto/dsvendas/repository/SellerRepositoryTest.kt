package com.gusto.dsvendas.repository

import com.gusto.dsvendas.repositories.SellerRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataJpaTest
@ExtendWith(SpringExtension::class)
class SellerRepositoryTest {

    @Autowired
    private lateinit var sellerRepository: SellerRepository

    @Test
    fun `Deve listar todos os vendedores`() {
        val list = sellerRepository.findAll()
        assertNotNull(list)
        assertEquals(5, list.size)
        assertEquals("Logan", list[0].name)
    }

}