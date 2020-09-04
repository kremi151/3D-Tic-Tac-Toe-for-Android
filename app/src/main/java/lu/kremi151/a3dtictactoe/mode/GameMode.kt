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

import android.content.Context
import androidx.appcompat.app.AlertDialog
import lu.kremi151.a3dtictactoe.R
import lu.kremi151.a3dtictactoe.enums.FieldValue
import lu.kremi151.a3dtictactoe.interfaces.ActivityInterface
import lu.kremi151.a3dtictactoe.interfaces.OnBoardTapListener
import lu.kremi151.a3dtictactoe.interfaces.Savegame
import lu.kremi151.a3dtictactoe.util.CubeRow
import lu.kremi151.a3dtictactoe.util.GameCube
import java.util.*

abstract class GameMode(
        protected val `interface`: ActivityInterface,
        protected val cube: GameCube
) : OnBoardTapListener {

    protected val random = Random(System.currentTimeMillis())
    var isLocked = false
        private set
    private var winningRow: CubeRow? = null
    protected val fieldColorDefault: Int
    protected val fieldColorWin: Int
    protected val fieldColorLast: Int

    protected val context: Context?
        get() = `interface`.context

    val savegame: Savegame?
        get() = `interface`.savegame

    protected fun announceWinner(showAlert: Boolean = true): Boolean {
        winningRow = cube.searchWinningRow()
        return if (winningRow != null) {
            val winner = cube.evaluateRow(winningRow!!)
            val message = context!!.getString(R.string.player_won, context!!.getString(winner!!.titleResource))
            if (showAlert) AlertDialog.Builder(context!!)
                    .setTitle(R.string.game_finished)
                    .setMessage(message)
                    .create()
                    .show()
            `interface`.setSubtitle(message)
            lockGame(true)
            true
        } else {
            false
        }
    }

    protected fun giveUp(player: FieldValue) {
        val message = context!!.getString(R.string.player_gave_up, context!!.getString(player.titleResource))
        AlertDialog.Builder(context!!)
                .setTitle(R.string.game_finished)
                .setMessage(message)
                .create()
                .show()
        `interface`.setSubtitle(message)
        lockGame(true)
    }

    protected fun announceNextPlayer(player: FieldValue) {
        `interface`.setSubtitle(R.string.player_turn, `interface`.context!!.getString(player.titleResource))
    }

    protected fun announceThinking(player: FieldValue) {
        `interface`.setSubtitle(R.string.opponent_thinking, `interface`.context!!.getString(player.titleResource))
    }

    protected fun lockGame(v: Boolean) {
        isLocked = v
    }

    val isGameFinished: Boolean
        get() = winningRow != null

    protected fun updateBoard() {
        `interface`.updateBoard()
    }

    protected fun enqueueTask(runnable: Runnable?) {
        `interface`.enqueueTask(runnable)
    }

    open fun getFieldColor(x: Int, y: Int, z: Int, previousColor: Int): Int {
        return previousColor
    }

    open fun getFieldValueColor(x: Int, y: Int, z: Int, previousColor: Int): Int {
        if (winningRow != null && winningRow!!.contains(x, y, z)) {
            return fieldColorWin
        } else if (cube.hasMoves()) {
            val move = cube.lastMove
            if (move!!.x == x && move.y == y && move.z == z) {
                return fieldColorLast
            }
        }
        return previousColor
    }

    protected fun getColor(colorRes: Int): Int {
        return `interface`.context!!.resources.getColor(colorRes)
    }

    abstract fun onInit()

    init {
        fieldColorDefault = getColor(R.color.fieldColor)
        fieldColorWin = getColor(R.color.fieldHighlightWinning)
        fieldColorLast = getColor(R.color.fieldHighlightLast)
    }
}