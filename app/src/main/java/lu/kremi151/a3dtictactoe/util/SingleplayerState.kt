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

open class SingleplayerState(
        private val sign: FieldValue,
        private val cube: GameCube
) {

    private val possibilities: MutableList<CubeRow>
    private val fields: MutableList<CubeField>
    private val random = Random(System.currentTimeMillis())
    private var attack = 0.5f
    private var defense = 0.5f
    private val mind: Array<Array<FloatArray>> = Array(cube.width()) { Array(cube.height()) { FloatArray(cube.depth()) } }
    private var mindMax = 1.0f

    fun setAttack(attack: Float): SingleplayerState {
        this.attack = min(1f, attack)
        return this
    }

    fun setDefense(defense: Float): SingleplayerState {
        this.defense = min(1f, defense)
        return this
    }

    private fun resetMind() {
        mindMax = 1.0f
        for (x in 0 until cube.width()) {
            for (y in 0 until cube.height()) {
                for (z in 0 until cube.depth()) {
                    mind[x][y][z] = 0.0f
                }
            }
        }
    }

    private fun findEmptyField(): CubeField? {
        val targetX = random.nextInt(cube.width())
        val targetY = random.nextInt(cube.height())
        val targetZ = random.nextInt(cube.depth())
        if (cube.valueAt(targetX, targetY, targetZ) !== FieldValue.EMPTY) {
            for (mx in 0 until cube.width()) {
                for (my in 0 until cube.height()) {
                    for (mz in 0 until cube.depth()) {
                        if (cube.valueAt(mx, my, mz) === FieldValue.EMPTY) {
                            return CubeField(mx, my, mz)
                        }
                    }
                }
            }
            return null
        }
        return CubeField(targetX, targetY, targetZ)
    }

    private fun rowProbability(row: CubeRow): Float {
        /*return Math.max(
                attack * cube.chanceToWinOnRow(player.opposite(), row),
                defense * cube.chanceToWinOnRow(player, row)
        );*/
        return attack * cube.chanceToWinOnRow(sign, row) +
                defense * cube.chanceToWinOnRow(sign.opposite(), row)
    }

    fun doTurn() {
        resetMind()
        val it = possibilities.iterator()
        while (it.hasNext()) {
            val row = it.next()
            val probability = Math.min(1f, rowProbability(row))
            if (probability == 0.0f) {
                it.remove()
            } else {
                for (fieldIndex in 0 until row.fieldCount()) {
                    val field = row.getField(fieldIndex)
                    var value = mind[field.x][field.y][field.z]
                    value = Math.max(value, probability)
                    mind[field.x][field.y][field.z] = value
                    if (value > mindMax) {
                        mindMax = value
                    }
                }
            }
        }
        val targetField = findEmptyField()
        if (targetField != null) {
            var targetX = targetField.x
            var targetY = targetField.y
            var targetZ = targetField.z
            var highestChance = mind[targetX][targetY][targetZ]
            for (mx in 0 until cube.width()) {
                for (my in 0 until cube.height()) {
                    for (mz in 0 until cube.depth()) {
                        if (mind[mx][my][mz] > highestChance && cube.valueAt(mx, my, mz) === FieldValue.EMPTY) {
                            targetX = mx
                            targetY = my
                            targetZ = mz
                            highestChance = mind[mx][my][mz]
                        }
                    }
                }
            }
            onTurnFinished(targetX, targetY, targetZ)
        } else {
            giveUp()
        }
    }

    protected open fun giveUp() {}
    protected open fun onTurnFinished(x: Int, y: Int, z: Int) {
        cube.setValueAt(x, y, z, sign)
    }

    init {
        possibilities = ArrayList(cube.rows)
        fields = ArrayList(cube.width() * cube.height() * cube.depth())
        for (x in 0 until cube.width()) {
            for (y in 0 until cube.height()) {
                for (z in 0 until cube.depth()) {
                    fields.add(CubeField(x, y, z))
                }
            }
        }
    }
}