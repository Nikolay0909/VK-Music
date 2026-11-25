package com.example.vkmusic.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vkmusic.data.Art
import com.example.vkmusic.data.MockData
import com.example.vkmusic.ui.components.negativeTopMargin

private const val PodcastId = "podcast_upal_podnyalsya"

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val colors = MaterialTheme.colorScheme

    Scaffold(
        containerColor = colors.surface,
        // Края экрана обрабатываем сами: hero заходит под статус-бар, нижняя панель — под навигацию
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            VkBottomBar(
                nowPlaying = state.nowPlaying,
                isPlaying = state.isPlaying,
                selectedTab = BottomTab.Home,
                onTogglePlay = viewModel::togglePlayPause,
                onTab = { tab ->
                    // Остальные вкладки пока заглушки, как в макете
                    if (tab != BottomTab.Home) viewModel.showToast("Раздел «${tab.label}»")
                },
            )
        },
    ) { innerPadding ->
        Box(Modifier.fillMaxSize().padding(bottom = innerPadding.calculateBottomPadding())) {
            HomeContent(state = state, viewModel = viewModel)
            VkToast(
                message = state.toast,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
            )
        }
    }
}

@Composable
private fun HomeContent(state: HomeUiState, viewModel: HomeViewModel) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        HeroSection(
            state = state,
            onToggleMix = viewModel::toggleVkMix,
            onSelectPreset = viewModel::selectPreset,
            onSelectArtist = viewModel::selectArtist,
            onToast = viewModel::showToast,
        )

        // Белый «лист» с закруглёнными верхними углами, заезжает на hero на 16dp (-mt-4)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .negativeTopMargin(16.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(colors.surface)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            PromoBanner(onConnect = { viewModel.showToast("Подписка оформлена: 1 месяц бесплатно") })

            QuickAccessRow(
                tracksCount = state.tracksCount,
                onPlayDaily = { viewModel.playTrack("Плейлист дня", "Персональный микс", Art.Miyagi) },
            )

            NewReleasesSection(
                filter = state.releaseFilter,
                savedIds = state.savedIds,
                onFilter = viewModel::selectReleaseFilter,
                onPlay = viewModel::playTrack,
                onToggleSave = viewModel::toggleSave,
                onSeeAll = { viewModel.showToast("Новые релизы") }, // TODO: переход на экран «Новые релизы»
            )

            FriendsSection(
                selectedIndex = state.selectedFriend,
                savedIds = state.savedIds,
                onSelect = viewModel::selectFriend,
                onPlayFriend = viewModel::playActiveFriend,
                onPlayTrack = viewModel::playTrack,
                onToggleSave = viewModel::toggleSave,
                onSeeAll = { viewModel.showToast("Показаны все совпадения с друзьями") },
            )

            SimilarSection(
                tab = state.similarTab,
                savedIds = state.savedIds,
                onTab = viewModel::selectSimilarTab,
                onPlay = viewModel::playTrack,
                onToggleSave = viewModel::toggleSave,
                onToast = viewModel::showToast,
            )

            PodcastSection(
                podcast = MockData.podcast,
                subscribed = PodcastId in state.savedIds,
                onPlay = {
                    viewModel.playTrack("Упал, поднялся · Выпуск 42", "Подкаст о бизнесе", MockData.podcast.cover)
                },
                onToggleSubscribe = { viewModel.toggleSave(PodcastId, MockData.podcast.title) },
                onToast = viewModel::showToast,
            )

            EditorialSection(onPlay = viewModel::playTrack, onToast = viewModel::showToast)

            ContentInfoLink(onClick = { viewModel.showToast("Правовая информация о контенте VK Музыки") })

            Spacer(Modifier.height(8.dp))
        }
    }
}

/** Тёмная «таблетка» с сообщением (#toast в макете). Текст сохраняется на время fade-out. */
@Composable
private fun VkToast(message: String?, modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    var lastMessage by remember { mutableStateOf("") }
    LaunchedEffect(message) { if (message != null) lastMessage = message }

    AnimatedVisibility(
        visible = message != null,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier,
    ) {
        Text(
            text = message ?: lastMessage,
            style = MaterialTheme.typography.labelMedium,
            color = colors.surface,
            maxLines = 1,
            modifier = Modifier
                .shadow(8.dp, CircleShape)
                .background(colors.onSurface, CircleShape)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        )
    }
}
