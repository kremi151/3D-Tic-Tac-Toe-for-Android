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

package lu.kremi151.a3dtictactoe.interfaces;

import android.content.Context;

public interface ActivityInterface {

    void setSubtitle(int subtitle, Object... args);
    void setSubtitle(CharSequence subtitle);
    GameModeAction addAction(int titleRes, Runnable action, boolean enabled);
    GameModeAction addAction(int titleRes, Runnable action);
    void updateActions();
    Context getContext();
    void updateBoard();
    void enqueueTask(Runnable runnable);
    Savegame getSavegame();

}
