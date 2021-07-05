package com.example.jetpackcompose.animation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.ui.tooling.preview.Preview

class Animation2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This is an extension function of Activity that sets the @Composable function that's
        // passed to it as the root view of the activity. This is meant to replace the .xml file
        // that we would typically set using the setContent(R.id.xml_file) method. The setContent
        // block defines the activity's layout.
        setContent {
            AnimateColorComponent()
        }
    }
}

// We represent a Composable function by annotating it with the @Composable annotation. Composable
// functions can only be called from within the scope of other composable functions. We should
// think of composable functions to be similar to lego blocks - each composable function is in turn
// built up of smaller composable functions.
@Composable
fun AnimateColorComponent() {
    // TODO: Add comment
    val currentColor by remember { mutableStateOf(Color.Red) }
    val transition = updateTransition(currentColor)

    val color by transition.animateColor(
        transitionSpec = { TweenSpec<Color>(durationMillis = 2000) }
    ) { state ->
        when (state) {
            Color.Red -> Color.Green
            Color.Green -> Color.Blue
            Color.Blue -> Color.Red
            else -> Color.Red
        }
    }

    // As the Transition is changing the interpolating the value of your props based
    // on the "from state" and the "to state", you get access to all the values
    // including the intermediate values as they are being updated. We can use the
    // state variable and access the relevant props/properties to update the relevant
    // composables/layouts. Below, we use state[color] to get get the latest value of color
    // and use it to paint the screen by setting it as the backgroundColor of the screen.
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = color)) { }
}

/**
 * Android Studio lets you preview your composable functions within the IDE itself, instead of
 * needing to download the app to an Android device or emulator. This is a fantastic feature as you
 * can preview all your custom components(read composable functions) from the comforts of the IDE.
 * The main restriction is, the composable function must not take any parameters. If your composable
 * function requires a parameter, you can simply wrap your component inside another composable
 * function that doesn't take any parameters and call your composable function with the appropriate
 * params. Also, don't forget to annotate it with @Preview & @Composable annotations.
 */
@Preview
@Composable
fun AnimateColorComponentPreview() {
    AnimateColorComponent()
}
