package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.ui.theme.BrokeItBorder
import com.example.ui.theme.BrokeItCardBg
import com.example.ui.theme.BrokeItDark
import com.example.ui.theme.BrokeItGray
import com.example.ui.theme.BrokeItGreen
import com.example.ui.theme.BrokeItGreenDark
import com.example.ui.theme.BrokeItGreenLight
import com.example.ui.theme.BrokeItRed
import com.example.ui.theme.BrokeItYellow

@Composable
fun ProductCard(
    product: Product,
    quantityInCart: Int,
    onAddToCart: () -> Unit,
    onRemoveFromCart: () -> Unit,
    onClickProduct: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, BrokeItBorder),
        shadowElevation = 0.5.dp,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClickProduct() }
            .testTag("product_card_${product.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Image Container + Delivery Badge + Discount Pill
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(BrokeItCardBg),
                contentAlignment = Alignment.Center
            ) {
                // Product Graphic Placeholder
                Icon(
                    imageVector = Icons.Default.ShoppingBag,
                    contentDescription = product.name,
                    tint = BrokeItGreen.copy(alpha = 0.4f),
                    modifier = Modifier.size(54.dp)
                )

                // 9-MIN Delivery Time Badge (Top Left)
                Surface(
                    shape = RoundedCornerShape(bottomEnd = 10.dp, topStart = 10.dp),
                    color = BrokeItDark.copy(alpha = 0.85f),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = "Fast Delivery",
                            tint = BrokeItYellow,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${product.deliveryTimeMins} MINS",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }

                // Discount percentage tag (Top Right)
                if (product.discountPercent > 0) {
                    Surface(
                        shape = RoundedCornerShape(bottomStart = 8.dp),
                        color = BrokeItRed,
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Text(
                            text = "${product.discountPercent}% OFF",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Weight/Unit Label
            Text(
                text = product.weightUnit,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = BrokeItGray
            )

            // Product Name
            Text(
                text = product.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BrokeItDark,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 17.sp,
                modifier = Modifier.height(36.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Price & Quantity Stepper Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Price section
                Column {
                    Text(
                        text = "₹${product.priceRupees}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = BrokeItDark
                    )
                    if (product.mrpRupees > product.priceRupees) {
                        Text(
                            text = "₹${product.mrpRupees}",
                            fontSize = 11.sp,
                            color = BrokeItGray,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }

                // Add Button / Quantity Stepper
                if (quantityInCart == 0) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = BrokeItGreenLight,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BrokeItGreen),
                        modifier = Modifier
                            .clickable { onAddToCart() }
                            .testTag("add_button_${product.id}")
                    ) {
                        Text(
                            text = "ADD",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = BrokeItGreenDark,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = BrokeItGreen,
                        modifier = Modifier.testTag("stepper_${product.id}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 3.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable { onRemoveFromCart() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Decrease",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }

                            Text(
                                text = "$quantityInCart",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable { onAddToCart() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Increase",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
