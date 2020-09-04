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

package lu.kremi151.a3dtictactoe.enums

import lu.kremi151.a3dtictactoe.R
import java.util.*

enum class FieldValue(val titleResource: Int) {
    EMPTY(R.string.empty),
    CROSS(R.string.cross),
    CIRCLE(R.string.circle);

    fun opposite(): FieldValue {
        return when (this) {
            EMPTY -> EMPTY
            CROSS -> CIRCLE
            CIRCLE -> CROSS
            else -> throw RuntimeException("Unknown FieldValue type: $this")
        }

    }

    companion object {

        fun random(random: Random): FieldValue {
            return if (random.nextBoolean()) CROSS else CIRCLE
        }

    }

}