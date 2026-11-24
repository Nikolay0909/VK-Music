package com.example.vkmusic.ui.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.QueueMusic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.vkmusic.data.MockData
import com.example.vkmusic.data.WaveArtist
import com.example.vkmusic.data.WavePreset
import com.example.vkmusic.ui.components.CircleIconButton
import com.example.vkmusic.ui.components.CoverImage
import com.example.vkmusic.ui.components.PillButton
import com.example.vkmusic.ui.components.scaleClick
import com.example.vkmusic.ui.theme.VkBrand
import com.example.vkmusic.ui.theme.VkOnSurface
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

private val OnHero = Color.White
private val ChipIdle = VkOnSurface.copy(alpha = 0.35f)

@Composable
fun HeroSection(
    state: HomeUiState,
    onToggleMix: () -> Unit,
    onSelectPreset: (WavePreset) -> Unit,
    onSelectArtist: (WaveArtist) -> Unit,
    onToast: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(listOf(VkBrand.HeroTop, VkBrand.Blue, VkBrand.HeroBottom))),
    ) {
        // Процедурная волна (в макете — <canvas id="vkWaveCanvas">)
        SilkWave(isPlaying = state.isPlaying, modifier = Modifier.matchParentSize())
        // Скрим, чтобы белый текст читался (bg-on-surface/30)
        Box(Modifier.matchParentSize().background(VkOnSurface.copy(alpha = 0.30f)))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 40.dp),
        ) {
            HeroHeader(onToast)
            Spacer(Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Spacer(Modifier.height(1.dp))
                PlayHalo(isPlaying = state.isPlaying, onClick = onToggleMix)

                Column(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Text(
                        "Слушать VK Микс",
                        style = MaterialTheme.typography.headlineLarge,
                        color = OnHero,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        state.mix.heroSubtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = OnHero,
                        textAlign = TextAlign.Center,
                    )
                }

                PillButton(
                    text = "Настроить",
                    icon = Icons.Outlined.Tune,
                    containerColor = ChipIdle,
                    contentColor = OnHero,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                    onClick = { onToast("Настройки VK Микса: баланс новинок и любимого") },
                )

                // Пресеты волны
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 4.dp),
                ) {
                    items(MockData.wavePresets, key = { it.name }) { preset ->
                        val selected = state.selectedWave == WaveChoice.Preset(preset.name)
                        PillButton(
                            text = preset.name,
                            containerColor = if (selected) Color.White else ChipIdle,
                            contentColor = if (selected) VkOnSurface else OnHero,
                            onClick = { onSelectPreset(preset) },
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        )
                    }
                }

                // Волна по артисту
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 4.dp),
                ) {
                    items(MockData.waveArtists, key = { it.name }) { artist ->
                        val selected = state.selectedWave == WaveChoice.Artist(artist.name)
                        ArtistChip(
                            artist = artist,
                            selected = selected,
                            onClick = { onSelectArtist(artist) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroHeader(onToast: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier.size(32.dp).background(Color.White.copy(alpha = 0.25f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.Person, contentDescription = null, tint = OnHero, modifier = Modifier.size(20.dp))
            }
            Text("Николай", style = MaterialTheme.typography.titleMedium, color = OnHero)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            CircleIconButton(
                icon = Icons.Outlined.Search,
                contentDescription = "Поиск музыки",
                tint = OnHero,
                onClick = { onToast("Поиск") },
            )
            CircleIconButton(
                icon = Icons.AutoMirrored.Outlined.QueueMusic,
                contentDescription = "Очередь воспроизведения",
                tint = OnHero,
                onClick = { onToast("Очередь воспроизведения") },
            )
        }
    }
}

@Composable
private fun ArtistChip(artist: WaveArtist, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .scaleClick(pressedScale = 0.97f, onClick = onClick)
            .background(if (selected) Color.White else ChipIdle, CircleShape)
            .padding(start = 6.dp, end = 12.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CoverImage(
            model = artist.avatar,
            contentDescription = artist.name,
            shape = CircleShape,
            modifier = Modifier.size(24.dp),
        )
        Text(
            artist.name,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) VkOnSurface else OnHero,
            maxLines = 1,
            overflow = TextOverflow.Clip,
        )
    }
}

// ---------------------------------------------------------------------------------------------
// Кнопка play с «дыханием» (vk-halo-ring)
// ---------------------------------------------------------------------------------------------

@Composable
private fun PlayHalo(isPlaying: Boolean, onClick: () -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        HaloRing(size = 96.dp, color = Color.White.copy(alpha = 0.15f), delayMs = 0, active = isPlaying)
        HaloRing(size = 80.dp, color = Color.White.copy(alpha = 0.25f), delayMs = 350, active = isPlaying)
        Box(
            modifier = Modifier
                .size(64.dp)
                .shadow(8.dp, CircleShape)
                .scaleClick(onClick = onClick)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = if (isPlaying) "Пауза VK Микс" else "Слушать VK Микс",
                tint = VkOnSurface,
                modifier = Modifier.size(34.dp),
            )
        }
    }
}

