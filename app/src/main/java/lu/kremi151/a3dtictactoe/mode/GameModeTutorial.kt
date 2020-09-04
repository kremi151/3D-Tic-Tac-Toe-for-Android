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

package lu.kremi151.a3dtictactoe.mode

import lu.kremi151.a3dtictactoe.R
import lu.kremi151.a3dtictactoe.enums.FieldValue
import lu.kremi151.a3dtictactoe.interfaces.ActivityAction
import lu.kremi151.a3dtictactoe.interfaces.ActivityInterface
import lu.kremi151.a3dtictactoe.util.AlertHelper.buildMessageAlert
import lu.kremi151.a3dtictactoe.util.CubeField
import lu.kremi151.a3dtictactoe.util.CubeRow
import lu.kremi151.a3dtictactoe.util.GameCube
import lu.kremi151.a3dtictactoe.util.SingleplayerState

class GameModeTutorial(activity: ActivityInterface, cube: GameCube) : GameMode(activity, cube) {
    private val LESSON_7: Objective = ObjectiveOutplay()
    private val LESSON_6 = ObjectiveRow(
            CubeRow(
                    CubeField(3, 0, 0),
                    CubeField(2, 0, 1),
                    CubeField(1, 0, 2),
                    CubeField(0, 0, 3)
            ), 1, false).setNextObjective(LESSON_7)
    private val LESSON_5 = ObjectiveRow(
            CubeRow(
                    CubeField(0, 0, 0),
                    CubeField(1, 1, 1),
                    CubeField(2, 2, 2),
                    CubeField(3, 3, 3)
            ), 1, false).setNextObjective(LESSON_6)
    private val LESSON_4 = ObjectiveRow(
            CubeRow(
                    CubeField(0, 0, 0),
                    CubeField(0, 0, 1),
                    CubeField(0, 0, 2),
                    CubeField(0, 0, 3)
            ), 1, false).setNextObjective(LESSON_5)
    private val LESSON_3 = ObjectiveRow(
            CubeRow(
                    CubeField(0, 0, 0),
                    CubeField(1, 1, 0),
                    CubeField(2, 2, 0),
                    CubeField(3, 3, 0)
            ), 1, false).setNextObjective(LESSON_4)
    private val LESSON_2 = ObjectiveRow(
            CubeRow(
                    CubeField(0, 0, 0),
                    CubeField(0, 1, 0),
                    CubeField(0, 2, 0),
                    CubeField(0, 3, 0)
            ), 1, true).setNextObjective(LESSON_3)
    private val LESSON_1 = ObjectiveRow(
            CubeRow(
                    CubeField(0, 0, 0),
                    CubeField(1, 0, 0),
                    CubeField(2, 0, 0),
                    CubeField(3, 0, 0)
            ), 1, true).setNextObjective(LESSON_2)

    private val player: FieldValue = FieldValue.random(random)
    private var currentObjective: Objective? = LESSON_1
    private var highlightingRow: CubeRow? = null
    private var hintRow: CubeRow? = null
    private var actionNext: ActivityAction? = null
    private var actionRetry: ActivityAction? = null
    private val fieldHintColor: Int = getColor(R.color.fieldBackgroundHint)

    override fun onTap(x: Int, y: Int, z: Int): Boolean {
        return currentObjective!!.onTap(x, y, z)
    }

    override fun onInit() {
        actionRetry = `interface`.buildAction(R.string.retry, Runnable {
            if (currentObjective != null) {
                currentObjective!!.onInit()
            }
        })!!.create()
        actionNext = `interface`.buildAction(R.string.next, Runnable {
            if (currentObjective != null && currentObjective!!.nextObjective != null) {
                currentObjective = currentObjective!!.nextObjective
                currentObjective!!.onInit()
                lockGame(false)
                actionNext!!.enabled = false
                `interface`.updateActions()
            }
        })!!.setEnabled(false).setVisible(true).create()
        currentObjective!!.onInit()
    }

