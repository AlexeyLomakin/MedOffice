package com.alekseilomain.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.alekseilomain.presentation.LanguageToggle
import com.alekseilomain.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    seed: String,
    onSeedChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onToggleLanguage: (String) -> Unit,
    currentLang: String
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        color = Color.White
    ) {
        Box(Modifier.fillMaxSize()) {
            LanguageToggle(
                currentLang = currentLang,
                onLanguageSelected = onToggleLanguage,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 10.dp, top = 16.dp)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp)
                    .padding(top = 56.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.doctor_image),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                )

                Spacer(Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.seed_enter),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.seed),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF999999),
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                    TextField(
                        value = seed,
                        onValueChange = onSeedChange,
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = stringResource(R.string.seed_enter),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFCCCCCC)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor   = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor   = Color(0xFFE0E0E0),
                            unfocusedIndicatorColor = Color(0xFFE0E0E0),
                            cursorColor             = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFE0E0E0),
                    thickness = 1.dp
                )
                Spacer(Modifier.height(16.dp))
                val gradientEnabled = Brush.horizontalGradient(
                    listOf(Color(0xFF42A5F5), Color(0xFF00BCD4))
                )
                val gradientDisabled = Brush.horizontalGradient(
                    listOf(Color(0xFFBDBDBD), Color(0xFFBDBDBD))
                )

                Button(
                    onClick = onLoginClick,
                    enabled = seed.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = if (seed.isNotBlank()) gradientEnabled else gradientDisabled,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.sign_in).uppercase(),
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White
                        )
                    }
                }
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

