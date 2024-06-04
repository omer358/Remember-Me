package com.example.rememberme

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.rememberme.ui.theme.RememberMeTheme

@Composable
fun RememberMeApp(){
    RememberMeTheme{
        Surface{
            Text(text = "Hello")
        }
    }
}