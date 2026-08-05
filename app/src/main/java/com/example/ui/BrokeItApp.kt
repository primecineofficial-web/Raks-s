package com.example.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.CartCheckoutScreen
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.CategoryProductListScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MobileLoginScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.OtpVerificationScreen
import com.example.ui.screens.ProductDetailScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.BrokeItBorder
import com.example.ui.theme.BrokeItDark
import com.example.ui.theme.BrokeItGray
import com.example.ui.theme.BrokeItGreen
import com.example.ui.theme.BrokeItGreenDark
import com.example.ui.theme.BrokeItGreenLight
import com.example.ui.theme.BrokeItTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.BrokeItViewModel

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val tabIndex: Int,
    val testTag: String
)

@Composable
fun BrokeItApp(
    viewModel: BrokeItViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.showClearCartToast) {
        if (uiState.showClearCartToast) {
            Toast.makeText(context, "🎉 Cart cleared! Real money saved safely.", Toast.LENGTH_SHORT).show()
            viewModel.dismissToast()
        }
    }

    BrokeItTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            when (uiState.currentScreen) {
                AppScreen.Splash -> {
                    SplashScreen()
                }

                AppScreen.MobileLogin -> {
                    MobileLoginScreen(
                        phoneNumber = uiState.phoneNumber,
                        phoneError = uiState.phoneError,
                        onPhoneNumberChanged = { viewModel.onPhoneNumberChanged(it) },
                        onSendOtpClicked = { viewModel.sendOtp() }
                    )
                }

                AppScreen.OtpVerification -> {
                    OtpVerificationScreen(
                        phoneNumber = uiState.phoneNumber,
                        otpDigits = uiState.otpDigits,
                        otpError = uiState.otpError,
                        timerSeconds = uiState.timerSeconds,
                        canResendOtp = uiState.canResendOtp,
                        onOtpDigitEntered = { index, digit -> viewModel.onOtpDigitEntered(index, digit) },
                        onVerifyClicked = { viewModel.verifyOtp() },
                        onResendClicked = { viewModel.resendOtp() },
                        onChangeNumberClicked = { viewModel.navigateBackToLogin() }
                    )
                }

                AppScreen.Onboarding -> {
                    OnboardingScreen(
                        onCompleteOnboarding = { viewModel.completeOnboarding() }
                    )
                }

                AppScreen.CategoryDetailList -> {
                    CategoryProductListScreen(
                        categoryId = uiState.selectedCategory,
                        cartItems = uiState.cartItems,
                        onBack = { viewModel.backToMainApp() },
                        onAddToCart = { viewModel.addToCart(it) },
                        onRemoveFromCart = { viewModel.removeFromCart(it) },
                        onProductClick = { viewModel.openProductDetail(it) }
                    )
                }

                AppScreen.MainApp -> {
                    Scaffold(
                        bottomBar = {
                            BrokeItBottomNavigation(
                                selectedTab = uiState.selectedTab,
                                totalCartItems = viewModel.getCartTotalCount(),
                                cartSubtotal = viewModel.getCartSubtotal(),
                                onTabSelected = { viewModel.selectTab(it) }
                            )
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            when (uiState.selectedTab) {
                                0 -> HomeScreen(
                                    selectedCategory = uiState.selectedCategory,
                                    searchQuery = uiState.searchQuery,
                                    cartItems = uiState.cartItems,
                                    onSelectCategory = { viewModel.selectCategory(it) },
                                    onSearchQueryChanged = { viewModel.updateSearchQuery(it) },
                                    onAddToCart = { viewModel.addToCart(it) },
                                    onRemoveFromCart = { viewModel.removeFromCart(it) },
                                    onProductClick = { viewModel.openProductDetail(it) },
                                    onProfileClick = { viewModel.selectTab(3) }
                                )

                                1 -> CategoriesScreen(
                                    onSelectCategory = { viewModel.openCategoryDetailScreen(it) }
                                )

                                2 -> CartCheckoutScreen(
                                    cartItemList = viewModel.getCartItemList(),
                                    subtotal = viewModel.getCartSubtotal(),
                                    mrpTotal = viewModel.getCartMrpTotal(),
                                    savings = viewModel.getCartSavings(),
                                    showModal = uiState.showOrderDisabledModal,
                                    onAddToCart = { viewModel.addToCart(it) },
                                    onRemoveFromCart = { viewModel.removeFromCart(it) },
                                    onAttemptOrder = { viewModel.onAttemptOrder() },
                                    onDismissModal = { viewModel.dismissOrderDisabledModal() },
                                    onCelebrateAndClear = { viewModel.celebrateAndClearCart() },
                                    onBrowseProducts = { viewModel.selectTab(0) }
                                )

                                3 -> ProfileScreen(
                                    phoneNumber = uiState.phoneNumber,
                                    totalPreventedSpend = uiState.totalPreventedSpend,
                                    totalCartsSimulated = uiState.totalCartsSimulated,
                                    onLogout = { viewModel.navigateBackToLogin() }
                                )
                            }
                        }
                    }
                }
            }

            // Product Detail Overlay Screen
            uiState.selectedProductForDetail?.let { product ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    ProductDetailScreen(
                        product = product,
                        quantityInCart = uiState.cartItems[product.id] ?: 0,
                        onClose = { viewModel.closeProductDetail() },
                        onAddToCart = { viewModel.addToCart(product.id) },
                        onRemoveFromCart = { viewModel.removeFromCart(product.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun BrokeItBottomNavigation(
    selectedTab: Int,
    totalCartItems: Int,
    cartSubtotal: Int,
    onTabSelected: (Int) -> Unit
) {
    val navItems = listOf(
        BottomNavItem("Home", Icons.Default.Home, 0, "nav_home"),
        BottomNavItem("Categories", Icons.Default.Category, 1, "nav_categories"),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, 2, "nav_cart"),
        BottomNavItem("Profile", Icons.Default.Person, 3, "nav_profile")
    )

    Surface(
        color = Color.White,
        shadowElevation = 12.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, BrokeItBorder),
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEach { item ->
                val isSelected = selectedTab == item.tabIndex

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) BrokeItGreenLight else Color.Transparent,
                    modifier = Modifier
                        .clickable { onTabSelected(item.tabIndex) }
                        .padding(horizontal = 4.dp, vertical = 6.dp)
                        .testTag(item.testTag)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BadgedBox(
                            badge = {
                                if (item.tabIndex == 2 && totalCartItems > 0) {
                                    Badge(
                                        containerColor = BrokeItGreen,
                                        contentColor = Color.White
                                    ) {
                                        Text(
                                            text = "$totalCartItems",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (isSelected) BrokeItGreenDark else BrokeItGray,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        if (isSelected) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = item.title,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrokeItGreenDark
                            )
                        }
                    }
                }
            }
        }
    }
}
