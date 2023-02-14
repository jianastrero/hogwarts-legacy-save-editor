import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.jianastrero.hsle.App
import com.jianastrero.hsle.Constants

fun main() = application {
    val windowState = rememberWindowState(
        width = Constants.WINDOW_WIDTH,
        height = Constants.WINDOW_HEIGHT,
        position = WindowPosition(Alignment.Center)
    )
    Window(
        state = windowState,
        title = "HL Save Editor",
        resizable = false,
        undecorated = true,
        transparent = true,
        onCloseRequest = ::exitApplication
    ) {
        App(::exitApplication)
    }
}
