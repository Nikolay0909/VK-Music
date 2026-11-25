package com.example.vkmusic.data

/** Трек / элемент списка. [subtitle] — что показываем, [playSubtitle] — что пишем в мини-плеере. */
data class Track(
    val id: String,
    val title: String,
    val subtitle: String,
    val cover: ImageModel,
    val playSubtitle: String = subtitle,
)

enum class ReleaseType { Single, Album }

data class Release(
    val id: String,
    val type: ReleaseType,
    val fromSubscription: Boolean,
    val badge: String,      // «Сингл · Сегодня»
    val meta: String,       // «2:44» или «11 треков»
    val title: String,
    val artists: String,
    val cover: ImageModel,
    val playTitle: String,
    val playSubtitle: String,
)

enum class ReleaseFilter(val label: String) {
    All("Все"),
    Singles("Синглы"),
    Albums("Альбомы"),
    Subscriptions("Подписки");

    fun matches(release: Release): Boolean = when (this) {
        All -> true
        Singles -> release.type == ReleaseType.Single
        Albums -> release.type == ReleaseType.Album
        Subscriptions -> release.fromSubscription
    }
}

data class Friend(
    val name: String,
    val shortName: String,
    val match: String,          // «98%»
    val status: String,         // «Слушает сейчас» / «Был 12 мин назад»
    val isLive: Boolean,
    val playlist: String,
    val avatar: ImageModel,
    val tracks: List<Track>,
)

/** Пресет волны («По вкусу», «Работа»…) */
data class WavePreset(val name: String, val subtitle: String, val cover: ImageModel)

/** Волна по артисту */
data class WaveArtist(val name: String, val avatar: ImageModel, val cover: ImageModel)

data class Podcast(val title: String, val description: String, val cover: ImageModel)

data class EditorialPick(val id: String, val title: String, val cover: ImageModel)
