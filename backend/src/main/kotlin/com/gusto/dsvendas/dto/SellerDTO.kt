package com.gusto.dsvendas.dto

import com.gusto.dsvendas.entities.Seller
import java.io.Serializable

class SellerDTO(private val id: Long, private val name: String): Serializable {
    constructor(seller: Seller) : this(seller.id, seller.name)
}