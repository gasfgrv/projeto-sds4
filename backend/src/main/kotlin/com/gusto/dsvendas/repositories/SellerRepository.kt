package com.gusto.dsvendas.repositories

import com.gusto.dsvendas.entities.Seller
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SellerRepository : JpaRepository<Seller, Long>