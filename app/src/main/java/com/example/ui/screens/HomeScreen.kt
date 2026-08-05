package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.GroceryData
import com.example.model.Product
import com.example.ui.components.ProductCard
import com.example.ui.theme.BrokeItBorder
import com.example.ui.theme.BrokeItCardBg
import com.example.ui.theme.BrokeItDark
import com.example.ui.theme.BrokeItGray
import com.example.ui.theme.BrokeItGreen
import com.example.ui.theme.BrokeItGreenDark
import com.example.ui.theme.BrokeItGreenLight
import com.example.ui.theme.BrokeItYellow

@Composable
fun HomeScreen(
    selectedCategory: String,
    searchQuery: String,
    cartItems: Map<String, Int>,
    onSelectCategory: (String) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    onRemoveFromCart: (String) -> Unit,
    onProductClick: (Product) -> Unit,
    onProfileClick: () -> Unit
) {
    val filteredProducts = GroceryData.products.filter { product ->
        val matchesCategory = if (selectedCategory == "all") true else {
            val catObj = GroceryData.categories.find { it.id == selectedCategory }
            catObj?.name.equals(product.category, ignoreCase = true)
        }
        val matchesSearch = searchQuery.isBlank() ||
                product.name.contains(searchQuery, ignoreCase = true) ||
                product.brand.contains(searchQuery, ignoreCase = true) ||
                product.category.contains(searchQuery, ignoreCase = true)

        matchesCategory && matchesSearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Blinkit-style Header
        Surface(
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Location & Delivery Badge Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = BrokeItGreenLight,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Location",
                                    tint = BrokeItGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Meerut",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = BrokeItDark
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "• Sector 4",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = BrokeItGray
                                )
                            }
                            Text(
                                text = "Main Market, Meerut, UP",
                                fontSize = 11.sp,
                                color = BrokeItGray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // 9-min simulation badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = BrokeItDark,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = BrokeItYellow,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "9 MINS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Text(
                                text = " (Sim)",
                                fontSize = 9.sp,
                                color = BrokeItGreenLight
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Profile Icon
                    Surface(
                        shape = CircleShape,
                        color = BrokeItCardBg,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BrokeItBorder),
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { onProfileClick() }
                            .testTag("home_profile_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = BrokeItDark,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChanged,
                    placeholder = {
                        Text(
                            text = "Search grocery, vegetables, fruits, chips...",
                            fontSize = 13.sp,
                            color = BrokeItGray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("home_search_bar"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = BrokeItGreen
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search",
                                tint = BrokeItGray,
                                modifier = Modifier.clickable { onSearchQueryChanged("") }
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrokeItGreen,
                        unfocusedBorderColor = BrokeItBorder,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = BrokeItCardBg
                    )
                )
            }
        }

        // Main Scrollable Content
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 100.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp)
        ) {
            // Header item 1: Horizontal Category Chips
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(GroceryData.categories) { category ->
                            val isSelected = selectedCategory == category.id
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) BrokeItGreen else BrokeItCardBg,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSelected) BrokeItGreen else BrokeItBorder
                                ),
                                modifier = Modifier
                                    .clickable { onSelectCategory(category.id) }
                                    .testTag("category_chip_${category.id}")
                            ) {
                                Text(
                                    text = category.name,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else BrokeItDark,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Header item 2: Hero Animated Banner
            if (searchQuery.isBlank() && selectedCategory == "all") {
                item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = BrokeItGreenLight,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BrokeItGreen.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = painterResource(id = R.drawable.img_hero_banner),
                                contentDescription = "BrokeIt Hero Banner",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp)
                                    .clip(RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.Crop,
                                alpha = 0.25f
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = BrokeItGreenDark
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Lock,
                                            contentDescription = null,
                                            tint = BrokeItYellow,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "BROKEIT SIMULATOR MODE",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color.White,
                                            letterSpacing = 0.5.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Add everything. Pay nothing.",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Black,
                                    color = BrokeItDark
                                )

                                Text(
                                    text = "BrokeIt lets you feel quick-commerce without real spending.",
                                    fontSize = 12.sp,
                                    color = BrokeItGray,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }

            // Section Header
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (selectedCategory == "all") "Bestsellers in 9 Mins" else GroceryData.categories.find { it.id == selectedCategory }?.name ?: "Products",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrokeItDark
                    )

                    Text(
                        text = "${filteredProducts.size} items",
                        fontSize = 12.sp,
                        color = BrokeItGray
                    )
                }
            }

            // Product Cards
            items(filteredProducts, key = { it.id }) { product ->
                ProductCard(
                    product = product,
                    quantityInCart = cartItems[product.id] ?: 0,
                    onAddToCart = { onAddToCart(product.id) },
                    onRemoveFromCart = { onRemoveFromCart(product.id) },
                    onClickProduct = { onProductClick(product) }
                )
            }
        }
    }
}
