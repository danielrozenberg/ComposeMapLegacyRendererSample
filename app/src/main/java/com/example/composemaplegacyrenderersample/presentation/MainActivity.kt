/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.example.composemaplegacyrenderersample.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.Text
import com.example.composemaplegacyrenderersample.presentation.theme.ComposeMapLegacyRendererSampleTheme
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapsInitializer
import com.google.maps.android.compose.GoogleMap
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    val context = LocalContext.current

    var renderer by remember {
        mutableStateOf<MapsInitializer.Renderer?>(null)
    }

    LaunchedEffect(Unit) {
        delay(2000)

        MapsInitializer.initialize(
            context,
            MapsInitializer.Renderer.LATEST,
        ) { renderer = it }
    }

    ComposeMapLegacyRendererSampleTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            renderer?.let {
                GoogleMap(
                    googleMapOptionsFactory = {
                        GoogleMapOptions()
                            .mapId("1a2b3")
                    },
                )
            }

            Text(
                text = when (renderer) {
                    MapsInitializer.Renderer.LATEST -> "Latest renderer"
                    MapsInitializer.Renderer.LEGACY -> "Legacy renderer"
                    else -> "Not initialized"
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 32.dp),
                color = Color.Red,
                textAlign = TextAlign.Center,
            )
        }
    }
}