# Codex task 01 — Android video foundation

Continue the existing Kotlin/Compose project without replacing its working structure.

1. Verify Gradle sync and build with a compatible JDK 17 / Android SDK. Report exact commands and results.
2. Add a video metadata screen with duration, dimensions, rotation, and frame rate (when available).
3. Implement a MediaExtractor/MediaCodec single-frame preview with correct rotation and cleanup.
4. Add a processing interface `AnimeProcessor` with a real-model-required contract; do not present ordinary filters as AI.
5. Add instrumentation/unit tests where practical.
6. Keep UI in Russian, dark graphite styling, and avoid unnecessary screens.
7. Document real results and limitations. Do not claim APK built unless build succeeds.

Acceptance: video selection and metadata/preview work on Android; no internet or root required.
