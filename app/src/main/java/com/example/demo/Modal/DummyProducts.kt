package com.example.demo.Modal

data class DummyProducts(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)