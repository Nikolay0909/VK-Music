package com.example.vkmusic.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.GraphicEq
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vkmusic.data.Art
import com.example.vkmusic.data.Friend
import com.example.vkmusic.data.ImageModel
import com.example.vkmusic.data.MockData
import com.example.vkmusic.data.Podcast
import com.example.vkmusic.data.Release
import com.example.vkmusic.data.ReleaseFilter
import com.example.vkmusic.data.ReleaseType
import com.example.vkmusic.data.Track
import com.example.vkmusic.ui.components.CircleIconButton
import com.example.vkmusic.ui.components.CoverImage
import com.example.vkmusic.ui.components.PillButton
import com.example.vkmusic.ui.components.SaveButton
import com.example.vkmusic.ui.components.SeeAllLink
import com.example.vkmusic.ui.components.scaleClick
import com.example.vkmusic.ui.theme.VkBrand
import com.example.vkmusic.ui.theme.VkText

private val ScreenPadding = 16.dp

// =============================================================================================
// Промо-баннер «Целый месяц — 0 ₽»
// =============================================================================================

@Composable
fun PromoBanner(onConnect: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .padding(horizontal = ScreenPadding)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(Brush.horizontalGradient(listOf(colors.primary, VkBrand.Purple, VkBrand.Magenta))),
    ) {
        Box(Modifier.matchParentSize().background(colors.onSurface.copy(alpha = 0.20f)))
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Column {
                    Text("Целый месяц — 0 ₽", style = MaterialTheme.typography.titleMedium, color = colors.onPrimary)
                    Text("с подпиской VK Музыка", style = MaterialTheme.typography.bodySmall, color = colors.onPrimary)
                }
                PillButton(
                    text = "Подключить",
                    containerColor = colors.surface,
                    contentColor = colors.onSurface,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                    onClick = onConnect,
                )
            }
            Box(
                modifier = Modifier.size(64.dp).background(Color.White.copy(alpha = 0.20f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.Headphones, contentDescription = null, tint = colors.onPrimary, modifier = Modifier.size(32.dp))
            }
        }
    }
}

// =============================================================================================
// Быстрый доступ: Мои треки / Недавнее / Плейлист дня
// =============================================================================================

@Composable
fun QuickAccessRow(tracksCount: Int, onPlayDaily: () -> Unit) {
    Row(
        modifier = Modifier.padding(horizontal = ScreenPadding),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        QuickCard(Art.Cover5, "Мои треки", "$tracksCount всего", Modifier.weight(1f), onClick = null)
        QuickCard(Art.Cover6, "Недавнее", "14 треков", Modifier.weight(1f), onClick = null)
        QuickCard(Art.Miyagi, "Плейлист дня", "Для вас", Modifier.weight(1f), onClick = onPlayDaily)
    }
}

@Composable
private fun QuickCard(
    cover: ImageModel,
    title: String,
    caption: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)?,
) {
    val colors = MaterialTheme.colorScheme
    val shape = MaterialTheme.shapes.medium
    Column(
        modifier = modifier
            .clip(shape)
            .background(colors.surfaceContainer)
            .then(if (onClick != null) Modifier.clickable(role = Role.Button, onClick = onClick) else Modifier)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CoverImage(cover, title, Modifier.fillMaxWidth().aspectRatio(1f))
        Column {
            Text(title, style = MaterialTheme.typography.labelMedium, color = colors.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(caption, style = MaterialTheme.typography.labelSmall, color = colors.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

// =============================================================================================
// Новые релизы
// =============================================================================================

@Composable
fun NewReleasesSection(
    filter: ReleaseFilter,
    savedIds: Set<String>,
    onFilter: (ReleaseFilter) -> Unit,
    onPlay: (title: String, subtitle: String, cover: ImageModel) -> Unit,
    onToggleSave: (id: String, title: String) -> Unit,
    onSeeAll: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val visible = MockData.releases.filter { filter.matches(it) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.padding(horizontal = ScreenPadding).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Новые релизы", style = MaterialTheme.typography.headlineMedium, color = colors.onSurface)
                    Text(
                        "6",
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.onSecondaryContainer,
                        modifier = Modifier
                            .background(colors.secondaryContainer, CircleShape)
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                    )
                }
                Text("Свежие треки ваших артистов", style = MaterialTheme.typography.bodySmall, color = colors.onSurfaceVariant)
            }
            SeeAllLink(onClick = onSeeAll)
        }

        // Фильтры
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = ScreenPadding),
        ) {
            items(ReleaseFilter.entries, key = { it.name }) { f ->
                val selected = f == filter
                PillButton(
                    text = f.label,
                    icon = if (f == ReleaseFilter.Subscriptions) Icons.Outlined.NotificationsActive else null,
                    iconSize = 15.dp,
                    // В макете иконка всегда синяя и на выбранном (синем) чипе пропадает — здесь белая
                    iconTint = if (selected) colors.onPrimary else colors.primary,
                    containerColor = if (selected) colors.primary else colors.surfaceContainer,
                    contentColor = if (selected) colors.onPrimary else colors.onSurface,
                    onClick = { onFilter(f) },
                )
            }
        }

        // Карусель карточек
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = ScreenPadding, end = ScreenPadding, bottom = 4.dp),
        ) {
            itemsIndexed(visible, key = { _, r -> r.id }) { index, release ->
                ReleaseCard(
                    release = release,
                    highlighted = index == 0,
                    saved = release.id in savedIds,
                    onPlay = { onPlay(release.playTitle, release.playSubtitle, release.cover) },
                    onToggleSave = { onToggleSave(release.id, release.title) },
                )
            }
        }
    }
}