/** keyframes haloBreath: scale 1 → 1.12, opacity .85 → .4, 1.8s ease-in-out, только пока играет музыка */
@Composable
private fun HaloRing(size: Dp, color: Color, delayMs: Int, active: Boolean) {
    val progress: Float = if (active) {
        val transition = rememberInfiniteTransition(label = "halo")
        val value by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 900, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(delayMs),
            ),
            label = "haloProgress",
        )
        value
    } else {
        0f
    }
    Box(
        modifier = Modifier
            .size(size)
            .graphicsLayer {
                val s = 1f + 0.12f * progress
                scaleX = s
                scaleY = s
                alpha = 0.85f - 0.45f * progress
            }
            .background(color, CircleShape),
    )
}

// ---------------------------------------------------------------------------------------------
// «Живая» волна из вертикальных световых полос (перенос drawSilkWave из макета)
// ---------------------------------------------------------------------------------------------

@Composable
private fun SilkWave(isPlaying: Boolean, modifier: Modifier = Modifier) {
    var time by remember { mutableFloatStateOf(0.8f) }
    val playing by rememberUpdatedState(isPlaying)

    LaunchedEffect(Unit) {
        var last = 0L
        while (true) {
            withFrameNanos { ts ->
                if (last == 0L) last = ts
                val dt = min(0.05f, (ts - last) / 1_000_000_000f)
                last = ts
                // Играет — волна течёт быстрее (1.45 против 0.42)
                time += dt * (if (playing) 1.45f else 0.42f)
            }
        }
    }

    // `time` читается только в фазе рисования → перекомпозиций нет, перерисовывается лишь Canvas
    Canvas(modifier = modifier) { drawSilkWave(time) }
}

private fun DrawScope.drawSilkWave(t: Float) {
    val w = size.width
    val h = size.height
    val midY = h * 0.31f
    val amp = 42.dp.toPx()
    val baseHalfH = 66.dp.toPx()
    val barStep = 8.dp.toPx()
    val strokeW = 2.2.dp.toPx()

    var x = 4.dp.toPx()
    while (x < w) {
        val u = x / w
        val breath = 0.82f + 0.24f * sin(t * 0.72f + u * 1.5f)
        val y1 = sin(u * 4.2f + t * 0.85f + 0.4f) * 0.58f
        val y2 = sin(u * 2.3f - t * 0.62f + 1.8f) * 0.40f
        val y3 = cos(u * 6.3f + t * 1.20f - 0.5f) * 0.14f
        val cy = midY + (y1 + y2 + y3) * amp * breath
        val halfH = baseHalfH * (0.62f + 0.44f * sin(u * 3.5f - t * 0.88f + 0.9f))

        val yTop = cy - halfH
        val yBot = cy + halfH
        val brush = Brush.verticalGradient(
            0.00f to Color(43, 139, 255, 0),
            0.22f to Color(96, 178, 255, (0.58f * 255).toInt()),
            0.48f to Color(255, 255, 255, (0.65f * 255).toInt()),
            0.62f to Color(240, 0, 156, (0.68f * 255).toInt()),
            0.84f to Color(142, 36, 170, (0.48f * 255).toInt()),
            1.00f to Color(142, 36, 170, 0),
            startY = yTop,
            endY = yBot,
        )
        drawLine(
            brush = brush,
            start = Offset(x, yTop),
            end = Offset(x, yBot),
            strokeWidth = strokeW,
            cap = StrokeCap.Round,
        )
        x += barStep
    }
}
