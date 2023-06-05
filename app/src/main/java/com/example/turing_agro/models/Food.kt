package com.example.turing_agro.models

import java.io.Serializable

class Food (
    val alimento: String,
    val imagem: Int,
    val consumo: Int,
    val venda: Int,
) : Serializable {

}