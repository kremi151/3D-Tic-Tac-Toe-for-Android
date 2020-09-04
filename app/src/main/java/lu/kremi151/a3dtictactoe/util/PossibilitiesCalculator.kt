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

import java.util.*

object PossibilitiesCalculator {
    fun calculatePossibilities(size: Int): List<CubeRow> {
        val maxIndex = size - 1
        val list = ArrayList<CubeRow>(76)
        for (z in 0 until size) {
            list.add(CubeRow.spread(0, 0, z, 1, 1, 0))
            list.add(CubeRow.spread(0, maxIndex, z, 1, -1, 0))
            for (x in 0 until size) {
                list.add(CubeRow.spread(x, 0, z, 0, 1, 0))
            }
            for (y in 0 until size) {
                list.add(CubeRow.spread(0, y, z, 1, 0, 0))
            }
        }
        for (x in 0 until size) {
            list.add(CubeRow.spread(x, 0, 0, 0, 1, 1))
            list.add(CubeRow.spread(x, maxIndex, 0, 0, -1, 1))
            for (y in 0 until size) {
                list.add(CubeRow.spread(x, y, 0, 0, 0, 1))
            }
        }
        for (y in 0 until size) {
            list.add(CubeRow.spread(0, y, 0, 1, 0, 1))
            list.add(CubeRow.spread(maxIndex, y, 0, -1, 0, 1))
        }
        list.add(CubeRow.spread(0, 0, 0, 1, 1, 1))
        list.add(CubeRow.spread(0, 0, maxIndex, 1, 1, -1))
        list.add(CubeRow.spread(maxIndex, 0, 0, -1, 1, 1))
        list.add(CubeRow.spread(maxIndex, 0, maxIndex, -1, 1, -1))
        return list
    }
}