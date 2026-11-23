package pro.zanime.ai

import android.net.Uri
import android.os.Bundle
import android.media.MediaMetadataRetriever
import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContent {
   var details by remember { mutableStateOf("Выберите видео для анализа") }
   var preview by remember { mutableStateOf<Bitmap?>(null) }
   var busy by remember { mutableStateOf(false) }
   val picker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
    if (uri != null) {
     busy = true
     preview = null
     details = "Чтение видео…"
     lifecycleScope.launch {
      try {
       val result = withContext(Dispatchers.IO) {
        val retriever = MediaMetadataRetriever()
        try {
         retriever.setDataSource(this@MainActivity, uri)
         fun get(k: Int) = retriever.extractMetadata(k)?.toIntOrNull()
         val ms = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull() ?: error("Нет длительности")
         val w = get(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH) ?: error("Нет ширины")
         val h = get(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT) ?: error("Нет высоты")
         val rotation = get(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION) ?: 0
         require(ms > 0 && w > 0 && h > 0) { "Некорректное видео" }
         val frame = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC) ?: error("Нет первого кадра")
         Pair("Длительность: ${ms / 1000.0} сек\nРазрешение: ${w} × ${h}\nПоворот: ${rotation}°", frame)
        } finally { retriever.release() }
       }
       details = result.first
       preview = result.second
      } catch (e: Exception) {
       details = "Ошибка: ${e.localizedMessage ?: "не удалось прочитать видео"}"
      } finally { busy = false }
     }
    }
   }
   MaterialTheme(colorScheme = darkColorScheme()) {
    Scaffold { padding ->
     Column(
      modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp).verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(16.dp)
     ) {
      Text("ZAnime AI Pro", style = MaterialTheme.typography.headlineMedium)
      Text("Локальный анализ видео")
      Button(enabled = !busy, onClick = { picker.launch("video/*") }) { Text("Выбрать видео") }
      if (busy) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
      preview?.let {
       Image(bitmap = it.asImageBitmap(), contentDescription = "Первый кадр видео",
        modifier = Modifier.fillMaxWidth().heightIn(max = 320.dp), contentScale = ContentScale.Fit)
      }
      Text(details)
      Text("ИИ-стилизация ещё не подключена")
     }
    }
   }
  }
 }
}
