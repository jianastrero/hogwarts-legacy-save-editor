
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.jianastrero.hsle.notification.Notifications
import com.jianastrero.hsle.save_file.CharacterSaveFile
import com.jianastrero.hsle.util.getCurrentVersion
import com.jianastrero.hsle.util.getLatestVersion
import java.io.File
import java.io.PrintStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() = application {
    val saveGameFile: SaveGameFile by remember { mutableStateOf(getSaveGameFile()) }
    val errorLogsFile by remember { mutableStateOf(File("error-logs.txt")) }

    fun ApplicationScope.myExitApplication() {
        saveGameFile.saveFileList.forEach {
            it.characterSaveFileData?.sqlite?.close()
        }
        exitApplication()
    }

    Thread.setDefaultUncaughtExceptionHandler { _, e ->
        e.printStackTrace()
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
            saveGameFile,
            ::myExitApplication
        )

        defaultScope.launch {
            val latestVersion = getLatestVersion()
            var shouldNotifyUpdateAvailable = true

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
                    val currentVersion = getCurrentVersion()
                    if (shouldNotifyUpdateAvailable && currentVersion != latestVersion) {
                        shouldNotifyUpdateAvailable = false
                        Notifications.success("A new version is available: $latestVersion")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

private fun getSaveGameFile(): SaveGameFile {
    CharacterSaveFile.generateTempFolder() // Create temp folder Deletes old temp data
    SaveGameFile.generateBackupFolder() // Create backup folder

    if (SaveGameFile.backup()) {
        Notifications.success("Automatically backed up your saved games")
    } else {
        Notifications.error("Something went wrong while backing up your saved games")
    }

    val savedGameFile = SaveGameFile.loadSavedGames()

    if (savedGameFile == null) {
        Notifications.error(
            "Couldn't load saved game list. Please send me your error-logs.txt",
            time = System.currentTimeMillis() + 5_000
        )
        throw RuntimeException("Couldn't load saved game list")
    }

    return savedGameFile
}