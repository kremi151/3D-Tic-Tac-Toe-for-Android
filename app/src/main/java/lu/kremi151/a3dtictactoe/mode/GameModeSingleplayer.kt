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
 * along with this program.  If not, see <http:></http:>//www.gnu.org/licenses/>.
 */
package lu.kremi151.a3dtictactoe.mode

import lu.kremi151.a3dtictactoe.enums.FieldValue
import lu.kremi151.a3dtictactoe.interfaces.Acceptor
import lu.kremi151.a3dtictactoe.interfaces.ActivityInterface
import lu.kremi151.a3dtictactoe.util.GameCube
import lu.kremi151.a3dtictactoe.util.SingleplayerState

class GameModeSingleplayer(activity: ActivityInterface, cube: GameCube) : GameMode(activity, cube) {
    private val player = FieldValue.random(random)
    var tag: String? = null
        private set
    private var onWinListener: Acceptor<GameModeSingleplayer>? = null
    private val singleplayerState: OptimizedSingleplayer
    fun setAttack(attack: Float): GameModeSingleplayer {
        singleplayerState.setAttack(attack)
        return this
    }

    fun setDefense(defense: Float): GameModeSingleplayer {
        singleplayerState.setDefense(defense)
        return this
    }

    fun setTag(tag: String?): GameModeSingleplayer {
        this.tag = tag
        return this
    }

    fun setOnWinListener(listener: Acceptor<GameModeSingleplayer>?): GameModeSingleplayer {
        onWinListener = listener
        return this
    }

    override fun onInit() {
        announceNextPlayer(player)
    }

    override fun onTap(x: Int, y: Int, z: Int): Boolean {
        val onWinListener = this.onWinListener
        if (cube.valueAt(x, y, z) === FieldValue.EMPTY) {
            cube.setValueAt(x, y, z, player)
            if (!announceWinner()) {
                announceThinking(player.opposite())
                lockGame(true)
                ThinkerThread().start()
            } else if (onWinListener != null) {
                onWinListener(this)
            }
            return true
        }
        return false
    }

    override fun getFieldColor(x: Int, y: Int, z: Int, previousColor: Int): Int {
        return super.getFieldColor(x, y, z, previousColor)
    }

    private inner class ThinkerThread : Thread() {
        override fun run() {
            singleplayerState.doTurn()
        }
    }

    private inner class OptimizedSingleplayer(sign: FieldValue?, cube: GameCube?) : SingleplayerState(sign!!, cube!!) {
        override fun giveUp() {
            enqueueTask(Runnable { this@GameModeSingleplayer.giveUp(player.opposite()) })
        }

        override fun onTurnFinished(x: Int, y: Int, z: Int) {
            enqueueTask(Runnable {
                cube.setValueAt(x, y, z, player.opposite())
                if (!announceWinner()) {
                    announceNextPlayer(player)
                    lockGame(false)
                }
                updateBoard()
            })
        }
    }

    init {
        singleplayerState = OptimizedSingleplayer(player.opposite(), cube)
    }
}