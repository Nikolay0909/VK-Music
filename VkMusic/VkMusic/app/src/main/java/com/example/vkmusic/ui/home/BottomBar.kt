package com.example.vkmusic.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.ChildCare
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.vkmusic.ui.components.CircleIconButton
import com.example.vkmusic.ui.components.CoverImage

enum class BottomTab(val label: String, val icon: ImageVector, val selectedIcon: ImageVector = icon) {
    Home("Главная", Icons.Outlined.MusicNote, Icons.Filled.MusicNote),
    Explore("Обзор", Icons.Outlined.Explore),
    BooksAndShows("Книги и шоу", Icons.Outlined.Mic),
    Kids("Детям", Icons.Outlined.ChildCare),
    Library("Моя музыка", Icons.Outlined.Favorite),
}

/** Мини-плеер (появляется после первого воспроизведения) + нижняя навигация на 5 вкладок */
@Composable
fun VkBottomBar(
    nowPlaying: NowPlaying?,
    isPlaying: Boolean,
    selectedTab: BottomTab,
    onTogglePlay: () -> Unit,
    onTab: (BottomTab) -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.surface)
            .navigationBarsPadding(),
    ) {
        HorizontalDivider(color = colors.outlineVariant)

        AnimatedVisibility(
            visible = nowPlaying != null,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),
        ) {
            if (nowPlaying != null) {
                MiniPlayer(nowPlaying, isPlaying, onTogglePlay)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 6.dp),
        ) {
            BottomTab.entries.forEach { tab ->
                val selected = tab == selectedTab
                val tint = if (selected) colors.primary else colors.onSurfaceVariant
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(role = Role.Tab) { onTab(tab) }
                        .padding(vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Icon(
                        imageVector = if (selected) tab.selectedIcon else tab.icon,
                        contentDescription = null,
                        tint = tint,
                        modifier = Modifier.size(22.dp),
                    )
                    Text(
                        tab.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = tint,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Composable
private fun MiniPlayer(nowPlaying: NowPlaying, isPlaying: Boolean, onToggle: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.surfaceContainerLow)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                CoverImage(nowPlaying.cover, null, Modifier.size(40.dp))
                Column {
                    Text(nowPlaying.title, style = MaterialTheme.typography.labelMedium, color = colors.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(nowPlaying.subtitle, style = MaterialTheme.typography.labelSmall, color = colors.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            CircleIconButton(
                icon = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = if (isPlaying) "Пауза" else "Играть",
                size = 36.dp,
                iconSize = 24.dp,
                tint = colors.onSurface,
                onClick = onToggle,
            )
        }
        HorizontalDivider(color = colors.outlineVariant)
    }
}
