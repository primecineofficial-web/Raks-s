package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CartItem
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
fun CartCheckoutScreen(
    cartItemList: List<CartItem>,
    subtotal: Int,
    mrpTotal: Int,
    savings: Int,
    showModal: Boolean,
    onAddToCart: (String) -> Unit,
    onRemoveFromCart: (String) -> Unit,
    onAttemptOrder: () -> Unit,
    onDismissModal: () -> Unit,
    onCelebrateAndClear: () -> Unit,
    onBrowseProducts: () -> Unit
) {
    if (cartItemList.isEmpty()) {
        // Empty State
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = BrokeItGreenLight,
                    modifier = Modifier.size(100.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Empty Cart",
                            tint = BrokeItGreen,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Your simulated cart is empty",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrokeItDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Add grocery items, snacks, or beverages to test the quick-commerce experience without spending money.",
                    fontSize = 14.sp,
                    color = BrokeItGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onBrowseProducts,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrokeItGreen,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(48.dp)
                        .testTag("empty_cart_browse_button")
                ) {
                    Text(
                        text = "Browse Grocery Store",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    } else {
        // Cart Items & Bill Summary Layout
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 16.dp, bottom = 120.dp)
            ) {
                // Top Header
                item {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Simulated Order Cart",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrokeItDark
                            )

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = BrokeItGreenLight
                            ) {
                                Text(
                                    text = "${cartItemList.sumOf { it.quantity }} Items",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BrokeItGreenDark,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Dopamine Protection Banner
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = BrokeItGreenLight,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BrokeItGreen.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Security,
                                    contentDescription = null,
                                    tint = BrokeItGreenDark,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "BrokeIt Impulse Shield Active",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BrokeItGreenDark
                                    )
                                    Text(
                                        text = "You are saving ₹$subtotal in real cash right now!",
                                        fontSize = 12.sp,
                                        color = BrokeItDark
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "ITEM LIST",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrokeItGray,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                // Items list
                items(cartItemList, key = { it.product.id }) { item ->
                    CartItemRow(
                        cartItem = item,
                        onAddToCart = { onAddToCart(item.product.id) },
                        onRemoveFromCart = { onRemoveFromCart(item.product.id) }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Bill Details Section
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = BrokeItCardBg,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BrokeItBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.ReceiptLong,
                                    contentDescription = null,
                                    tint = BrokeItDark,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Bill Summary",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BrokeItDark
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            BillRow("Item Total (MRP)", "₹$mrpTotal")
                            if (savings > 0) {
                                BillRow("Product Discount", "- ₹$savings", color = BrokeItGreenDark)
                            }
                            BillRow("Delivery Fee (9 MINS)", "FREE", color = BrokeItGreen)
                            BillRow("Handling & Platform Fee", "FREE", color = BrokeItGreen)

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 12.dp),
                                color = BrokeItBorder
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Grand Total Bill",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BrokeItDark
                                    )
                                    Text(
                                        text = "Simulated amount",
                                        fontSize = 11.sp,
                                        color = BrokeItGray
                                    )
                                }

                                Text(
                                    text = "₹$subtotal",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Black,
                                    color = BrokeItDark
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // Bottom Sticky Bar with DISABLED Order Button
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, BrokeItBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Disabled Order Button
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = BrokeItCardBg,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BrokeItBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .clickable { onAttemptOrder() }
                            .testTag("disabled_order_button")
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Order Disabled",
                                tint = BrokeItGray,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Order disabled – BrokeIt mode ON",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrokeItGray
                            )
                        }
                    }

                    Text(
                        text = "Tap to verify impulse shield dialog",
                        fontSize = 11.sp,
                        color = BrokeItGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                    )
                }
            }
        }
    }

    // Impulse Control Protection Modal Dialog
    if (showModal) {
        AlertDialog(
            onDismissRequest = onDismissModal,
            icon = {
                Surface(
                    shape = CircleShape,
                    color = BrokeItGreenLight,
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "Shield Active",
                            tint = BrokeItGreenDark,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            },
            title = {
                Text(
                    text = "Relax, you're safe! 🛡️",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = BrokeItDark
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "BrokeIt successfully blocked this transaction.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrokeItGreenDark,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "You got the thrill of quick-commerce shopping without spending a single rupee. Your simulated bill total was ₹$subtotal.",
                        fontSize = 13.sp,
                        color = BrokeItGray,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = onCelebrateAndClear,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrokeItGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("celebrate_clear_cart_button")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Clear Cart & Save ₹$subtotal",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = onDismissModal,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Got it",
                        color = BrokeItDark
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onAddToCart: () -> Unit,
    onRemoveFromCart: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, BrokeItBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon / image
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(BrokeItCardBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingBag,
                    contentDescription = cartItem.product.name,
                    tint = BrokeItGreen.copy(alpha = 0.5f),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cartItem.product.name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrokeItDark,
                    maxLines = 1
                )
                Text(
                    text = cartItem.product.weightUnit,
                    fontSize = 11.sp,
                    color = BrokeItGray
                )
                Text(
                    text = "₹${cartItem.product.priceRupees} × ${cartItem.quantity} = ₹${cartItem.product.priceRupees * cartItem.quantity}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = BrokeItGreenDark,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            // Stepper
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = BrokeItGreen,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(26.dp)
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
                        text = "${cartItem.quantity}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(26.dp)
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

@Composable
fun BillRow(
    label: String,
    value: String,
    color: Color = BrokeItDark
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = BrokeItGray
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}
