package com.gusto.dsvendas.controllers

import com.gusto.dsvendas.dto.SaleDTO
import com.gusto.dsvendas.dto.SaleSuccessDTO
import com.gusto.dsvendas.dto.SaleSumDTO
import com.gusto.dsvendas.services.SaleService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.HttpURLConnection

@RestController
@RequestMapping("/sales")
class SaleController @Autowired constructor(private val saleService: SaleService) {

    @GetMapping
    @ApiOperation(value = "Listagem de vendas")
    @ApiResponses(
        value = [
            ApiResponse(
                code = HttpURLConnection.HTTP_OK,
                response = Array<SaleDTO>::class,
                message = "Todas as vendas feitas"
            ),
            ApiResponse(
                code = HttpURLConnection.HTTP_NOT_FOUND,
                message = ERRO_404
            )
        ]
    )
    fun findAll(page: Pageable): ResponseEntity<Page<SaleDTO>> = ResponseEntity.ok(saleService.findAll(page))

    @GetMapping("/amount-by-seller")
    @ApiOperation(value = "Total de vendas feitas por cada vendedor")
    @ApiResponses(
        value = [
            ApiResponse(
                code = HttpURLConnection.HTTP_OK,
                response = Array<SaleSumDTO>::class,
                message = "Total de vendas feitas por cada vendedor"
            ),
            ApiResponse(
                code = HttpURLConnection.HTTP_NOT_FOUND,
                message = ERRO_404
            )
        ]
    )
    fun amountGroupedBySeller(): ResponseEntity<List<SaleSumDTO>> =
        ResponseEntity.ok(saleService.amountGroupedBySeller())

    @GetMapping("/success-by-seller")
    @ApiOperation(value = "Percentual de sucesso das vendas feitas por cada vendedor")
    @ApiResponses(
        value = [
            ApiResponse(
                code = HttpURLConnection.HTTP_OK,
                response = Array<SaleSuccessDTO>::class,
                message = "Percentual de sucesso das vendas feitas por cada vendedor"
            ),
            ApiResponse(
                code = HttpURLConnection.HTTP_NOT_FOUND,
                message = ERRO_404
            )
        ]
    )
    fun successGroupedBySeller(): ResponseEntity<List<SaleSuccessDTO>> =
        ResponseEntity.ok(saleService.successGroupedBySeller())

    companion object {
        const val ERRO_404 = "Nenhum dado foi encontrado"
    }
}