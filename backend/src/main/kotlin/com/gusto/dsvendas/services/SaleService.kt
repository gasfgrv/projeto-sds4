package com.gusto.dsvendas.services

import com.gusto.dsvendas.dto.SaleDTO
import com.gusto.dsvendas.dto.SaleSuccessDTO
import com.gusto.dsvendas.dto.SaleSumDTO
import com.gusto.dsvendas.entities.Sale
import com.gusto.dsvendas.repositories.SaleRepository
import com.gusto.dsvendas.repositories.SellerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaleService @Autowired constructor(
    private val saleRepository: SaleRepository,
    private val sellerRepository: SellerRepository
) {

    @Transactional(readOnly = true)
    fun findAll(page: Pageable): Page<SaleDTO> {
        sellerRepository.findAll()
        return saleRepository.findAll(page).map { sale: Sale -> SaleDTO(sale) }
    }

    @Transactional(readOnly = true)
    fun amountGroupedBySeller(): List<SaleSumDTO> = saleRepository.amountGroupedBySeller()

    @Transactional(readOnly = true)
    fun successGroupedBySeller(): List<SaleSuccessDTO> = saleRepository.successGroupedBySeller()

}