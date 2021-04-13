package com.holland

import javafx.event.ActionEvent
import javafx.scene.control.Alert
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import kotlin.system.exitProcess

object UiUtil {

    fun initMenu(menuBar: MenuBar) {
        menus.forEach { (k, v) ->
            val menu = Menu(k)
            v.forEach { (kItem, vItem) ->
                val item = MenuItem(kItem)
                menu.items.add(item.apply { setOnAction { run { vItem.invoke(it) } } })
            }
            menuBar.menus.add(menu)
        }
    }

    private val menus: Map<String, Map<String, (ActionEvent?) -> Unit>>
        get() = mapOf(
            "连接" to mapOf(
                "退出" to { exitProcess(1) }
            ),
            "关于" to mapOf(
                "关于" to {
                    Alert(Alert.AlertType.INFORMATION).apply {
                        contentText = """|微信: Senor_Zhang
                                         |邮箱: zhn.pop@gmail.com""".trimMargin()
                        show()
                    }
                },
                "版本" to {
                    Alert(Alert.AlertType.INFORMATION).apply {
                        contentText = "版本日期 2021年2月22日"
                        show()
                    }
                }
            )
        )
}