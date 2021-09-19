package com.gusto.dsvendas.repositories

import com.gusto.dsvendas.dto.SaleSuccessDTO
import com.gusto.dsvendas.dto.SaleSumDTO
import com.gusto.dsvendas.entities.Sale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SaleRepository : JpaRepository<Sale, Long> {

    @Query(
        "SELECT new com.gusto.dsvendas.dto.SaleSumDTO(obj.seller, SUM(obj.amount)) " +
        "FROM Sale AS obj " +
        "GROUP BY obj.seller"
    )
    fun amountGroupedBySeller(): List<SaleSumDTO>

    @Query(
        "SELECT new com.gusto.dsvendas.dto.SaleSuccessDTO(obj.seller, SUM(obj.visited), SUM(obj.deals)) " +
        "FROM Sale AS obj " +
        "GROUP BY obj.seller"
    )
    fun successGroupedBySeller(): List<SaleSuccessDTO>
}