package com.example.vkmusic

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vkmusic.ui.home.HomeScreen
import com.example.vkmusic.ui.theme.VkMusicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            // Сверху тёмный hero → светлые иконки статус-бара
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            // Снизу белая панель → тёмные иконки навигации
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )
        setContent {
            VkMusicTheme {
                HomeScreen()
            }
        }
    }
}
