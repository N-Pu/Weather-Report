package com.project.weatherreport.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.project.weatherreport.R

// Set of Material typography styles to start with
val MyTypography = Typography(
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.evolventa_bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        shadow = Shadow(
                                offset = Offset(x = 0f, y = 6f),
                                blurRadius = 5f,
                                color = Color.Black.copy(alpha = 0.5f)
                            )
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserrat)),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        shadow = Shadow(
                                offset = Offset(x = 0f, y = 6f),
                                blurRadius = 5f,
                                color = Color.Black.copy(alpha = 0.5f)
                            )
    ),

)