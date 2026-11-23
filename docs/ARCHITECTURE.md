# Architecture
Target: Vivo X200 Ultra, Android ARM64, offline, no root.
Phase 1: Kotlin/Compose UI and video picker.
Next: MediaExtractor/MediaCodec preview, ONNX inference, H.264 export with audio.
Stream frames to avoid retaining full video in memory.
Preserve timestamps, orientation and aspect ratio.
Do not pretend ordinary filters are neural models.
