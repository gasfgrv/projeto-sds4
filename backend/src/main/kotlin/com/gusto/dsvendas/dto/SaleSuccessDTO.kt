package com.gusto.dsvendas.dto

import com.gusto.dsvendas.entities.Seller
import java.io.Serializable

class SaleSuccessDTO(
    seller: Seller,
    val visited: Long,
    val deals: Long
) : Serializable {
    val sellerName: String = seller.name
}