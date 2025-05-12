package com.alekseilomain.presentation.contactForm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alekseilomain.presentation.R
import com.alekseilomain.presentation.navigation.FormMode


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactFormScreen(
    mode: FormMode,
    uiState: ContactFormUiState,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,

) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.are_you_sure)) },
            text = { Text(stringResource(R.string.information_will_not_be_saved)) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onCancel()
                }) {
                    Text(stringResource(R.string.exit))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (mode == FormMode.ADD)
                            stringResource(R.string.new_contact)
                        else
                            stringResource(R.string.edit)
                    )
                },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Close, contentDescription = stringResource(R.string.exit))
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.Top
            ) {
                val lastNameError = !uiState.isLastNameValid
                val emailError = !uiState.isEmailValid

                TextField(
                    value = uiState.lastName,
                    onValueChange = onLastNameChange,
                    label = {
                        if (uiState.lastName.isEmpty()) {
                            Text(
                                buildString {
                                    append(stringResource(R.string.last_name))
                                    if (mode == FormMode.ADD) append("*")
                                },
                                color = colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    },
                    isError = lastNameError,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        errorContainerColor = Color.White
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        color = if (lastNameError) Color.Red else Color.Black
                    )
                )

                Spacer(Modifier.height(16.dp))

                TextField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    label = {
                        if (uiState.email.isEmpty()) {
                            Text(
                                buildString {
                                    append(stringResource(R.string.email))
                                    if (mode == FormMode.ADD) append("*")
                                },
                                color = colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    },
                    isError = emailError,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        errorContainerColor = Color.White
                ),
                    textStyle = LocalTextStyle.current.copy(
                        color = if (emailError) Color.Red else Color.Black
                    )
                )
            }

            Button(
                onClick = onSave,
                enabled = uiState.isFormValid,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(Color(0xFF42A5F5), Color(0xFF00BCD4))
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (mode == FormMode.ADD)
                            stringResource(R.string.add_contact)
                        else
                            stringResource(R.string.save),
                        color = Color.White
                    )
                }
            }
        }
    }
}
