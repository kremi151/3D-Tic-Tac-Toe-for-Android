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

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;

import lu.kremi151.a3dtictactoe.enums.FieldValue;
import lu.kremi151.a3dtictactoe.interfaces.OnBoardTapListener;
import lu.kremi151.a3dtictactoe.util.CubeRow;
import lu.kremi151.a3dtictactoe.util.GameCube;

public abstract class GameMode implements OnBoardTapListener{

    protected final GameCube cube;
    private final Context context;
    private boolean locked = false;

    public GameMode(Context context, GameCube cube){
        this.cube = cube;
        this.context = context;
    }

    protected final GameCube getCube(){
        return cube;
    }

    protected final Context getContext(){
        return context;
    }

    protected final boolean announceWinner(){
        CubeRow winningRow = cube.searchWinningRow();
        if(winningRow != null){
            FieldValue winner = cube.evaluateRow(winningRow);
            new AlertDialog.Builder(getContext())
                    .setTitle("Game finished")
                    .setMessage(winner.name() + " has won!")
                    .create()
                    .show();
            lockGame(true);
            //TODO: Announce
            return true;
        }else{
            return false;
        }
    }

    protected final void giveUp(FieldValue player){
        new AlertDialog.Builder(getContext())
                .setTitle("Game finished")
                .setMessage(player.name() + " has given up!")
                .create()
                .show();
        lockGame(true);
    }

    protected void lockGame(boolean v){
        this.locked = v;
    }

    public boolean isLocked(){
        return this.locked;
    }

    public int getFieldColor(int x, int y, int z, int previousColor){
        return previousColor;
    }

    public int getFieldValueColor(int x, int y, int z, int previousColor){
        return previousColor;
    }

}
