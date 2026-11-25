package com.example.vkmusic.data

/** Заглушки вместо API. Весь текст и порядок — как в макете. */
object MockData {

    const val InitialTracksCount = 382

    /** Треки, которые в макете уже добавлены в «Мои треки» (data-saved="true") */
    val InitiallySavedIds = setOf("one_dance")

    val wavePresets = listOf(
        WavePreset("По вкусу", "Музыкальные рекомендации для вас", Art.MumiTroll),
        WavePreset("Работа", "Фокус и продуктивность без лишних слов", Art.Miyagi),
        WavePreset("Тренировка", "Энергичный бит для спорта", Art.BigBabyTape),
        WavePreset("Хип-хоп", "Свежие биты и любимые куплеты", Art.BigBabyTape),
        WavePreset("Рок", "Гитары, лайвы и классика жанра", Art.Aria),
        WavePreset("Вечер", "Мягкая электроника и инди", Art.Cover6), // cover_drake_feat
    )

    val waveArtists = listOf(
        WaveArtist("Мумий Тролль", Art.MumiTroll, Art.MumiTroll),
        WaveArtist("Big Baby Tape", Art.BigBabyTape, Art.BigBabyTape),
        WaveArtist("Miyagi", Art.Miyagi, Art.Miyagi),
        WaveArtist("Ария", Art.Aria, Art.Aria),
        WaveArtist("INSTASAMKA", Art.Instasamka, Art.Instasamka),
    )

    val releases = listOf(
        Release(
            id = "chrome_heart", type = ReleaseType.Single, fromSubscription = true,
            badge = "Сингл · Сегодня", meta = "2:44",
            title = "Chrome Heart", artists = "Big Baby Tape, Aarne", cover = Art.BigBabyTape,
            playTitle = "Chrome Heart", playSubtitle = "Big Baby Tape, Aarne",
        ),
        Release(
            id = "echo_vershin", type = ReleaseType.Album, fromSubscription = true,
            badge = "Альбом · Пятница", meta = "11 треков",
            title = "Эхо вершин", artists = "Miyagi & Эндшпиль", cover = Art.Miyagi,
            playTitle = "Эхо вершин (Intro)", playSubtitle = "Miyagi & Эндшпиль",
        ),
        Release(
            id = "mayak_vladivostoka", type = ReleaseType.Single, fromSubscription = false,
            badge = "Сингл · Вчера", meta = "3:12",
            title = "Маяк Владивостока", artists = "Мумий Тролль", cover = Art.MumiTroll,
            playTitle = "Маяк Владивостока", playSubtitle = "Мумий Тролль",
        ),
    )

    private fun friendTrack(id: String, title: String, artist: String, cover: ImageModel, friendShort: String) =
        Track(id, title, artist, cover, playSubtitle = "$artist · Плейлист $friendShort")

    val friends = listOf(
        Friend(
            name = "Цыренов Жамьян", shortName = "Жамьян", match = "98%",
            status = "Слушает сейчас", isLive = true, playlist = "капец...", avatar = Art.Cover6,
            tracks = listOf(
                friendTrack("mockingbird", "Mockingbird", "Eminem", Art.Cover6, "Жамьяна"),
                friendTrack("za_dengi_da", "ЗА ДЕНЬГИ ДА", "INSTASAMKA", Art.Cover5, "Жамьяна"),
                friendTrack("soka_soka", "сока сока", "бразильский фонк", Art.BigBabyTape, "Жамьяна"),
            ),
        ),
        Friend(
            name = "Алина Соколова", shortName = "Алина", match = "91%",
            status = "Слушает сейчас", isLive = true, playlist = "Ночной неон", avatar = Art.Cover5,
            tracks = listOf(
                friendTrack("za_dengi_da", "ЗА ДЕНЬГИ ДА", "INSTASAMKA", Art.Cover5, "Алины"),
                friendTrack("one_dance", "One Dance", "Drake feat. WizKid", Art.Cover6, "Алины"),
                friendTrack("echo_vershin", "Эхо вершин", "Miyagi & Эндшпиль", Art.Miyagi, "Алины"),
            ),
        ),
        Friend(
            name = "Максим Волков", shortName = "Макс", match = "86%",
            status = "Был 12 мин назад", isLive = false, playlist = "Бас в дорогу", avatar = Art.BigBabyTape,
            tracks = listOf(
                friendTrack("chrome_heart", "Chrome Heart", "Big Baby Tape, Aarne", Art.BigBabyTape, "Макса"),
                friendTrack("ameli", "Ameli", "Big Baby Tape, Aarne, Toxi$", Art.BigBabyTape, "Макса"),
                friendTrack("echo_vershin", "Эхо вершин", "Miyagi & Эндшпиль", Art.Miyagi, "Макса"),
            ),
        ),
        Friend(
            name = "Дарья Ким", shortName = "Даша", match = "82%",
            status = "Слушает сейчас", isLive = true, playlist = "Инди и гитары", avatar = Art.MumiTroll,
            tracks = listOf(
                friendTrack("mayak_vladivostoka", "Маяк Владивостока", "Мумий Тролль", Art.MumiTroll, "Даши"),
                friendTrack("oskolki", "Осколки", "Ария", Art.Aria, "Даши"),
                friendTrack("vyshe_domov", "Выше домов", "Сироткин", Art.MumiTroll, "Даши"),
            ),
        ),
    )

    val similar = listOf(
        Track("ameli", "Ameli", "Big Baby Tape, Aarne, Toxi$", Art.BigBabyTape),
        Track("one_dance", "One Dance", "Drake feat. WizKid, Kyla", Art.Cover6),
    )

    val discoveries = listOf(
        Track("vyshe_domov", "Выше домов", "Сироткин · Новое для вас", Art.MumiTroll, playSubtitle = "Сироткин · Открытия"),
        Track("oskolki", "Осколки", "Ария · Похоже на ваш рок", Art.Aria, playSubtitle = "Ария · Открытия"),
    )

    val podcast = Podcast(
        title = "Упал, поднялся",
        description = "Истории про реальный бизнес, а не про «успешный успех».",
        cover = Art.Miyagi,
    )

    val editorial = listOf(
        EditorialPick("baby_cute", "Baby Cute: как девчонки...", Art.Cover5),
        EditorialPick("gavrilina", "Что слушает GAVRILINA", Art.BigBabyTape),
    )
}
