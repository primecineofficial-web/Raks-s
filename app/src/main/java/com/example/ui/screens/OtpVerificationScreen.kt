package com.example.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrokeItBorder
import com.example.ui.theme.BrokeItCardBg
import com.example.ui.theme.BrokeItDark
import com.example.ui.theme.BrokeItGray
import com.example.ui.theme.BrokeItGreen
import com.example.ui.theme.BrokeItGreenDark
import com.example.ui.theme.BrokeItGreenLight
import com.example.ui.theme.BrokeItRed

@Composable
fun OtpVerificationScreen(
    phoneNumber: String,
    otpDigits: List<String>,
    otpError: String?,
    timerSeconds: Int,
    canResendOtp: Boolean,
    onOtpDigitEntered: (Int, String) -> Unit,
    onVerifyClicked: () -> Unit,
    onResendClicked: () -> Unit,
    onChangeNumberClicked: () -> Unit
) {
    val maskedPhone = if (phoneNumber.length >= 10) {
        "+91 ${phoneNumber.substring(0, 2)}***** ${phoneNumber.substring(7)}"
    } else {
        "+91 XXXXX XXXXX"
    }

    val focusRequesters = remember { List(6) { FocusRequester() } }
    val fullOtpCode = otpDigits.joinToString("")
    val isComplete = fullOtpCode.length == 6

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onChangeNumberClicked,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Change Number",
                        tint = BrokeItDark
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Verification",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrokeItDark
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Verify OTP",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = BrokeItDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "We have sent a 6-digit verification code to $maskedPhone.",
                fontSize = 14.sp,
                color = BrokeItGray,
                lineHeight = 20.sp
            )

            Row(
                modifier = Modifier.padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Wrong number? ",
                    fontSize = 13.sp,
                    color = BrokeItGray
                )
                Text(
                    text = "Change mobile number",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrokeItGreen,
                    modifier = Modifier.clickable { onChangeNumberClicked() }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Demo Code Banner Hint
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = BrokeItGreenLight,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Key,
                        contentDescription = null,
                        tint = BrokeItGreenDark,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Demo Verification Code: 123456",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrokeItGreenDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 6 OTP Digit Boxes Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 0 until 6) {
                    val digit = otpDigits.getOrNull(i) ?: ""
                    val isFocused = digit.isNotEmpty() || (i == 0 && fullOtpCode.isEmpty())

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = if (digit.isNotEmpty()) BrokeItGreenLight else BrokeItCardBg,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = if (isFocused) 2.dp else 1.dp,
                                color = when {
                                    otpError != null -> BrokeItRed
                                    digit.isNotEmpty() -> BrokeItGreen
                                    else -> BrokeItBorder
                                },
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        BasicTextField(
                            value = digit,
                            onValueChange = { newValue ->
                                if (newValue.length > 1) {
                                    onOtpDigitEntered(i, newValue)
                                } else {
                                    onOtpDigitEntered(i, newValue)
                                    if (newValue.isNotEmpty() && i < 5) {
                                        focusRequesters[i + 1].requestFocus()
                                    }
                                }
                            },
                            modifier = Modifier
                                .focusRequester(focusRequesters[i])
                                .testTag("otp_input_$i"),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrokeItDark,
                                textAlign = TextAlign.Center
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                    }
                }
            }

            Text(
                text = "Enter the 6-digit code sent to your mobile.",
                fontSize = 12.sp,
                color = BrokeItGray,
                modifier = Modifier.padding(top = 10.dp, start = 2.dp)
            )

            // Error Text if any
            if (otpError != null) {
                Text(
                    text = otpError,
                    fontSize = 13.sp,
                    color = BrokeItRed,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp, start = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Timer / Resend Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (canResendOtp) {
                    Text(
                        text = "Didn't receive code? ",
                        fontSize = 14.sp,
                        color = BrokeItGray
                    )
                    Text(
                        text = "Resend OTP",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrokeItGreen,
                        modifier = Modifier
                            .clickable { onResendClicked() }
                            .testTag("resend_otp_button")
                    )
                } else {
                    Text(
                        text = "Resend code in ",
                        fontSize = 14.sp,
                        color = BrokeItGray
                    )
                    Text(
                        text = "0:${if (timerSeconds < 10) "0$timerSeconds" else timerSeconds}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrokeItGreenDark
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Verify Button
            Button(
                onClick = onVerifyClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("verify_otp_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isComplete) BrokeItGreen else BrokeItBorder,
                    contentColor = if (isComplete) Color.White else BrokeItGray
                ),
                enabled = isComplete
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isComplete) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = "Verify & Continue",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
