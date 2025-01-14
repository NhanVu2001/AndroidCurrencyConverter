package com.example.myapplication.presentation

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExchangeScreenCore(
    viewModel: MainView = koinViewModel()
) {
    ExchangeScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ExchangeScreen(
    state: ExchangeState,
    onAction: (ExchangeActions) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        Box(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxSize()
        )
        {
            var isDropDownOpen by rememberSaveable { mutableStateOf(false) }
            var isFromCurrencyPicker by rememberSaveable { mutableStateOf(false) }

            ExchangeSection(state = state,
                onOpenCurrencyPicker = { isFromCurrency ->
                    isFromCurrencyPicker = isFromCurrency
                    isDropDownOpen = true
                }
            )
            DropdownMenu(
                modifier = Modifier.heightIn(max = 700.dp),
                expanded = isDropDownOpen,
                onDismissRequest = {
                    isDropDownOpen = false
                }
            ) {
                state.allCurrency.forEachIndexed { index, currency ->
                    Column {
                        if (index == 0) {
                            HorizontalDivider()
                        }
                        Text(
                            text = currency.code + " - " + currency.name,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .clickable {
                                    isDropDownOpen = false
                                    if (isFromCurrencyPicker) {
                                        onAction(ExchangeActions.SelectedFrom(index))
                                    } else {
                                        onAction(ExchangeActions.SelectedTo(index))
                                    }
                                }
                                .padding(horizontal = 20.dp, vertical = 5.dp)
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
        InputSection(
            modifier = Modifier.fillMaxHeight(0.6f),
            onAction = onAction
        )
    }

}

@Composable
fun InputSection(modifier: Modifier = Modifier, onAction: (ExchangeActions) -> Unit) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        {
            Input(text = "1", color = MaterialTheme.colorScheme.onBackground) {
                onAction(ExchangeActions.Input(it))
            }
            Input(text = "2", color = MaterialTheme.colorScheme.onBackground) {
                onAction(ExchangeActions.Input(it))
            }
            Input(text = "3", color = MaterialTheme.colorScheme.onBackground) {
                onAction(ExchangeActions.Input(it))
            }
            Input(text = "C", color = Color.Red) {
                onAction(ExchangeActions.Clear)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        {
            Input(text = "4", color = MaterialTheme.colorScheme.onBackground) {
                onAction(ExchangeActions.Input(it))
            }
            Input(text = "5", color = MaterialTheme.colorScheme.onBackground) {
                onAction(ExchangeActions.Input(it))
            }
            Input(text = "6", color = MaterialTheme.colorScheme.onBackground) {
                onAction(ExchangeActions.Input(it))
            }
            Input() {
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        {
            Input(text = "7", color = MaterialTheme.colorScheme.onBackground) {
                onAction(ExchangeActions.Input(it))
            }
            Input(text = "8", color = MaterialTheme.colorScheme.onBackground) {
                onAction(ExchangeActions.Input(it))
            }
            Input(text = "9", color = MaterialTheme.colorScheme.onBackground) {
                onAction(ExchangeActions.Input(it))
            }
            Input() {
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        {
            Input(text = "00", color = MaterialTheme.colorScheme.onBackground) {
                onAction(ExchangeActions.Input(it))
            }
            Input(text = "0", color = MaterialTheme.colorScheme.onBackground) {
                onAction(ExchangeActions.Input(it))
            }
            Input(text = ".", color = MaterialTheme.colorScheme.onBackground) {
                onAction(ExchangeActions.Input(it))
            }
            Input(
                icon = Icons.AutoMirrored.Rounded.Backspace,
                color = MaterialTheme.colorScheme.primary
            ) {
                onAction(ExchangeActions.Delete)
            }
        }
    }
}

@Composable
fun RowScope.Input(
    modifier: Modifier = Modifier, text: String? = null, icon: ImageVector? = null,
    color: Color = MaterialTheme.colorScheme.background,
    onClick: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable {
                onClick(text ?: "")
            },
        contentAlignment = Alignment.Center
    ) {
        if (text != null) {
            Text(
                text = text,
                fontSize = 35.sp,
                color = color,
            )
        } else if (icon != null) {
            Icon(
                imageVector = icon, contentDescription = null,
                tint = color, modifier = Modifier.size(30.dp)
            )

        }
    }
}


@Composable
fun ExchangeSection(
    modifier: Modifier = Modifier,
    state: ExchangeState,
    onOpenCurrencyPicker: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.clickable { onOpenCurrencyPicker(true) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = state.from.code,
                    fontSize = 30.sp,
                )
                Spacer(modifier = Modifier.width(3.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = "Selected from currency",
                )
            }
            Text(
                text = state.amount,
                fontSize = 30.sp,
                textAlign = TextAlign.End,
                maxLines = 1,
                color = MaterialTheme.colorScheme.primary,
                overflow = TextOverflow.Ellipsis
//                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.clickable { onOpenCurrencyPicker(false) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = state.to.code,
                    fontSize = 30.sp,
                )
                Spacer(modifier = Modifier.width(3.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = "Selected to currency",
                )

            }
            Text(
                text = state.result,
                fontSize = 30.sp,
                textAlign = TextAlign.End,
                maxLines = 1,
                color = MaterialTheme.colorScheme.primary,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Preview
@Composable
private fun ExchangeScreenPreview() {
    MyApplicationTheme {
        ExchangeScreen(
            state = ExchangeState(),
            onAction = {}
        )
    }
}