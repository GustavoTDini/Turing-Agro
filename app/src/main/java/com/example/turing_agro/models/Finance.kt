package com.example.turing_agro.models

import java.io.Serializable

class Finance (
    val nome: String,
    val quantidade: Int,
    val previsto: Float,
    var real: Float
) : Serializable {

}



