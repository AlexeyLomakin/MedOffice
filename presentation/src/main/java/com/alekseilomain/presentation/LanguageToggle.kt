package com.alekseilomain.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageToggle(
    currentLang: String,
    onLanguageSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = Color(0xFFF0F0F0), shape = RectangleShape)
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            listOf("ru", "en").forEachIndexed { idx, lang ->
                val selected = lang == currentLang
                Box(
                    modifier = Modifier
                        .background(
                            color = if (selected) Color.White else Color.Transparent,
                            shape = RectangleShape
                        )
                        .clickable { onLanguageSelected(lang) }
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = lang.uppercase(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = if (selected)
                            Color.Black
                        else
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
                if (idx == 0) {
                    Spacer(
                        Modifier
                            .width(1.dp)
                            .height(1.dp)
                            .background(Color(0xFFBDBDBD))
                    )
                }
            }
        }
    }
}