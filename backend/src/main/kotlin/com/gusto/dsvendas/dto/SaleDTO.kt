package com.gusto.dsvendas.dto

import com.gusto.dsvendas.entities.Sale
import com.gusto.dsvendas.entities.Seller
import java.time.LocalDate

class SaleDTO(
    private val id: Long,
    private val visited: Int,
    private val deals: Int,
    private val amount: Double,
    private val date: LocalDate,
    private val seller: SellerDTO
) {
    constructor(sale: Sale) : this(
        sale.id,
        sale.visited,
        sale.deals,
        sale.amount,
        sale.date,
        SellerDTO(sale.seller)
    )
}