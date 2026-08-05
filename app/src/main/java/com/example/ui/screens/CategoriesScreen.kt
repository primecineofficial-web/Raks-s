package com.example.ui.screens

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Sanitizer
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GroceryData
import com.example.model.CategoryItem
import com.example.ui.theme.BrokeItBorder
import com.example.ui.theme.BrokeItCardBg
import com.example.ui.theme.BrokeItDark
import com.example.ui.theme.BrokeItGray
import com.example.ui.theme.BrokeItGreen
import com.example.ui.theme.BrokeItGreenDark
import com.example.ui.theme.BrokeItGreenLight

@Composable
fun CategoriesScreen(
    onSelectCategory: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 16.dp)
    ) {
        // Header
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Explore Categories",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = BrokeItDark
            )
            Text(
                text = "Choose from fresh fruits, vegetables, snacks, and daily essentials.",
                fontSize = 13.sp,
                color = BrokeItGray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Categories Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(GroceryData.categories.filter { it.id != "all" }) { category ->
                CategoryCardItem(
                    category = category,
                    onClick = { onSelectCategory(category.id) }
                )
            }
        }
    }
}

@Composable
fun CategoryCardItem(
    category: CategoryItem,
    onClick: () -> Unit
) {
    val iconVector: ImageVector = when (category.iconName) {
        "shopping_bag" -> Icons.Default.ShoppingBag
        "eco" -> Icons.Default.Eco
        "nutrition" -> Icons.Default.Eco
        "fastfood" -> Icons.Default.Fastfood
        "bakery_dining" -> Icons.Default.BakeryDining
        "grain" -> Icons.Default.Grain
        "soup_kitchen" -> Icons.Default.SoupKitchen
        "local_drink" -> Icons.Default.LocalDrink
        "local_bar" -> Icons.Default.LocalBar
        "sanitizer" -> Icons.Default.Sanitizer
        else -> Icons.Default.Category
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = BrokeItCardBg,
        border = androidx.compose.foundation.BorderStroke(1.dp, BrokeItBorder),
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .clickable { onClick() }
            .testTag("category_grid_${category.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = BrokeItGreenLight,
                modifier = Modifier.size(46.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = category.name,
                        tint = BrokeItGreenDark,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = category.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = BrokeItDark,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = category.count,
                fontSize = 10.sp,
                color = BrokeItGray,
                textAlign = TextAlign.Center
            )
        }
    }
}
