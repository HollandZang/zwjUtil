package com.holland

import javafx.application.Application
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.stage.Stage

@Suppress("PrivatePropertyName")
class HomePage : Application() {
    private lateinit var menu_bar: MenuBar
    private lateinit var m1: TextField
    private lateinit var secret: TextField
    private lateinit var m2: TextField
    private lateinit var encode: Button
    private lateinit var decode: Button
    private lateinit var copy: Button
    private lateinit var text: TextArea

    override fun start(primaryStage: Stage?) {
        val pane = FXMLLoader.load<Parent>(javaClass.getResource("/HomePage.fxml"))
        primaryStage!!.scene = Scene(pane)
        primaryStage.title = "零宽字符加密工具"
        primaryStage.show()

        menu_bar = pane.lookup("#menu") as MenuBar
        UiUtil.initMenu(menu_bar)
        m1 = pane.lookup("#m1") as TextField
        secret = pane.lookup("#secret") as TextField
        m2 = pane.lookup("#m2") as TextField
        encode = pane.lookup("#encode") as Button
        decode = pane.lookup("#decode") as Button
        copy = pane.lookup("#copy") as Button
        text = pane.lookup("#text") as TextArea

        encode.onAction = EventHandler {
            if (m1.text.isBlank()) {
                Alert(Alert.AlertType.WARNING).apply {
                    contentText = "明文不能为空"
                    show()
                }
                return@EventHandler
            }
            if (secret.text.isBlank()) {
                Alert(Alert.AlertType.WARNING).apply {
                    contentText = "秘文不能为空"
                    show()
                }
                return@EventHandler
            }
            text.text = ZWJ.encode(m1.text, secret.text)
        }

        decode.onAction = EventHandler {
            if (m2.text.isBlank()) {
                Alert(Alert.AlertType.WARNING).apply {
                    contentText = "秘文不能为空"
                    show()
                }
                return@EventHandler
            }
            val pair = ZWJ.decode(m2.text)
            text.text = "明文: ${pair.first}\n密文: ${pair.second}"
        }

        copy.onAction = EventHandler {
            val clipboardContent = ClipboardContent()
            clipboardContent.putString(text.text)
            Clipboard.getSystemClipboard().setContent(clipboardContent)
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(HomePage::class.java, *args)
}
