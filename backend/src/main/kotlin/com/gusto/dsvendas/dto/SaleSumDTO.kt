package com.gusto.dsvendas.dto

import com.gusto.dsvendas.entities.Seller
import java.io.Serializable

class SaleSumDTO constructor(seller: Seller, val sum: Double): Serializable {
    val sellerName: String = seller.name
}
