package com.example.turing_agro.models

import java.io.Serializable

/**
 * A placeholder item representing a piece of content.
 */
class Supply(
    val fornecedor: String,
    val insumo: String,
    val imagem: Int,
    val area: String,
    val quantidade: Int
) : Serializable {

}