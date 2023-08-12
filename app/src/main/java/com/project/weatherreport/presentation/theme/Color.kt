package com.project.weatherreport.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

//val Purple80 = Color(0xFFD0BCFF)
//val PurpleGrey80 = Color(0xFFCCC2DC)
//val Pink80 = Color(0xFFEFB8C8)
//
//val Purple40 = Color(0xFF6650a4)
//val PurpleGrey40 = Color(0xFF625b71)
//val Pink40 = Color(0xFF7D5260)


val Gray = Color(0x1A0F4C75)

val Gradient = Brush.linearGradient(
    0.0f to Color(136, 218, 255),
    1.0f to Color(219, 244, 255),
//        start = Offset.Zero,
//        end = Offset.Infinite
)

val ErrorGradient = Brush.linearGradient(
    0.0f to Color(255, 77, 92, 255),
    1.0f to Color(219, 244, 255),
//        start = Offset.Zero,
//        end = Offset.Infinite
)