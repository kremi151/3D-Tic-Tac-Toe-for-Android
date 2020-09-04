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

package lu.kremi151.a3dtictactoe.interfaces

import android.content.Context

interface ActivityInterface {
    fun setSubtitle(subtitle: Int, vararg args: Any?)
    fun setSubtitle(subtitle: CharSequence?)
    fun buildAction(titleRes: Int, runnable: Runnable): ActionBuilder?
    fun updateActions()
    val context: Context?
    fun updateBoard()
    fun enqueueTask(runnable: Runnable?)
    val savegame: Savegame?

    abstract class ActionBuilder(protected var action: ActivityAction) {
        fun setEnabled(enabled: Boolean): ActionBuilder {
            action.enabled = enabled
            return this
        }

        fun setVisible(visible: Boolean): ActionBuilder {
            action.visible = visible
            return this
        }

        abstract fun create(): ActivityAction?

    }
}