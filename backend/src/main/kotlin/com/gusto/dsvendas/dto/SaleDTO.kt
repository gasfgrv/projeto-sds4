package com.gusto.dsvendas.dto

import com.gusto.dsvendas.entities.Sale
import com.gusto.dsvendas.entities.Seller
import java.time.LocalDate

class SaleDTO(
    val id: Long,
    val visited: Int,
    val deals: Int,
    val amount: Double,
    val date: LocalDate,
    val seller: SellerDTO
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