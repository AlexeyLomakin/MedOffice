package com.alekseilomain.presentation.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alekseilomain.domain.model.Contact
import com.alekseilomain.presentation.LanguageToggle
import com.alekseilomain.presentation.R
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    contacts: List<Contact>,
    city: String,
    currentLang: String,
    onLogout: () -> Unit,
    onAddContact: () -> Unit,
    onEditContact: (Long) -> Unit,
    onToggleLanguage: () -> Unit,
    onLoadNextPage: () -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .filter { it != null && it >= contacts.lastIndex }
            .collect { onLoadNextPage() }
    }

    var showLogoutDialog by remember { mutableStateOf(false) }
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text(stringResource(R.string.are_you_sure)) },
            text = { Text(stringResource(R.string.information_will_be_deleted)) },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    onLogout()
                }) {
                    Text(stringResource(R.string.exit))
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    Box(Modifier.padding(start = 16.dp)) {
                        LanguageToggle(currentLang, onLanguageSelected = { onToggleLanguage() })
                    }
                },
                title = {
                    Text(city.ifBlank { " " }, style = MaterialTheme.typography.titleMedium)
                },
                actions = {
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(
                            painter = painterResource(R.drawable.logout),
                            contentDescription = stringResource(R.string.exit),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HeaderRow()

            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState
                ) {
                    itemsIndexed(contacts, key = { _, contact -> contact.id }) { index, contact ->
                        ContactRow(
                            rowIndex = index + 1,
                            contact = contact,
                            index = index,
                            onEdit = { if (contact.isManual) onEditContact(contact.id) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = onAddContact,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF42A5F5), Color(0xFF00BCD4))
                            ),
                            RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.add_contact),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderRow() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        listOf(
            "№" to 1f,
            stringResource(R.string.last_name) to 2.5f,
            stringResource(R.string.email) to 4.5f
        ).forEachIndexed { idx, (text, weight) ->
            Box(
                Modifier
                    .weight(weight)
                    .fillMaxHeight()
                    .background(Color(0xFFE1F5FF))
                    .border(1.dp, Color(0xFFE0E0E0))
                    .padding(horizontal = 4.dp),
                contentAlignment = if (idx == 0) Alignment.Center else Alignment.CenterStart
            ) {
                Text(
                    text,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun ContactRow(
    rowIndex: Int,
    contact: Contact,
    index: Int,
    onEdit: () -> Unit
) {
    val backgroundColor = if (index % 2 == 1) Color(0xFFEDEDED) else Color.White

    Row(
        Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(backgroundColor)
    ) {
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(Dp.Hairline, Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Text(rowIndex.toString(), style = MaterialTheme.typography.bodySmall)
        }
        Box(
            Modifier
                .weight(2.5f)
                .fillMaxHeight()
                .border(Dp.Hairline, Color(0xFFE0E0E0))
                .padding(horizontal = 4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                contact.lastName,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(
            Modifier
                .weight(4.5f)
                .fillMaxHeight()
                .border(Dp.Hairline, Color(0xFFE0E0E0))
                .padding(horizontal = 4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    contact.email,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodySmall
                )
                if (contact.isManual) {
                    Icon(
                        painterResource(R.drawable.edit),
                        contentDescription = stringResource(R.string.edit),
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { onEdit() }
                    )
                }
            }
        }
    }
}

