package com.example.prueba

data class Pescado(
    var idPez: Int,
    var nombre: String? = null,
    var espinado: Boolean,
    var tipo: String? = null,
    var imageUrl: String? = null
)