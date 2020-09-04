/**
 * 3D Tic Tac Toe for Android
 * Copyright (C) 2018  Michel Kremer (kremi151)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package lu.kremi151.a3dtictactoe.util

import lu.kremi151.a3dtictactoe.enums.FieldValue
import java.util.*
import kotlin.math.min

class GameCube constructor(private val dimension: Int = 4) {
    private val cube: Array<Array<Array<FieldValue?>>> = Array(dimension) { Array(dimension) { arrayOfNulls<FieldValue>(dimension) } }
    private val possibilities: List<CubeRow>
    private val history = LinkedList<Move>()

    fun clear() {
        for (x in 0 until width()) {
            for (y in 0 until height()) {
                for (z in 0 until depth()) {
                    cube[x][y][z] = FieldValue.EMPTY
                }
            }
        }
        history.clear()
    }

    fun valueAt(x: Int, y: Int, z: Int): FieldValue? {
        return cube[x][y][z]
    }

    fun valueAt(field: CubeField): FieldValue? {
        return cube[field.x][field.y][field.z]
    }

    fun setValueAt(x: Int, y: Int, z: Int, value: FieldValue?, log: Boolean) {
        cube[x][y][z] = value
        if (log) history.add(Move(value, x, y, z))
    }

    fun setValueAt(x: Int, y: Int, z: Int, value: FieldValue?) {
        setValueAt(x, y, z, value, true)
    }

    val lastMove: Move?
        get() = if (history.size > 0) {
            history.last
        } else {
            null
        }

    fun hasMoves(): Boolean {
        return history.size > 0
    }

    fun dimension(): Int {
        return dimension
    }

    @Deprecated("Use dimension() instead")
    fun width(): Int {
        return dimension()
    }

    @Deprecated("Use dimension() instead")
    fun height(): Int {
        return dimension()
    }

    @Deprecated("Use dimension() instead")
    fun depth(): Int {
        return dimension()
    }

    val rows: List<CubeRow>
        get() = Collections.unmodifiableList(possibilities)

    fun searchWinningRow(): CubeRow? {
        for (row in possibilities) {
            if (evaluateRow(row) !== FieldValue.EMPTY) {
                return row
            }
        }
        return null
    }

    fun evaluateRow(row: CubeRow): FieldValue? {
        val comp = valueAt(row.getField(0))
        for (i in 1 until row.fieldCount()) {
            if (comp !== valueAt(row.getField(i))) {
                return FieldValue.EMPTY
            }
        }
        return comp
    }

    fun chanceToWinOnField(player: FieldValue, field: CubeField?): Float {
        var chance = 0.0f
        var rows = 0
        for (row in possibilities) {
            if (row.contains(field)) {
                chance += chanceToWinOnRow(player, row)
                rows++
            }
        }
        return if (rows > 0) chance / rows else 0f
    }

    fun chanceToWinOnRow(player: FieldValue, row: CubeRow): Float {
        var index = 0.0f
        for (i in 0 until row.fieldCount()) {
            val field = row.getField(i)
            index += when {
                valueAt(field) === player -> {
                    1.0f
                }
                valueAt(field) === FieldValue.EMPTY -> {
                    0.5f
                }
                else -> {
                    return 0f
                }
            }
        }
        index = min(1.0f, index / row.fieldCount())
        return index * index
    }

    inner class Move(private val player: FieldValue?, x: Int, y: Int, z: Int) : CubeField(x, y, z)

    init {
        clear()
        possibilities = PossibilitiesCalculator.calculatePossibilities(dimension)
    }
}