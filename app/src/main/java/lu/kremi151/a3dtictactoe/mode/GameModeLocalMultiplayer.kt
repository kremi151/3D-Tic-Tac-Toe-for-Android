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

import lu.kremi151.a3dtictactoe.enums.FieldValue
import lu.kremi151.a3dtictactoe.interfaces.ActivityInterface
import lu.kremi151.a3dtictactoe.util.GameCube

class GameModeLocalMultiplayer(activity: ActivityInterface, cube: GameCube) : GameMode(activity, cube) {
    private var current = FieldValue.random(random)

    override fun onInit() {
        announceNextPlayer(current)
    }

    override fun onTap(x: Int, y: Int, z: Int): Boolean {
        return if (cube.valueAt(x, y, z) === FieldValue.EMPTY) {
            cube.setValueAt(x, y, z, current)
            current = current.opposite()
            if (!announceWinner()) {
                announceNextPlayer(current)
            }
            true
        } else {
            false
        }
    }
}