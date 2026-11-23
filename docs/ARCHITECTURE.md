# ZAnime AI Pro — architecture

Target: Vivo X200 Ultra, Android ARM64, offline, no root.

## Phase 1
- Kotlin + Jetpack Compose UI.
- Android Storage Access Framework / GetContent video selection.
- MediaExtractor + MediaCodec decoder.
- ONNX Runtime Mobile for frame stylization (after model/license verification).
- MediaCodec H.264 encoder + MediaMuxer; preserve audio where compatible.
- Stream frames, never retain full video in memory.
- Preserve timestamps, rotation, and aspect ratio.

## Proposed modules (not implemented yet)
app, core-video, core-ai, core-processing, core-export, core-common.

## Engineering gates
1. Build and launch minimal UI.
2. Import video and inspect metadata.
3. Decode one frame and render preview.
4. Validate real ONNX model on a still frame.
5. Process a short clip and mux original audio.
6. Benchmark CPU, memory and thermals on device.
7. Evaluate QNN only after baseline works.

No fake AI processing: if a model is unavailable, disable processing with an explanatory message.
No network permission required for inference.
