package com.kaandradec.frozenfun.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

//import java.util.Properties
//import javax.activation.DataHandler
//import javax.activation.FileDataSource
//import javax.mail.Message
//import javax.mail.Session
//import javax.mail.Transport
//import javax.mail.internet.InternetAddress
//import javax.mail.internet.MimeBodyPart
//import javax.mail.internet.MimeMessage
//import javax.mail.internet.MimeMultipart

// Obtenemos la altura de la barra de estado
@Composable
@SuppressLint("InternalInsetResource", "DiscouragedApi")
fun getStatusBarHeightDp(): Dp {
    val context = LocalContext.current
    val statusBarHeight = context.resources.getDimensionPixelSize(
        context.resources.getIdentifier("status_bar_height", "dimen", "android")
    )
    return with(LocalDensity.current) { statusBarHeight.toDp() }
}

// ENVIAR EMAIL
//fun sendEmailWithAttachment(email: String, subject: String, body: String, file: File) {
//    val properties = Properties().apply {
//        put("mail.smtp.host", "smtp.gmail.com")
//        put("mail.smtp.port", "587")
//        put("mail.smtp.auth", "true")
//        put("mail.smtp.starttls.enable", "true")
//    }
//
//    val session = Session.getInstance(properties, null)
//    val message = MimeMessage(session).apply {
//        setFrom(InternetAddress("your-email@domain.com"))
//        addRecipient(Message.RecipientType.TO, InternetAddress(email))
//        this.subject = subject
//
//        // body
//        val mimeBodyPart = MimeBodyPart().apply {
//            setText(body)
//        }
//
//        // attachment
//        val attachmentBodyPart = MimeBodyPart().apply {
//            val source = FileDataSource(file)
//            dataHandler = DataHandler(source)
//            fileName = file.name
//        }
//
//        val multipart = MimeMultipart().apply {
//            addBodyPart(mimeBodyPart)
//            addBodyPart(attachmentBodyPart)
//        }
//
//        setContent(multipart)
//    }
//
//    Transport.send(message, "your-email@domain.com", "your-email-password")
//}