    override fun getFieldColor(x: Int, y: Int, z: Int, previousColor: Int): Int {
        return if (hintRow != null && hintRow!!.contains(x, y, z)) {
            fieldHintColor
        } else {
            previousColor
        }
    }

    override fun getFieldValueColor(x: Int, y: Int, z: Int, previousColor: Int): Int {
        return if (highlightingRow != null && highlightingRow!!.contains(x, y, z)) {
            fieldColorWin
        } else {
            fieldColorDefault
        }
    }

    private abstract inner class Objective {
        var nextObjective: Objective? = null
            private set

        abstract fun onInit()
        abstract fun onTap(x: Int, y: Int, z: Int): Boolean
        fun setNextObjective(objective: Objective?): Objective {
            nextObjective = objective
            return this
        }

    }

    private inner class ObjectiveRow(
            private val row: CubeRow,
            private var maxTries: Int,
            private val hint: Boolean
    ) : Objective() {

        override fun onInit() {
            cube.clear()
            val hide = random.nextInt(cube.dimension())
            for (i in 0 until cube.dimension()) {
                if (i != hide) {
                    val field = row.getField(i)
                    cube.setValueAt(field.x, field.y, field.z, player, false)
                }
            }
            highlightingRow = null
            hintRow = if (hint) row else null
            updateBoard()
            `interface`.setSubtitle(R.string.tries_left, maxTries)
            lockGame(false)
            buildMessageAlert(context!!, R.string.tutorial_objective, R.string.tutorial_build_row).show()
        }

        override fun onTap(x: Int, y: Int, z: Int): Boolean {
            cube.setValueAt(x, y, z, player, false)
            highlightingRow = cube.searchWinningRow()
            maxTries--
            `interface`.setSubtitle(R.string.tries_left, maxTries)
            if (highlightingRow != null) {
                if (nextObjective == null) {
                    announceWinner(false)
                    buildMessageAlert(context!!, R.string.tutorial_objective_success, R.string.tutorial_complete).show()
                } else {
                    buildMessageAlert(context!!, R.string.tutorial_objective_success, R.string.tutorial_build_row_success).show()
                    lockGame(true)
                    actionNext!!.enabled = true
                    `interface`.updateActions()
                }
            } else if (maxTries <= 0) {
                lockGame(true)
                buildMessageAlert(context!!, R.string.tutorial_objective, R.string.tutorial_objective_fail_tries).show()
            }
            return true
        }

    }

    private inner class ObjectiveOutplay : Objective() {
        private var singleplayerState: SingleplayerState? = null
        override fun onInit() {
            cube.clear()
            cube.setValueAt(1, 0, 0, player, false)
            cube.setValueAt(1, 1, 0, player, false)
            cube.setValueAt(2, 3, 0, player, false)
            cube.setValueAt(3, 3, 0, player, false)
            highlightingRow = null
            hintRow = null
            updateBoard()
            lockGame(false)
            singleplayerState = SingleplayerState(player.opposite(), cube)
            buildMessageAlert(context!!, R.string.tutorial_objective, R.string.tutorial_build_row).show()
        }

        override fun onTap(x: Int, y: Int, z: Int): Boolean {
            return if (cube.valueAt(x, y, z) === FieldValue.EMPTY) {
                cube.setValueAt(x, y, z, player)
                highlightingRow = cube.searchWinningRow()
                if (highlightingRow == null) {
                    singleplayerState!!.doTurn()
                    highlightingRow = cube.searchWinningRow()
                    if (highlightingRow != null) {
                        lockGame(true)
                        buildMessageAlert(context!!, R.string.tutorial_objective, R.string.tutorial_build_row_fail).show()
                    }
                } else {
                    if (nextObjective == null) {
                        announceWinner(false)
                        buildMessageAlert(context!!, R.string.tutorial_objective_success, R.string.tutorial_complete).show()
                    } else {
                        buildMessageAlert(context!!, R.string.tutorial_objective_success, R.string.tutorial_build_row_success).show()
                        lockGame(true)
                        actionNext!!.enabled = true
                        `interface`.updateActions()
                    }
                }
                true
            } else {
                false
            }
        }
    }

}