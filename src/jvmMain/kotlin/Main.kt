
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.jianastrero.hsle.App
import com.jianastrero.hsle.Constants
import com.jianastrero.hsle.sqlite.HLSESQLite
import java.awt.FileDialog

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
        onCloseRequest = ::myExitApplication
    ) {
        App(
            onSelectLoadFile = {
                val fileDialog = FileDialog(window, "Load Save Game File", FileDialog.LOAD).apply {
                    isMultipleMode = false
                    file = "*.sav"
                    directory = it
                    setFilenameFilter { _, name -> name.endsWith(".sav") }
                    isVisible = true
                }
                fileDialog.files.firstOrNull()?.absolutePath
            },
            ::myExitApplication
        )
    }
}

fun ApplicationScope.myExitApplication() {
    HLSESQLite.close()
    exitApplication()
}