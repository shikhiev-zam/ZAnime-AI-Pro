package pro.zanime.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedUri by remember { mutableStateOf<String?>(null) }
            val picker = rememberLauncherForActivityResult(
                ActivityResultContracts.GetContent()
            ) { uri -> selectedUri = uri?.toString() }
            MaterialTheme(colorScheme = darkColorScheme()) {
                Scaffold { padding ->
                    Column(
                        modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text("ZAnime AI Pro", style = MaterialTheme.typography.headlineMedium)
                        Text("Локальная аниме-стилизация видео")
                        Button(onClick = { picker.launch("video/*") }) {
                            Text("Выбрать видео")
                        }
                        Text(selectedUri ?: "Видео пока не выбрано")
                        Text("AI-обработка: ещё не подключена", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
        }
    }
}
