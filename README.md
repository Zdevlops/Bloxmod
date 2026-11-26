# Bloxstrap Mobile (Kotlin + Jetpack Compose)

Aplikasi Android pendamping Roblox dengan antarmuka **Warm Minimalist Glassmorphism** dan **Liquid Glassy Floating Navigation Bar**.

## Fitur Utama
- **Auto-Deteksi Perangkat:** Membaca model HP, SoC, core CPU, RAM, refresh rate layar, dan suhu baterai secara otomatis.
- **Deteksi Beban CPU & GPU 100%:** Memantau beban di latar belakang dan memunculkan rekomendasi graphic setting otomatis saat terjadi bottleneck.
- **Roblox Graphics Quality (1–21) + Strict App Lock:** Mengatur `DFIntDebugFRMQualityLevelOverride` dari level 1 sampai 21 (default `5` Standar, maksimal `21` Mentok) dan mengunci `FFlagFixGraphicsQuality` agar setting bawaan in-game tidak berpengaruh.
- **FFlag Manager & ClientAppSettings.json:** Preset siap pakai (`Anti-Lag`, `Standar`, `Mentok Lv. 21`), **Add manual FFlag** (Bloxstrap/Fishstrap), Backup/Restore, serta ekspor JSON.
