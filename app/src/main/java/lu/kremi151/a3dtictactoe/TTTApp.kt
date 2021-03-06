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

package lu.kremi151.a3dtictactoe

import android.app.Application
import android.content.Context
import lu.kremi151.a3dtictactoe.interfaces.Savegame
import lu.kremi151.a3dtictactoe.util.SharedPreferencesSavegame


class TTTApp : Application() {
    lateinit var savegame: Savegame
        private set

    override fun onCreate() {
        super.onCreate()
        savegame = SharedPreferencesSavegame(getSharedPreferences("3dtitato.save", Context.MODE_PRIVATE))
    }

}