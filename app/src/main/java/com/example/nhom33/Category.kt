package com.example.nhom33

data class Category(
    val id: Int = 0,
    val name: String = "",
    val imageRes: Int = 0 // Nếu lấy từ drawable, hoặc dùng String nếu lấy từ URL
)