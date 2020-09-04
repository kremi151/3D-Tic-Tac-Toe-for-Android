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

import android.content.Context
import androidx.appcompat.app.AlertDialog
import lu.kremi151.a3dtictactoe.R

object AlertHelper {

    fun buildCloseGameAlert(c: Context, onProceed: Runnable): AlertDialog {
        val builder = AlertDialog.Builder(c)
        builder.setTitle(R.string.game_ongoing)
        builder.setMessage(R.string.end_game_confirm)
        builder.setNegativeButton(android.R.string.no) { dialog, _ -> dialog.cancel() }
        builder.setPositiveButton(android.R.string.yes) { dialog, _ ->
            onProceed.run()
            dialog.dismiss()
        }
        return builder.create()
    }

    fun buildMessageAlert(c: Context, titleRes: Int, messageRes: Int, runAfter: Runnable? = null): AlertDialog {
        val builder = AlertDialog.Builder(c)
        builder.setTitle(titleRes)
        builder.setMessage(messageRes)
        builder.setNeutralButton(android.R.string.ok) { dialog, _ ->
            runAfter?.run()
            dialog.dismiss()
        }
        return builder.create()
    }

}