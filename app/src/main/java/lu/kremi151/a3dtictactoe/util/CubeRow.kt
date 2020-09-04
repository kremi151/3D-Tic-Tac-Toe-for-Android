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

class CubeRow(
        private val a: CubeField,
        private val b: CubeField,
        private val c: CubeField,
        private val d: CubeField
) {

    fun fieldCount(): Int {
        return 4
    }

    fun getField(index: Int): CubeField {
        return when (index) {
            0 -> a
            1 -> b
            2 -> c
            3 -> d
            else -> throw IndexOutOfBoundsException("Row only has " + fieldCount() + " fields, index requested for " + index)
        }
    }

    operator fun contains(field: CubeField?): Boolean {
        return a == field || b == field || c == field || d == field
    }

    fun contains(x: Int, y: Int, z: Int): Boolean {
        for (i in 0 until fieldCount()) {
            val field = getField(i)
            if (field.x == x && field.y == y && field.z == z) {
                return true
            }
        }
        return false
    }

    companion object {
        /**
         * Builds a row
         * @param startX The x coordinate of the first field
         * @param startY The y coordinate of the first field
         * @param startZ The z coordinate of the first field
         * @param dx The offset the x coordinates of the successive fields will be located at. Shall only be -1, 0 or 1.
         * @param dy The offset the y coordinates of the successive fields will be located at. Shall only be -1, 0 or 1.
         * @param dz The offset the z coordinates of the successive fields will be located at. Shall only be -1, 0 or 1.
         * @return Returns the built row
         */
        fun spread(startX: Int, startY: Int, startZ: Int, dx: Int, dy: Int, dz: Int): CubeRow {
            return CubeRow(
                    CubeField(startX, startY, startZ),
                    CubeField(startX + dx, startY + dy, startZ + dz),
                    CubeField(startX + 2 * dx, startY + 2 * dy, startZ + 2 * dz),
                    CubeField(startX + 3 * dx, startY + 3 * dy, startZ + 3 * dz)
            )
        }
    }

}