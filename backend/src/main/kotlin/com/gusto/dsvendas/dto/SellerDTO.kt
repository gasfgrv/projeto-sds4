package com.gusto.dsvendas.dto

import com.gusto.dsvendas.entities.Seller
import java.io.Serializable

class SellerDTO(
    val id: Long,
    val name: String
) : Serializable {
    constructor(seller: Seller) : this(seller.id, seller.name)
}