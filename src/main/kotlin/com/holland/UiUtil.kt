package com.holland

import javafx.event.ActionEvent
import javafx.scene.control.Alert
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import java.io.File
import kotlin.system.exitProcess

object UiUtil {
    fun openFolderChooser(primaryStage: Stage): File? {
        val folderChooser = DirectoryChooser()
        folderChooser.title = "选择文件夹"
        // TODO: 2021/2/3 记录上一次打开的位置
//        val lastFile = File("")
//        if (lastFile != null) folderChooser.initialDirectory = lastFile
        val selectedfolder = folderChooser.showDialog(primaryStage)
//        FileWriteUtil.newLine2File(selectedfolder.path,"conf","record.conf")
        return if (selectedfolder != null) {
//            lastFile = selectedfolder
            selectedfolder
        } else null
    }

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

    private val menus = mapOf(
        Pair(
            "连接", mapOf<String, (ActionEvent?) -> Unit>(
//                Pair("新的连接", { Connect().start(Stage()) }),
                Pair("退出", { exitProcess(1) })
            )
        ), Pair(
            "关于", mapOf<String, (ActionEvent?) -> Unit>(
                Pair("关于", {
                    Alert(Alert.AlertType.INFORMATION).apply {
                        contentText = "微信: Senor_Zhang\n邮箱: zhn.pop@gmail.com"
                        show()
                    }
                }),
                Pair("版本", {
                    Alert(Alert.AlertType.INFORMATION).apply {
                        contentText = "版本日期 2021年2月22日"
                        show()
                    }
                })
            )
        )
    )
}