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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun MobileLoginScreen(
    phoneNumber: String,
    phoneError: String?,
    onPhoneNumberChanged: (String) -> Unit,
    onSendOtpClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // App Brand Pill
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = BrokeItGreenLight
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = BrokeItGreenDark,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "BrokeIt Account Verification",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrokeItGreenDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Enter your mobile number",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = BrokeItDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "We’ll send you a 6-digit verification code to login to BrokeIt.",
                fontSize = 14.sp,
                color = BrokeItGray,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Phone Field Input Group
            Text(
                text = "MOBILE NUMBER",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = BrokeItGray,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Country Code Selector Box
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = BrokeItCardBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, BrokeItBorder),
                    modifier = Modifier
                        .height(56.dp)
                        .clickable { }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🇮🇳 +91",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrokeItDark
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Select country",
                            tint = BrokeItGray
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Main Phone Input
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = onPhoneNumberChanged,
                    placeholder = {
                        Text(
                            text = "98765 43210",
                            color = BrokeItGray.copy(alpha = 0.5f),
                            fontSize = 16.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("phone_number_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = BrokeItGreen
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrokeItGreen,
                        unfocusedBorderColor = BrokeItBorder,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = BrokeItCardBg
                    ),
                    isError = phoneError != null
                )
            }

            // Example formatting text
            Text(
                text = "Example: +91 98765 43210",
                fontSize = 12.sp,
                color = BrokeItGray,
                modifier = Modifier.padding(top = 6.dp, start = 4.dp)
            )

            // Validation Error text
            if (phoneError != null) {
                Text(
                    text = phoneError,
                    fontSize = 12.sp,
                    color = BrokeItRed,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Informational Note Card
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = BrokeItCardBg,
                border = androidx.compose.foundation.BorderStroke(1.dp, BrokeItBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Note",
                        tint = BrokeItGreen,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "We use your number only to verify login. No real orders or payments are ever made in BrokeIt.",
                        fontSize = 12.sp,
                        color = BrokeItGray,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        // Bottom CTA & Terms Footer
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onSendOtpClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("send_otp_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrokeItGreen,
                    contentColor = Color.White
                ),
                enabled = phoneNumber.length == 10
            ) {
                Text(
                    text = "Send OTP",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "By continuing, you agree to receive a one-time verification code on this number.",
                fontSize = 11.sp,
                color = BrokeItGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}
