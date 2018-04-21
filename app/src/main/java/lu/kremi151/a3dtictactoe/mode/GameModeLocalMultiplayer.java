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

package lu.kremi151.a3dtictactoe.mode;

import lu.kremi151.a3dtictactoe.enums.FieldValue;
import lu.kremi151.a3dtictactoe.interfaces.ActivityInterface;
import lu.kremi151.a3dtictactoe.util.GameCube;

public class GameModeLocalMultiplayer extends GameMode {

    private FieldValue current = FieldValue.CROSS;

    public GameModeLocalMultiplayer(ActivityInterface activity, GameCube cube) {
        super(activity, cube);
    }

    @Override
    public void onInit() {
        announceNextPlayer(current);
    }

    @Override
    public boolean onTap(int x, int y, int z) {
        if(cube.valueAt(x, y, z) == FieldValue.EMPTY){
            cube.setValueAt(x, y, z, current);
            current = current.opposite();

            if(!announceWinner()){
                announceNextPlayer(current);
            }

            return true;
        }else{
            return false;
        }
    }
}
