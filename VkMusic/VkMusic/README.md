# VK Музыка — Android (Kotlin + Jetpack Compose)

Экран «Главная» перенесён из HTML-макета `vk (2).html` один в один: hero с анимированной
«волной», пресеты и волны по артистам, промо-баннер, быстрый доступ, новые релизы с фильтрами,
«Слушают ваши друзья», «Похоже / Открытия», подкаст, редакция, мини-плеер, нижняя навигация, toast.

## Как запустить
1. Android Studio (Ladybug или новее), JDK 17.
2. `File → Open` → папка `VkMusic`. Дождитесь Gradle Sync (Studio сама скачает Gradle 8.11.1).
3. Запустите на эмуляторе/устройстве (minSdk 26). Нужен интернет — картинки грузятся по URL (Coil).

Если Sync ругается на версии — обновите их через `Alt+Enter` (Studio предложит актуальные).

## Структура
```
app/src/main/java/com/example/vkmusic/
├─ MainActivity.kt            edge-to-edge, тема, HomeScreen
├─ data/
│  ├─ Models.kt               Track, Release, Friend, WavePreset …
│  ├─ MockData.kt             все тексты/списки из макета (замените на API)
│  └─ Art.kt                  URL картинок из макета + drawable
└─ ui/
   ├─ theme/                  Color / Type / Theme — токены из tailwind.config
   ├─ components/Common.kt    PillButton, SaveButton, CoverImage, scaleClick …
   └─ home/
      ├─ HomeViewModel.kt     вся JS-логика макета (toggleSave, playTrack, toast, фильтры…)
      ├─ HomeScreen.kt        Scaffold + прокрутка + toast
      ├─ HeroSection.kt       градиент, SilkWave (Canvas), halo-анимация, чипы
      ├─ Sections.kt          все секции белого «листа»
      └─ BottomBar.kt         мини-плеер + 5 вкладок
```

## Соответствие макет → Compose
| HTML / JS                          | Compose                                         |
|------------------------------------|-------------------------------------------------|
| tailwind colors / fontSize / radius| `ui/theme` (ColorScheme, Typography, Shapes)    |
| `<canvas>` + `drawSilkWave`        | `SilkWave` (Canvas + `withFrameNanos`)          |
| `.vk-halo-ring` keyframes          | `HaloRing` (`rememberInfiniteTransition`)       |
| `active:scale-95`                  | `Modifier.scaleClick`                           |
| `-mt-4` у белого листа             | `Modifier.negativeTopMargin`                    |
| `toggleSave`, `playTrack`, `showToast` … | методы `HomeViewModel` + `StateFlow`      |
| Material Symbols                   | `material-icons-extended`                       |

## Что сознательно упрощено / отличается
- **Звука нет**: плеер хранит только состояние UI. Для реального воспроизведения — Media3 (ExoPlayer) + MediaSessionService.
- **Картинки**: в макете это временные ссылки `googleusercontent` и один base64 (лежит в `res/drawable-nodpi/art_instasamka.jpg`). Подставьте свои URL/ресурсы в `Art.kt`.
- **Навигация**: только «Главная». Остальные вкладки и «Все ›» показывают toast, как заглушки в макете. Следующий шаг — Navigation Compose и остальные экраны (`NewReleasesFeed.html` и др.).
- **Баги макета исправлены**: иконка на выбранном синем чипе «Подписки»/«Открытия» была синей на синем (невидима) — теперь белая; одинаковые треки в разных секциях теперь синхронно меняют состояние «+ / ✓».
- **Шрифты**: Inter — вариативный, в `res/font/inter.ttf`. Для `mono-sm` вместо JetBrains Mono — системный моноширинный.
- Тексты захардкожены на русском; для релиза вынесите в `strings.xml`.
- Иконки-цели касания 32–36 dp ровно как в макете; для доступности можно увеличить до 48 dp.
- Лаунчер-иконки нет (используется стандартная).
