package com.example.astragram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.astragram.ui.screens.MainScreen
import com.example.astragram.ui.theme.AstragramTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AstragramTheme {
                MainScreen()
            }
        }
    }
}
