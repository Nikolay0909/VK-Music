package com.example.vkmusic.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkmusic.data.Art
import com.example.vkmusic.data.ImageModel
import com.example.vkmusic.data.MockData
import com.example.vkmusic.data.ReleaseFilter
import com.example.vkmusic.data.WaveArtist
import com.example.vkmusic.data.WavePreset
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Выбранная «волна» в hero-блоке: пресет или артист */
sealed interface WaveChoice {
    data class Preset(val name: String) : WaveChoice
    data class Artist(val name: String) : WaveChoice
}

data class NowPlaying(val title: String, val subtitle: String, val cover: ImageModel)

/** Что запустится по большой кнопке «Слушать VK Микс» */
data class MixState(
    val title: String,
    val playerSubtitle: String,
    val heroSubtitle: String,
    val cover: ImageModel,
)

enum class SimilarTab { Similar, Discoveries }

data class HomeUiState(
    val tracksCount: Int = MockData.InitialTracksCount,
    val savedIds: Set<String> = MockData.InitiallySavedIds,
    val nowPlaying: NowPlaying? = null,
    val isPlaying: Boolean = false,
    val mix: MixState = MixState(
        title = "VK Микс · Волна по вкусу",
        playerSubtitle = "Персональный поток",
        heroSubtitle = "Музыкальные рекомендации для вас",
        cover = Art.MumiTroll,
    ),
    val selectedWave: WaveChoice = WaveChoice.Preset("По вкусу"),
    val releaseFilter: ReleaseFilter = ReleaseFilter.All,
    val similarTab: SimilarTab = SimilarTab.Similar,
    val selectedFriend: Int = 0,
    val toast: String? = null,
)

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private var toastJob: Job? = null

    // ---- Плеер (пока только UI-состояние; реальное воспроизведение — Media3/ExoPlayer) ----

    fun playTrack(title: String, subtitle: String, cover: ImageModel) {
        _state.update {
            it.copy(nowPlaying = NowPlaying(title, subtitle, cover), isPlaying = true)
        }
    }

    fun togglePlayPause() = _state.update { it.copy(isPlaying = !it.isPlaying) }

    fun toggleVkMix() {
        val s = _state.value
        if (s.isPlaying) togglePlayPause() else playTrack(s.mix.title, s.mix.playerSubtitle, s.mix.cover)
    }

    // ---- Hero: пресеты и волны по артисту ----

    fun selectPreset(preset: WavePreset) {
        _state.update {
            it.copy(
                selectedWave = WaveChoice.Preset(preset.name),
                mix = MixState(
                    title = "VK Микс · ${preset.name}",
                    playerSubtitle = preset.subtitle,
                    heroSubtitle = preset.subtitle,
                    cover = preset.cover,
                ),
            )
        }
        startCurrentMix()
    }

    fun selectArtist(artist: WaveArtist) {
        _state.update {
            it.copy(
                selectedWave = WaveChoice.Artist(artist.name),
                mix = MixState(
                    title = "Волна: ${artist.name}",
                    playerSubtitle = "Микс по артисту",
                    heroSubtitle = "Волна по артисту · ${artist.name}",
                    cover = artist.cover,
                ),
            )
        }
        startCurrentMix()
    }

    private fun startCurrentMix() {
        val mix = _state.value.mix
        playTrack(mix.title, mix.playerSubtitle, mix.cover)
    }

    // ---- Секции ----

    fun selectReleaseFilter(filter: ReleaseFilter) = _state.update { it.copy(releaseFilter = filter) }

    fun selectSimilarTab(tab: SimilarTab) = _state.update { it.copy(similarTab = tab) }

    fun selectFriend(index: Int) = _state.update { it.copy(selectedFriend = index) }

    fun playActiveFriend() {
        val friend = MockData.friends[_state.value.selectedFriend]
        val first = friend.tracks.first()
        playTrack(first.title, first.playSubtitle, first.cover)
    }

    /** «+» / «✓» в Мои треки. Счётчик меняется на ±1, как в макете. */
    fun toggleSave(id: String, title: String) {
        val wasSaved = id in _state.value.savedIds
        _state.update {
            it.copy(
                savedIds = if (wasSaved) it.savedIds - id else it.savedIds + id,
                tracksCount = if (wasSaved) maxOf(0, it.tracksCount - 1) else it.tracksCount + 1,
            )
        }
        showToast(if (wasSaved) "Удалено из Моих треков" else "«$title» добавлено в Мои треки")
    }

    // ---- Toast ----

    fun showToast(message: String) {
        _state.update { it.copy(toast = message) }
        toastJob?.cancel()
        toastJob = viewModelScope.launch {
            delay(2200)
            _state.update { it.copy(toast = null) }
        }
    }
}
