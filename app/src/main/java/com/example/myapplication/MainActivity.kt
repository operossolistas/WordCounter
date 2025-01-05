package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CounterScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen() {
    var text by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isCountingWords by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val counter = remember { TextCounter() }
    val resultPrefix = stringResource(R.string.result_prefix)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(stringResource(R.string.input_hint)) },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RadioButton(
                selected = isCountingWords,
                onClick = { isCountingWords = true }
            )
            Text(stringResource(R.string.words_option))

            RadioButton(
                selected = !isCountingWords,
                onClick = { isCountingWords = false }
            )
            Text(stringResource(R.string.chars_option))
        }

        Button(
            onClick = {
                if (text.isEmpty()) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.empty_input_error),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val count = if (isCountingWords) {
                        counter.countWords(text)
                    } else {
                        counter.countCharacters(text)
                    }
                    result = "$resultPrefix $count"
                }
            }
        ) {
            Text(stringResource(R.string.count_button))
        }

        Text(
            text = result,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}