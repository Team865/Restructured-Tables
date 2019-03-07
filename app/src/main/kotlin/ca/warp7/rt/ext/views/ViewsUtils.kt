package ca.warp7.rt.ext.views

import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.scene.control.MenuItem
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import krangl.*
import org.controlsfx.control.spreadsheet.Grid
import org.controlsfx.control.spreadsheet.GridBase
import org.controlsfx.control.spreadsheet.SpreadsheetCell
import org.controlsfx.control.spreadsheet.SpreadsheetCellType.*
import org.kordamp.ikonli.javafx.FontIcon
import java.time.LocalDate
import java.util.*

typealias Combo = KeyCodeCombination

fun menuItem(t: String,
             icon: String?,
             combo: KeyCombination,
             onAction: (event: ActionEvent) -> Unit): MenuItem {

    val item = MenuItem()
    item.text = t
    if (icon != null) {
        val fontIcon = FontIcon()
        fontIcon.iconLiteral = icon
        item.graphic = fontIcon
    }
    item.isMnemonicParsing = true
    item.accelerator = combo
    item.setOnAction { onAction.invoke(it) }
    return item
}


fun toGrid(df: DataFrame): Grid {
    val grid = GridBase(df.nrow, df.ncol)
    df.rows.forEachIndexed { i, row ->
        val rowList = FXCollections.observableArrayList<SpreadsheetCell>()
        row.values.forEachIndexed { j, value ->
            rowList.add(when (value) {
                null -> STRING.createCell(i, j, 1, 1, "")
                is Double -> DOUBLE.createCell(i, j, 1, 1, value)
                is Int -> INTEGER.createCell(i, j, 1, 1, value)
                is LocalDate -> DATE.createCell(i, j, 1, 1, value)
                is String -> STRING.createCell(i, j, 1, 1, value)
                else -> STRING.createCell(i, j, 1, 1, value.toString())
            })
        }
        grid.rows.add(rowList)
    }
    grid.columnHeaders.addAll(df.cols.map { it.name })
    return grid
}

fun DataFrame.comboSort(columns: List<SortColumn>) = if (columns.isEmpty()) this else (0 until nrow)
        .sortedWith(columns
                .map {
                    this[it.columnName].run {
                        when (it.sortType) {
                            SortType.Ascending -> ascendingComparator()
                            SortType.Descending -> descendingComparator().reversed()
                        }
                    }
                }
                .reduce { a, b -> a.then(b) }
        ).toIntArray().run {
            cols.map {
                when (it) {
                    is DoubleCol -> DoubleCol(it.name, Array(nrow) { i -> it.values[this[i]] })
                    is IntCol -> IntCol(it.name, Array(nrow) { i -> it.values[this[i]] })
                    is BooleanCol -> BooleanCol(it.name, Array(nrow) { i -> it.values[this[i]] })
                    is StringCol -> StringCol(it.name, Array(nrow) { i -> it.values[this[i]] })
                    is AnyCol -> AnyCol(it.name, Array(nrow) { i -> it.values[this[i]] })
                    else -> throw UnsupportedOperationException()
                }
            }
        }.let { dataFrameOf(*it.toTypedArray()) }

private fun DataCol.ascendingComparator(): java.util.Comparator<Int> = when (this) {
    is DoubleCol -> {
        Comparator { a, b -> nullsLast<Double>().compare(values[a], values[b]) }
    }
    is IntCol -> {
        Comparator { a, b -> nullsLast<Int>().compare(values[a], values[b]) }
    }
    is BooleanCol -> {
        Comparator { a, b -> nullsLast<Boolean>().compare(values[a], values[b]) }
    }
    is StringCol -> {
        Comparator { a, b -> nullsLast<String>().compare(values[a], values[b]) }
    }
    is AnyCol -> {
        Comparator { l, r ->
            @Suppress("UNCHECKED_CAST")
            nullsLast<Comparable<Any>>().compare(values[l] as Comparable<Any>, values[r] as Comparable<Any>)
        }
    }
    else -> throw UnsupportedOperationException()
}

private fun DataCol.descendingComparator(): java.util.Comparator<Int> = when (this) {
    is DoubleCol -> {
        Comparator { a, b -> nullsFirst<Double>().compare(values[a], values[b]) }
    }
    is IntCol -> {
        Comparator { a, b -> nullsFirst<Int>().compare(values[a], values[b]) }
    }
    is BooleanCol -> {
        Comparator { a, b -> nullsFirst<Boolean>().compare(values[a], values[b]) }
    }
    is StringCol -> {
        Comparator { a, b -> nullsFirst<String>().compare(values[a], values[b]) }
    }
    is AnyCol -> {
        Comparator { l, r ->
            @Suppress("UNCHECKED_CAST")
            nullsFirst<Comparable<Any>>().compare(values[l] as Comparable<Any>, values[r] as Comparable<Any>)
        }
    }
    else -> throw UnsupportedOperationException()
}

fun colorScale(k: Double, r: Int, g: Int, b: Int): String {
    val rHex = java.lang.Integer.toHexString(255 - (k * (255 - r)).toInt())
    val gHex = java.lang.Integer.toHexString(255 - (k * (255 - g)).toInt())
    val bHex = java.lang.Integer.toHexString(255 - (k * (255 - b)).toInt())

    return (if (rHex.length == 1) "0" + rHex else rHex) +
            (if (gHex.length == 1) "0" + gHex else gHex) +
            (if (bHex.length == 1) "0" + bHex else bHex)
}