@Composable
private fun ReleaseCard(
    release: Release,
    highlighted: Boolean,
    saved: Boolean,
    onPlay: () -> Unit,
    onToggleSave: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val shape = MaterialTheme.shapes.large
    Column(
        modifier = Modifier
            .width(240.dp)
            .clip(shape)
            .background(colors.surfaceContainerLow)
            .border(1.dp, colors.outlineVariant, shape)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(
                release.badge,
                style = MaterialTheme.typography.labelSmall,
                color = if (release.type == ReleaseType.Single) colors.primary else VkBrand.Purple,
            )
            Text(release.meta, style = VkText.MonoSm, color = colors.onSurfaceVariant)
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CoverImage(release.cover, release.title, Modifier.size(56.dp), MaterialTheme.shapes.medium)
            Column(Modifier.weight(1f)) {
                Text(release.title, style = MaterialTheme.typography.titleMedium, color = colors.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(release.artists, style = MaterialTheme.typography.bodySmall, color = colors.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PillButton(
                text = "Слушать",
                icon = Icons.Filled.PlayArrow,
                containerColor = if (highlighted) colors.primary else colors.surface,
                contentColor = if (highlighted) colors.onPrimary else colors.onSurface,
                borderColor = if (highlighted) null else colors.outlineVariant,
                onClick = onPlay,
            )
            SaveButton(saved = saved, title = release.title, onClick = onToggleSave, iconSize = 18.dp, bordered = true)
        }
    }
}

// =============================================================================================
// «Только в VK Музыке» — друзья слушают
// =============================================================================================

@Composable
fun FriendsSection(
    selectedIndex: Int,
    savedIds: Set<String>,
    onSelect: (Int) -> Unit,
    onPlayFriend: () -> Unit,
    onPlayTrack: (title: String, subtitle: String, cover: ImageModel) -> Unit,
    onToggleSave: (id: String, title: String) -> Unit,
    onSeeAll: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val friend = MockData.friends[selectedIndex]

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.padding(horizontal = ScreenPadding).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier
                        .background(Brush.horizontalGradient(listOf(colors.primary, VkBrand.Purple)), CircleShape)
                        .padding(horizontal = 10.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(Icons.Outlined.GraphicEq, contentDescription = null, tint = colors.onPrimary, modifier = Modifier.size(14.dp))
                    Text("ТОЛЬКО В VK МУЗЫКЕ", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.3.sp), color = colors.onPrimary)
                }
                Text("Слушают ваши друзья", style = MaterialTheme.typography.headlineMedium, color = colors.onSurface)
            }
            SeeAllLink(onClick = onSeeAll)
        }

        // Аватары с кольцом совпадения вкусов
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = ScreenPadding, vertical = 4.dp),
        ) {
            itemsIndexed(MockData.friends, key = { _, f -> f.name }) { index, f ->
                FriendAvatar(friend = f, selected = index == selectedIndex, onClick = { onSelect(index) })
            }
        }

        // Карточка выбранного друга
        val cardShape = MaterialTheme.shapes.extraLarge
        Column(
            modifier = Modifier
                .padding(horizontal = ScreenPadding)
                .fillMaxWidth()
                .clip(cardShape)
                .background(colors.surface)
                .border(1.dp, colors.outlineVariant, cardShape),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.linearGradient(listOf(colors.primary, VkBrand.Purple, VkBrand.Magenta))),
            ) {
                Box(Modifier.matchParentSize().background(colors.onSurface.copy(alpha = 0.20f)))
                Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(friend.match, style = MaterialTheme.typography.labelLarge, color = colors.onPrimary)
                            Text("совпадение вкусов", style = MaterialTheme.typography.bodySmall, color = colors.onPrimary)
                        }
                        Text(
                            friend.status,
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.onPrimary,
                            modifier = Modifier
                                .background(colors.onSurface.copy(alpha = 0.30f), CircleShape)
                                .padding(horizontal = 8.dp, vertical = 2.dp),
                        )
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(friend.playlist, style = MaterialTheme.typography.titleMedium, color = colors.onPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text(friend.name, style = MaterialTheme.typography.bodySmall, color = colors.onPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        PillButton(
                            text = "Слушать",
                            icon = Icons.Filled.PlayArrow,
                            iconSize = 18.dp,
                            containerColor = colors.surface,
                            contentColor = colors.onSurface,
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                            onClick = onPlayFriend,
                        )
                    }
                }
            }
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                friend.tracks.forEach { track ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clip(MaterialTheme.shapes.small)
                                .clickable(role = Role.Button) { onPlayTrack(track.title, track.playSubtitle, track.cover) },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            CoverImage(track.cover, track.title, Modifier.size(40.dp))
                            Column {
                                Text(track.title, style = MaterialTheme.typography.labelMedium, color = colors.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(track.subtitle, style = MaterialTheme.typography.labelSmall, color = colors.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }
                        SaveButton(
                            saved = track.id in savedIds,
                            title = track.title,
                            iconSize = 18.dp,
                            onClick = { onToggleSave(track.id, track.title) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FriendAvatar(friend: Friend, selected: Boolean, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val ring: Brush = if (selected) {
        // bg-gradient-to-tr: снизу-слева → вверх-вправо
        Brush.linearGradient(
            colors = listOf(VkBrand.Blue, VkBrand.Purple, VkBrand.Magenta),
            start = Offset(0f, Float.POSITIVE_INFINITY),
            end = Offset(Float.POSITIVE_INFINITY, 0f),
        )
    } else {
        SolidColor(colors.surfaceContainerHigh)
    }

    Column(
        modifier = Modifier
            .width(64.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(role = Role.Button, onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(Modifier.background(ring, CircleShape).padding(4.dp)) {
            CoverImage(
                model = friend.avatar,
                contentDescription = friend.shortName,
                shape = CircleShape,
                modifier = Modifier.size(56.dp).border(2.dp, colors.surface, CircleShape),
            )
            if (friend.isLive) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 2.dp, end = 2.dp)
                        .size(14.dp)
                        .background(colors.primary, CircleShape)
                        .border(2.dp, colors.surface, CircleShape),
                )
            }
            Text(
                friend.match,
                style = MaterialTheme.typography.labelSmall,
                color = if (selected) colors.onPrimary else colors.onSurface,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 6.dp)
                    .background(if (selected) colors.primary else colors.surface, CircleShape)
                    .then(if (selected) Modifier else Modifier.border(1.dp, colors.outlineVariant, CircleShape))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
            )
        }
        Text(
            friend.shortName,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) colors.onSurface else colors.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 2.dp),
        )
    }
}

// =============================================================================================
// Похоже на прослушанное / Открытия
// =============================================================================================

@Composable
fun SimilarSection(
    tab: SimilarTab,
    savedIds: Set<String>,
    onTab: (SimilarTab) -> Unit,
    onPlay: (title: String, subtitle: String, cover: ImageModel) -> Unit,
    onToggleSave: (id: String, title: String) -> Unit,
    onToast: (String) -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val tracks = if (tab == SimilarTab.Similar) MockData.similar else MockData.discoveries

    Column(
        modifier = Modifier.padding(horizontal = ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(
                if (tab == SimilarTab.Similar) "Похоже на прослушанное" else "Открытия",
                style = MaterialTheme.typography.headlineMedium,
                color = colors.onSurface,
                modifier = Modifier.weight(1f),
            )
            CircleIconButton(
                icon = Icons.Outlined.ChevronRight,
                contentDescription = "Показать все рекомендации",
                size = 28.dp,
                iconSize = 18.dp,
                tint = colors.onSurfaceVariant,
                background = colors.surfaceContainer,
                onClick = { onToast("Открыт полный список рекомендаций") },
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val similarSelected = tab == SimilarTab.Similar
            PillButton(
                text = "Похожее",
                containerColor = if (similarSelected) colors.primary else colors.surfaceContainer,
                contentColor = if (similarSelected) colors.onPrimary else colors.onSurface,
                onClick = { onTab(SimilarTab.Similar) },
            )
            PillButton(
                text = "Открытия",
                icon = Icons.Outlined.AutoAwesome,
                iconSize = 15.dp,
                iconTint = if (!similarSelected) colors.onPrimary else colors.primary,
                containerColor = if (!similarSelected) colors.primary else colors.surfaceContainer,
                contentColor = if (!similarSelected) colors.onPrimary else colors.onSurface,
                onClick = { onTab(SimilarTab.Discoveries) },
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            tracks.forEach { track ->
                TrackRow(
                    track = track,
                    saved = track.id in savedIds,
                    onPlay = { onPlay(track.title, track.playSubtitle, track.cover) },
                    onToggleSave = { onToggleSave(track.id, track.title) },
                    onMore = { onToast("Действия с треком") },
                )
            }
        }
    }
}

@Composable
private fun TrackRow(
    track: Track,
    saved: Boolean,
    onPlay: () -> Unit,
    onToggleSave: () -> Unit,
    onMore: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clip(MaterialTheme.shapes.small)
                .clickable(role = Role.Button, onClick = onPlay),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            CoverImage(track.cover, track.title, Modifier.size(48.dp), MaterialTheme.shapes.medium)
            Column {
                Text(track.title, style = MaterialTheme.typography.labelLarge, color = colors.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(track.subtitle, style = MaterialTheme.typography.bodySmall, color = colors.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            SaveButton(saved = saved, title = track.title, onClick = onToggleSave)
            CircleIconButton(
                icon = Icons.Outlined.MoreVert,
                contentDescription = "Ещё",
                size = 32.dp,
                iconSize = 20.dp,
                tint = colors.onSurfaceVariant,
                onClick = onMore,
            )
        }
    }
}

// =============================================================================================
// Популярные подкасты
// =============================================================================================

@Composable
fun PodcastSection(
    podcast: Podcast,
    subscribed: Boolean,
    onPlay: () -> Unit,
    onToggleSubscribe: () -> Unit,
    onToast: (String) -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitleRow("Популярные подкасты", "Все подкасты") { onToast("Все подкасты") }

        val shape = MaterialTheme.shapes.extraLarge
        Column(
            modifier = Modifier
                .padding(horizontal = ScreenPadding)
                .fillMaxWidth()
                .clip(shape)
                .background(colors.surface)
                .border(1.dp, colors.outlineVariant, shape)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            CoverImage(podcast.cover, podcast.title, Modifier.size(128.dp), MaterialTheme.shapes.large)
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(podcast.title, style = MaterialTheme.typography.titleMedium, color = colors.onSurface)
                Text(
                    podcast.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }
            Row(Modifier.padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                PillButton(
                    text = "Слушать",
                    icon = Icons.Filled.PlayArrow,
                    iconSize = 18.dp,
                    containerColor = colors.surfaceContainer,
                    contentColor = colors.onSurface,
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    onClick = onPlay,
                )
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 36.dp)
                        .clip(CircleShape)
                        .background(colors.surfaceContainer)
                        .clickable(role = Role.Button, onClick = onToggleSubscribe),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = if (subscribed) Icons.Outlined.Check else Icons.Outlined.Add,
                        contentDescription = "Подписаться на подкаст",
                        tint = if (subscribed) colors.primary else colors.onSurface,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }
    }
}

// =============================================================================================
// Собрано редакцией
// =============================================================================================

@Composable
fun EditorialSection(
    onPlay: (title: String, subtitle: String, cover: ImageModel) -> Unit,
    onToast: (String) -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitleRow("Собрано редакцией", "Все подборки редакции") { onToast("Все подборки редакции") }
        Row(
            modifier = Modifier.padding(horizontal = ScreenPadding),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            MockData.editorial.forEach { pick ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .scaleClick { onPlay(pick.title, "VK Музыка · Редакция", pick.cover) },
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    CoverImage(pick.cover, pick.title, Modifier.fillMaxWidth().aspectRatio(1f), MaterialTheme.shapes.large)
                    Column {
                        Text(pick.title, style = MaterialTheme.typography.labelLarge, color = colors.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Text("VK Музыка", style = MaterialTheme.typography.bodySmall, color = colors.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitleRow(title: String, chevronDescription: String, onChevron: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = Modifier.padding(horizontal = ScreenPadding).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, style = MaterialTheme.typography.headlineMedium, color = colors.onSurface)
        CircleIconButton(
            icon = Icons.Outlined.ChevronRight,
            contentDescription = chevronDescription,
            size = 28.dp,
            iconSize = 18.dp,
            tint = colors.onSurfaceVariant,
            background = colors.surfaceContainer,
            onClick = onChevron,
        )
    }
}

// =============================================================================================
// «Информация о контенте»
// =============================================================================================

@Composable
fun ContentInfoLink(onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .padding(start = ScreenPadding, end = ScreenPadding, bottom = 8.dp)
            .clip(MaterialTheme.shapes.small)
            .clickable(role = Role.Button, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(Icons.Outlined.Info, contentDescription = null, tint = colors.onSurfaceVariant, modifier = Modifier.size(18.dp))
        Text("Информация о контенте", style = MaterialTheme.typography.bodySmall, color = colors.onSurfaceVariant)
        Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = colors.onSurfaceVariant, modifier = Modifier.size(16.dp))
    }
}
