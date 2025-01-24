package com.rperez.compose2d

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import com.rperez.compose2d.ui.theme.Compose2DTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose2DTheme {
                Game(resources)
            }
        }
    }
}

@Composable
fun Game(resources: Resources) {
    Box(modifier = Modifier.fillMaxSize()) {
        val asuka1 = BitmapFactory.decodeResource(resources, R.drawable.asuka01)
        val asuka2 = BitmapFactory.decodeResource(resources, R.drawable.asuka02)

        var h by remember { mutableIntStateOf(0) }
        var w by remember { mutableIntStateOf(0) }

        var asuka01Scaled by remember { mutableStateOf<Bitmap?>(null) }
        var asuka01Position by remember { mutableStateOf(Offset(0f, 0f)) }

        var asuka02Scaled by remember { mutableStateOf<Bitmap?>(null) }
        var asuka02Position by remember { mutableStateOf(Offset(0f, 0f)) }
        var asuka02XOffset by remember { mutableFloatStateOf(0f) }
        var asuka02YOffset by remember { mutableFloatStateOf(0f) }

        val asuka3 = BitmapFactory.decodeResource(resources, R.drawable.asuka03)
        var asuka03Scaled by remember { mutableStateOf<Bitmap?>(null) }
        var asuka03Position by remember { mutableStateOf(Offset(0f, 0f)) }
        var asuka03XOffset by remember { mutableFloatStateOf(0f) }
        var asuka03YOffset by remember { mutableFloatStateOf(0f) }

        val asuka4 = BitmapFactory.decodeResource(resources, R.drawable.asuka04)

        Canvas(modifier = Modifier
            .fillMaxSize()
            .onSizeChanged {
                h = it.height
                w = it.width
                val newWidth1 = w / 6
                val scaleFactor1 = newWidth1.toFloat() / asuka1.width
                val newHeight1 = (asuka1.height * scaleFactor1).toInt()
                asuka01Scaled =
                    Bitmap.createScaledBitmap(asuka1, newWidth1, newHeight1, true)
                asuka01Position = Offset(
                    x = (w - newWidth1) / 2f,
                    y = (h - newHeight1) / 2f
                )

                val newWidth2 = w / 5
                val scaleFactor2 = newWidth2.toFloat() / asuka2.width
                val newHeight2 = (asuka2.height * scaleFactor2).toInt()
                asuka02Scaled =
                    Bitmap.createScaledBitmap(asuka2, newWidth2, newHeight2, true)
                asuka02Position = Offset(
                    x = asuka02XOffset,
                    y = asuka02YOffset
                )

                val newWidth3 = w / 7
                val scaleFactor3 = newWidth3.toFloat() / asuka3.width
                val newHeight3 = (asuka3.height * scaleFactor3).toInt()
                asuka03Scaled =
                    Bitmap.createScaledBitmap(asuka3, newWidth3, newHeight3, true)
                asuka03Position = Offset(
                    x = asuka03XOffset,
                    y = asuka03YOffset
                )
            }
            .pointerInput(Unit) {
                coroutineScope {
                    launch {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                event.changes
                                    .firstOrNull()
                                    ?.let { pointerInputChange ->
                                        val touchX = pointerInputChange.position.x
                                        val touchY = pointerInputChange.position.y
                                        asuka01Scaled?.let {
                                            asuka01Position = Offset(
                                                x = touchX - it.width / 2f,
                                                y = touchY - it.height / 2f
                                            )
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        ) {
            asuka01Scaled?.let {
                val borderWidth = 8f

                drawImage(
                    image = it.asImageBitmap(),
                    topLeft = asuka01Position
                )

                // top
                drawLine(
                    color = Black,
                    start = Offset(
                        asuka01Position.x,
                        asuka01Position.y + borderWidth / 2
                    ),
                    end = Offset(
                        asuka01Position.x + it.width,
                        asuka01Position.y + borderWidth / 2
                    ),
                    strokeWidth = borderWidth
                )

                // bottom
                drawLine(
                    color = Black,
                    start = Offset(
                        asuka01Position.x,
                        asuka01Position.y + it.height - borderWidth / 2
                    ),
                    end = Offset(
                        asuka01Position.x + it.width,
                        asuka01Position.y + it.height - borderWidth / 2
                    ),
                    strokeWidth = borderWidth
                )

                // left
                drawLine(
                    color = Black,
                    start = Offset(
                        asuka01Position.x + borderWidth / 2,
                        asuka01Position.y
                    ),
                    end = Offset(
                        asuka01Position.x + borderWidth / 2,
                        asuka01Position.y + it.height
                    ),
                    strokeWidth = borderWidth
                )

                // right
                drawLine(
                    color = Black,
                    start = Offset(
                        asuka01Position.x + it.width - borderWidth / 2,
                        asuka01Position.y
                    ),
                    end = Offset(
                        asuka01Position.x + it.width - borderWidth / 2,
                        asuka01Position.y + it.height
                    ),
                    strokeWidth = borderWidth
                )
            }

            asuka02Scaled?.let {
                val borderWidth = 8f // Border width in pixels

                drawImage(
                    image = it.asImageBitmap(),
                    topLeft = Offset(
                        asuka02XOffset,
                        asuka02YOffset
                    )
                )

                // top
                drawLine(
                    color = Black,
                    start = Offset(
                        asuka02XOffset,
                        asuka02YOffset + borderWidth / 2
                    ),
                    end = Offset(
                        asuka02XOffset + it.width,
                        asuka02YOffset + borderWidth / 2
                    ),
                    strokeWidth = borderWidth
                )

                // bottom
                drawLine(
                    color = Black,
                    start = Offset(
                        asuka02XOffset,
                        asuka02YOffset + it.height - borderWidth / 2
                    ),
                    end = Offset(
                        asuka02XOffset + it.width,
                        asuka02YOffset + it.height - borderWidth / 2
                    ),
                    strokeWidth = borderWidth
                )

                // left
                drawLine(
                    color = Black,
                    start = Offset(
                        asuka02XOffset + borderWidth / 2,
                        asuka02YOffset
                    ),
                    end = Offset(
                        asuka02XOffset + borderWidth / 2,
                        asuka02YOffset + it.height
                    ),
                    strokeWidth = borderWidth
                )

                // right
                drawLine(
                    color = Black,
                    start = Offset(
                        asuka02XOffset + it.width - borderWidth / 2,
                        asuka02YOffset
                    ),
                    end = Offset(
                        asuka02XOffset + it.width - borderWidth / 2,
                        asuka02YOffset + it.height
                    ),
                    strokeWidth = borderWidth
                )
            }
        }

        LaunchedEffect(w) {
            if (asuka02Scaled != null) {
                val width = asuka02Scaled?.width ?: 0
                val screenWidth = w - width
                while (true) {
                    for (x in 0..screenWidth step 8) {
                        asuka02XOffset = x.toFloat()
                        delay(16L)
                    }
                    for (x in screenWidth downTo 0 step 8) {
                        asuka02XOffset = x.toFloat()
                        delay(16L)
                    }
                }
            }
        }
    }
}