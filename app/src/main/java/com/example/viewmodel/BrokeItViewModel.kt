package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GroceryData
import com.example.model.CartItem
import com.example.model.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BrokeItUiState(
    val currentScreen: AppScreen = AppScreen.Splash,
    val phoneNumber: String = "",
    val phoneError: String? = null,
    val otpDigits: List<String> = List(6) { "" },
    val otpError: String? = null,
    val timerSeconds: Int = 30,
    val canResendOtp: Boolean = false,
    val isLoggedIn: Boolean = false,
    val selectedTab: Int = 0, // 0: Home, 1: Categories, 2: Cart, 3: Profile
    val selectedCategory: String = "all",
    val searchQuery: String = "",
    val cartItems: Map<String, Int> = emptyMap(), // productId -> qty
    val selectedProductForDetail: Product? = null,
    val showOrderDisabledModal: Boolean = false,
    val totalPreventedSpend: Int = 2450, // Default demo saved rupees
    val totalCartsSimulated: Int = 8,
    val showClearCartToast: Boolean = false
)

sealed class AppScreen {
    object Splash : AppScreen()
    object MobileLogin : AppScreen()
    object OtpVerification : AppScreen()
    object Onboarding : AppScreen()
    object MainApp : AppScreen()
    object CategoryDetailList : AppScreen()
}

class BrokeItViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BrokeItUiState())
    val uiState: StateFlow<BrokeItUiState> = _uiState.asStateFlow()

    init {
        // Run splash screen timer
        viewModelScope.launch {
            delay(2200)
            if (!_uiState.value.isLoggedIn) {
                _uiState.update { it.copy(currentScreen = AppScreen.MobileLogin) }
            } else {
                _uiState.update { it.copy(currentScreen = AppScreen.MainApp) }
            }
        }
    }

    fun onPhoneNumberChanged(number: String) {
        val filtered = number.filter { it.isDigit() }.take(10)
        _uiState.update {
            it.copy(
                phoneNumber = filtered,
                phoneError = if (filtered.isNotEmpty() && filtered.length < 10) "Please enter a valid 10-digit mobile number." else null
            )
        }
    }

    fun sendOtp() {
        val phone = _uiState.value.phoneNumber
        if (phone.length < 10) {
            _uiState.update { it.copy(phoneError = "Please enter a valid 10-digit mobile number.") }
            return
        }

        _uiState.update {
            it.copy(
                currentScreen = AppScreen.OtpVerification,
                otpDigits = List(6) { "" },
                otpError = null,
                timerSeconds = 30,
                canResendOtp = false
            )
        }

        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            for (i in 30 downTo 0) {
                _uiState.update { it.copy(timerSeconds = i, canResendOtp = i == 0) }
                delay(1000)
            }
        }
    }

    fun resendOtp() {
        if (!_uiState.value.canResendOtp) return
        _uiState.update {
            it.copy(
                timerSeconds = 30,
                canResendOtp = false,
                otpError = null,
                otpDigits = List(6) { "" }
            )
        }
        startTimer()
    }

    fun onOtpDigitEntered(index: Int, digit: String) {
        val current = _uiState.value.otpDigits.toMutableList()
        if (digit.length > 1) {
            // Handle paste action
            val digits = digit.filter { it.isDigit() }.take(6)
            for (i in 0 until 6) {
                current[i] = if (i < digits.length) digits[i].toString() else ""
            }
        } else {
            current[index] = digit.filter { it.isDigit() }
        }

        _uiState.update { it.copy(otpDigits = current, otpError = null) }
    }

    fun verifyOtp() {
        val code = _uiState.value.otpDigits.joinToString("")
        if (code == "123456") {
            _uiState.update {
                it.copy(
                    isLoggedIn = true,
                    currentScreen = AppScreen.Onboarding,
                    otpError = null
                )
            }
        } else {
            _uiState.update {
                it.copy(otpError = "Incorrect code. Please try again (Demo code is 123456).")
            }
        }
    }

    fun completeOnboarding() {
        _uiState.update { it.copy(currentScreen = AppScreen.MainApp) }
    }

    fun selectTab(tabIndex: Int) {
        _uiState.update { it.copy(selectedTab = tabIndex) }
    }

    fun selectCategory(categoryId: String) {
        _uiState.update {
            it.copy(
                selectedCategory = categoryId,
                currentScreen = if (categoryId != "all" && it.currentScreen != AppScreen.MainApp) AppScreen.CategoryDetailList else it.currentScreen
            )
        }
    }

    fun openCategoryDetailScreen(categoryId: String) {
        _uiState.update {
            it.copy(
                selectedCategory = categoryId,
                currentScreen = AppScreen.CategoryDetailList
            )
        }
    }

    fun backToMainApp() {
        _uiState.update { it.copy(currentScreen = AppScreen.MainApp) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun addToCart(productId: String) {
        val currentCart = _uiState.value.cartItems.toMutableMap()
        val currentQty = currentCart[productId] ?: 0
        currentCart[productId] = currentQty + 1
        _uiState.update { it.copy(cartItems = currentCart) }
    }

    fun removeFromCart(productId: String) {
        val currentCart = _uiState.value.cartItems.toMutableMap()
        val currentQty = currentCart[productId] ?: 0
        if (currentQty > 1) {
            currentCart[productId] = currentQty - 1
        } else {
            currentCart.remove(productId)
        }
        _uiState.update { it.copy(cartItems = currentCart) }
    }

    fun openProductDetail(product: Product) {
        _uiState.update { it.copy(selectedProductForDetail = product) }
    }

    fun closeProductDetail() {
        _uiState.update { it.copy(selectedProductForDetail = null) }
    }

    fun onAttemptOrder() {
        _uiState.update { it.copy(showOrderDisabledModal = true) }
    }

    fun dismissOrderDisabledModal() {
        _uiState.update { it.copy(showOrderDisabledModal = false) }
    }

    fun celebrateAndClearCart() {
        val cartTotal = getCartSubtotal()
        _uiState.update {
            it.copy(
                cartItems = emptyMap(),
                showOrderDisabledModal = false,
                totalPreventedSpend = it.totalPreventedSpend + cartTotal,
                totalCartsSimulated = it.totalCartsSimulated + 1,
                showClearCartToast = true
            )
        }
    }

    fun dismissToast() {
        _uiState.update { it.copy(showClearCartToast = false) }
    }

    fun navigateBackToLogin() {
        _uiState.update {
            it.copy(
                isLoggedIn = false,
                currentScreen = AppScreen.MobileLogin,
                phoneNumber = "",
                otpDigits = List(6) { "" }
            )
        }
    }

    fun getCartItemList(): List<CartItem> {
        val map = _uiState.value.cartItems
        return GroceryData.products.filter { map.containsKey(it.id) }.map { product ->
            CartItem(product = product, quantity = map[product.id] ?: 0)
        }
    }

    fun getCartTotalCount(): Int {
        return _uiState.value.cartItems.values.sum()
    }

    fun getCartSubtotal(): Int {
        return getCartItemList().sumOf { it.product.priceRupees * it.quantity }
    }

    fun getCartMrpTotal(): Int {
        return getCartItemList().sumOf { it.product.mrpRupees * it.quantity }
    }

    fun getCartSavings(): Int {
        return getCartMrpTotal() - getCartSubtotal()
    }
}
