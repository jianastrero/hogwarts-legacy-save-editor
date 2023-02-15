package com.jianastrero.hsle.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jianastrero.hsle.enumerations.NotificationType
import com.jianastrero.hsle.model.Notification
import com.jianastrero.hsle.theme.BlueGray
import com.jianastrero.hsle.theme.Red
import com.jianastrero.hsle.theme.Yellow

@Composable
fun NotificationItem(
    notification: Notification,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                when (notification.type) {
                    NotificationType.Info -> BlueGray
                    NotificationType.Success -> Yellow
                    NotificationType.Error -> Red
                },
                shape = RoundedCornerShape(12.dp)
            )
            .then(modifier)
    ) {
        Text(
            text = notification.message,
            color = when (notification.type) {
                NotificationType.Success -> Color.Black
                NotificationType.Error,
                NotificationType.Info -> Color.White
            },
            fontFamily = FontFamily.Default,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(12.dp)
        )
    }
}
