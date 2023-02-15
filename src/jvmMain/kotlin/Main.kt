
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.jianastrero.hsle.App
import com.jianastrero.hsle.Constants
import com.jianastrero.hsle.enumerations.NotificationType
import com.jianastrero.hsle.model.Notification
import com.jianastrero.hsle.notification.Notifications
import com.jianastrero.hsle.sqlite.HLSESQLite
import java.awt.FileDialog
import java.io.File
import java.io.PrintStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() = application {
    val errorLogsFile = File("error-logs.txt")

    Thread.setDefaultUncaughtExceptionHandler { _, e ->
        Notifications.error(e.cause?.message ?: "Something went wrong")
        while (!errorLogsFile.exists()) {
            errorLogsFile.createNewFile()
        }
        errorLogsFile.outputStream().use { fos ->
            PrintStream(fos).use { ps ->
                e.printStackTrace(ps)
            }
        }
    }

    val defaultScope = rememberCoroutineScope { Dispatchers.Default }
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

        defaultScope.launch {
            while (!window.isVisible) {
                delay(500) // Wait till window is visible
            }
            while (window.isVisible) {
                try {
                    delay(500)
                    val oldestNotification = Notifications.peek()
                    if (oldestNotification != null && oldestNotification.time + 2_000 <= System.currentTimeMillis()) {
                        Notifications.pop()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

fun ApplicationScope.myExitApplication() {
    HLSESQLite.close()
    exitApplication()
}