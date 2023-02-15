
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.jianastrero.hsle.App
import com.jianastrero.hsle.Constants
import com.jianastrero.hsle.sqlite.HLSESQLite
import java.awt.FileDialog
import java.io.File
import java.io.PrintStream

fun main() = application {
    Thread.setDefaultUncaughtExceptionHandler { _, e ->
        val errorLogsFile = File("error-logs.txt")
        while (!errorLogsFile.exists()) {
            errorLogsFile.createNewFile()
        }
        errorLogsFile.outputStream().use { fos ->
            PrintStream(fos).use { ps ->
                e.printStackTrace(ps)
            }
        }
    }

    val windowState = rememberWindowState(
        width = Constants.WINDOW_WIDTH,
        height = Constants.WINDOW_HEIGHT,
        position = WindowPosition(Alignment.Center)
    )
    Window(
        state = windowState,
        title = "HL Save Editor",
        icon = painterResource("icon.png"),
        resizable = false,
        undecorated = true,
        transparent = true,
        onCloseRequest = ::myExitApplication
    ) {
        App(
            onSelectLoadFilePath = {
                val fileDialog = FileDialog(window, "Load Save Game File", FileDialog.LOAD).apply {
                    isMultipleMode = false
                    file = "*.sav"
                    directory = it
                    setFilenameFilter { _, name -> name.endsWith(".sav") }
                    isVisible = true
                }
                fileDialog.files.firstOrNull()?.absolutePath
            },
            onSelectSaveFilePath = {
                val fileDialog = FileDialog(window, "Save Save Game File", FileDialog.SAVE).apply {
                    isMultipleMode = false
                    file = "*.sav"
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