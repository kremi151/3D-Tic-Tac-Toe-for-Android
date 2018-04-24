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

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class GameModeAction implements MenuItem.OnMenuItemClickListener{

    public final String title;
    public final Runnable action;
    public boolean enabled = true;

    public GameModeAction(String title, Runnable action){
        this.title = title;
        this.action = action;
    }

    public GameModeAction setEnabled(boolean enabled){
        this.enabled = enabled;
        return this;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        action.run();
        return true;
    }
}
