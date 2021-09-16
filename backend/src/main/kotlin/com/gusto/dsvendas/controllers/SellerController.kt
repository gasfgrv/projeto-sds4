package com.gusto.dsvendas.controllers

import com.gusto.dsvendas.dto.SellerDTO
import com.gusto.dsvendas.services.SellerService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.HttpURLConnection

@RestController
@RequestMapping("/sellers")
class SellerController @Autowired constructor(private val sellerService: SellerService) {

    @GetMapping
    @ApiOperation(value = "Listagem de vendedores")
    @ApiResponses(
        value = [
            ApiResponse(
                code = HttpURLConnection.HTTP_OK,
                response = Array<SellerDTO>::class,
                message = "Todos os vendedores cadastrados"
            ),
            ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Nenhum dado foi encontrado")
        ]
    )
    fun findAll(): ResponseEntity<List<SellerDTO>> {
        return ResponseEntity.ok(sellerService.findAll())
    }

}