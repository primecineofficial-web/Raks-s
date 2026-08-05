package com.example.data

import com.example.model.CategoryItem
import com.example.model.Product

object GroceryData {

    val categories = listOf(
        CategoryItem("all", "All", "widgets", "120+ items"),
        CategoryItem("grocery", "Grocery", "shopping_bag", "45 items"),
        CategoryItem("vegetables", "Vegetables", "eco", "28 items"),
        CategoryItem("fruits", "Fruits", "nutrition", "20 items"),
        CategoryItem("snacks", "Snacks", "fastfood", "35 items"),
        CategoryItem("namkeen", "Namkeen", "bakery_dining", "18 items"),
        CategoryItem("atta_rice", "Atta & Rice", "grain", "22 items"),
        CategoryItem("oil_masala", "Oil & Masala", "soup_kitchen", "30 items"),
        CategoryItem("dairy", "Dairy", "local_drink", "15 items"),
        CategoryItem("beverages", "Beverages", "local_bar", "25 items"),
        CategoryItem("personal_care", "Personal Care", "sanitizer", "40 items")
    )

    val products = listOf(
        // Prompt specific items
        Product(
            id = "p1",
            name = "Aashirvaad Shudh Chakki Atta",
            brand = "Aashirvaad",
            category = "Atta & Rice",
            weightUnit = "5 kg",
            priceRupees = 250,
            mrpRupees = 280,
            deliveryTimeMins = 9,
            description = "100% pure whole wheat grain flour with rich natural dietary fiber. Processed with modern chakki technique for soft roti.",
            rating = 4.9,
            isBestseller = true
        ),
        Product(
            id = "p2",
            name = "Fortune Special Basmati Rice",
            brand = "Fortune",
            category = "Atta & Rice",
            weightUnit = "1 kg",
            priceRupees = 120,
            mrpRupees = 150,
            deliveryTimeMins = 9,
            description = "Long grain aromatic basmati rice aged to perfection. Ideal for royal biryani and daily meals.",
            rating = 4.7,
            isBestseller = true
        ),
        Product(
            id = "p3",
            name = "Fresh Hybrid Tomatoes",
            brand = "Farm Fresh",
            category = "Vegetables",
            weightUnit = "1 kg",
            priceRupees = 40,
            mrpRupees = 50,
            deliveryTimeMins = 9,
            description = "Handpicked farm-fresh red ripe tomatoes rich in Lycopene and Vitamin C. Sourced daily.",
            rating = 4.8,
            isBestseller = true
        ),
        Product(
            id = "p4",
            name = "Fresh Organic Potatoes",
            brand = "Farm Fresh",
            category = "Vegetables",
            weightUnit = "1 kg",
            priceRupees = 35,
            mrpRupees = 45,
            deliveryTimeMins = 9,
            description = "Firm, smooth farm potatoes ideal for boiling, baking, or crispy fries. Minimal pesticide usage.",
            rating = 4.6
        ),
        Product(
            id = "p5",
            name = "Lay's India's Magic Masala Chips",
            brand = "Lay's",
            category = "Snacks",
            weightUnit = "70 g",
            priceRupees = 20,
            mrpRupees = 20,
            deliveryTimeMins = 9,
            description = "Crispy potato chips tossed in spicy Indian aromatic spices. The ultimate quick crunch.",
            rating = 4.9,
            isBestseller = true
        ),
        Product(
            id = "p6",
            name = "Haldiram's Aloo Bhujia",
            brand = "Haldiram's",
            category = "Namkeen",
            weightUnit = "200 g",
            priceRupees = 60,
            mrpRupees = 70,
            deliveryTimeMins = 9,
            description = "Classic spicy potato wire noodles blended with tepary bean flour and traditional Rajasthani spices.",
            rating = 4.8,
            isBestseller = true
        ),
        Product(
            id = "p7",
            name = "Amul Taaza Toned Milk",
            brand = "Amul",
            category = "Dairy",
            weightUnit = "1 L",
            priceRupees = 70,
            mrpRupees = 75,
            deliveryTimeMins = 9,
            description = "Pasteurized double toned fresh milk packed with calcium, protein, and essential nutrients.",
            rating = 4.9,
            isBestseller = true
        ),
        Product(
            id = "p8",
            name = "Coca-Cola Original Taste Drink",
            brand = "Coca-Cola",
            category = "Beverages",
            weightUnit = "750 ml",
            priceRupees = 40,
            mrpRupees = 45,
            deliveryTimeMins = 9,
            description = "Refreshing sparkling soft drink with iconic cola taste. Best enjoyed chilled.",
            rating = 4.7
        ),
        Product(
            id = "p9",
            name = "Dettol Liquid Handwash Original",
            brand = "Dettol",
            category = "Personal Care",
            weightUnit = "200 ml",
            priceRupees = 55,
            mrpRupees = 65,
            deliveryTimeMins = 9,
            description = "100% germ protection liquid hand wash with pine fragrance. Gentle on hands.",
            rating = 4.8
        ),

        // Additional variety items
        Product(
            id = "p10",
            name = "Fresh Royal Alphonso Mangoes",
            brand = "Farm Fresh",
            category = "Fruits",
            weightUnit = "1 kg (4-5 pcs)",
            priceRupees = 299,
            mrpRupees = 380,
            deliveryTimeMins = 9,
            description = "Naturally ripened sweet aromatic Ratnagiri Alphonso mangoes. Rich golden pulp.",
            rating = 4.9,
            isBestseller = true
        ),
        Product(
            id = "p11",
            name = "Fresh Robusta Bananas",
            brand = "Farm Fresh",
            category = "Fruits",
            weightUnit = "500 g (3-4 pcs)",
            priceRupees = 28,
            mrpRupees = 35,
            deliveryTimeMins = 9,
            description = "Energy packed sweet yellow bananas. Great for breakfast smoothies and snacks.",
            rating = 4.7
        ),
        Product(
            id = "p12",
            name = "Amul Pasteurised Salted Butter",
            brand = "Amul",
            category = "Dairy",
            weightUnit = "100 g",
            priceRupees = 58,
            mrpRupees = 60,
            deliveryTimeMins = 9,
            description = "Utterly butterly delicious fresh cream butter. The taste of India.",
            rating = 4.9
        ),
        Product(
            id = "p13",
            name = "Fortune Sunlite Refined Sunflower Oil",
            brand = "Fortune",
            category = "Oil & Masala",
            weightUnit = "1 L pouch",
            priceRupees = 135,
            mrpRupees = 160,
            deliveryTimeMins = 9,
            description = "Light and healthy cooking oil enriched with natural Vitamin E.",
            rating = 4.6
        ),
        Product(
            id = "p14",
            name = "Cadbury Dairy Milk Silk Chocolate",
            brand = "Cadbury",
            category = "Snacks",
            weightUnit = "150 g",
            priceRupees = 175,
            mrpRupees = 190,
            deliveryTimeMins = 9,
            description = "Creamier, smoother milk chocolate bar that melts in your mouth effortlessly.",
            rating = 4.9,
            isBestseller = true
        ),
        Product(
            id = "p15",
            name = "Nescafe Classic Instant Coffee",
            brand = "Nescafe",
            category = "Beverages",
            weightUnit = "50 g jar",
            priceRupees = 190,
            mrpRupees = 210,
            deliveryTimeMins = 9,
            description = "100% pure roasted coffee beans for rich aroma and bold morning brew.",
            rating = 4.8
        ),
        Product(
            id = "p16",
            name = "Surf Excel Easy Wash Detergent",
            brand = "Surf Excel",
            category = "Personal Care",
            weightUnit = "1 kg",
            priceRupees = 140,
            mrpRupees = 165,
            deliveryTimeMins = 9,
            description = "Super concentrated stain removing powder that works fast even in tough collar stains.",
            rating = 4.7
        )
    )
}
