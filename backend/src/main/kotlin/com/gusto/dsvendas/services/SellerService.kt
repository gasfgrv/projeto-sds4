package com.gusto.dsvendas.services

import com.gusto.dsvendas.dto.SellerDTO
import com.gusto.dsvendas.entities.Seller
import com.gusto.dsvendas.repositories.SellerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class SellerService @Autowired constructor(private val sellerRepository: SellerRepository) {

    fun findAll(): List<SellerDTO> = sellerRepository.findAll()
        .stream().map { seller: Seller -> SellerDTO(seller) }
        .collect(Collectors.toList())

}