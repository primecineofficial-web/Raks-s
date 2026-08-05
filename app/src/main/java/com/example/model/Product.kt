package com.example.model

data class Product(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val weightUnit: String,
    val priceRupees: Int,
    val mrpRupees: Int,
    val deliveryTimeMins: Int = 9,
    val imageResId: Int? = null,
    val description: String = "Fresh, high quality grocery item delivered to your door in minutes (simulation).",
    val rating: Double = 4.8,
    val isBestseller: Boolean = false,
    val discountPercent: Int = if (mrpRupees > priceRupees) (((mrpRupees - priceRupees).toDouble() / mrpRupees) * 100).toInt() else 0
)

data class CartItem(
    val product: Product,
    val quantity: Int
)

data class CategoryItem(
    val id: String,
    val name: String,
    val iconName: String,
    val count: String
)
