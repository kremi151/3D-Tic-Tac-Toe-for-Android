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

import lu.kremi151.a3dtictactoe.R;
import lu.kremi151.a3dtictactoe.enums.FieldValue;
import lu.kremi151.a3dtictactoe.interfaces.ActivityInterface;
import lu.kremi151.a3dtictactoe.interfaces.OnBoardTapListener;
import lu.kremi151.a3dtictactoe.interfaces.Savegame;
import lu.kremi151.a3dtictactoe.util.CubeRow;
import lu.kremi151.a3dtictactoe.util.GameCube;

public abstract class GameMode implements OnBoardTapListener{

    protected final GameCube cube;
    private final ActivityInterface activity;
    private boolean locked = false;
    private CubeRow winningRow = null;

    protected final int fieldColorDefault, fieldColorWin, fieldColorLast;

    public GameMode(ActivityInterface activity, GameCube cube){
        this.cube = cube;
        this.activity = activity;

        this.fieldColorDefault = getColor(R.color.fieldColor);
        this.fieldColorWin = getColor(R.color.fieldHighlightWinning);
        this.fieldColorLast = getColor(R.color.fieldHighlightLast);
    }

    protected final GameCube getCube(){
        return cube;
    }

    protected final Context getContext(){
        return activity.getContext();
    }

    public final Savegame getSavegame(){
        return activity.getSavegame();
    }

    protected final boolean announceWinner(){
        return announceWinner(true);
    }

    protected final boolean announceWinner(boolean showAlert){
        winningRow = cube.searchWinningRow();
        if(winningRow != null){
            FieldValue winner = cube.evaluateRow(winningRow);
            final String message = getContext().getString(R.string.player_won, getContext().getString(winner.getTitleResource()));
            if(showAlert) new AlertDialog.Builder(getContext())
                            .setTitle(R.string.game_finished)
                            .setMessage(message)
                            .create()
                            .show();
            activity.setSubtitle(message);
            lockGame(true);
            return true;
        }else{
            return false;
        }
    }

    protected final void giveUp(FieldValue player){
        final String message = getContext().getString(R.string.player_gave_up, getContext().getString(player.getTitleResource()));
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.game_finished)
                .setMessage(message)
                .create()
                .show();
        activity.setSubtitle(message);
        lockGame(true);
    }

    protected final void announceNextPlayer(FieldValue player){
        activity.setSubtitle(R.string.player_turn, activity.getContext().getString(player.getTitleResource()));
    }

    protected final void announceThinking(FieldValue player){
        activity.setSubtitle(R.string.opponent_thinking, activity.getContext().getString(player.getTitleResource()));
    }

    protected final void lockGame(boolean v){
        this.locked = v;
    }

    public final boolean isLocked(){
        return this.locked;
    }

    public final boolean isGameFinished(){
        return winningRow != null;
    }

    protected final void updateBoard(){
        this.activity.updateBoard();
    }

    protected final void enqueueTask(Runnable runnable){
        this.activity.enqueueTask(runnable);
    }

    public int getFieldColor(int x, int y, int z, int previousColor){
        return previousColor;
    }

    public int getFieldValueColor(int x, int y, int z, int previousColor){
        if(winningRow != null && winningRow.contains(x, y, z)) {
            return fieldColorWin;
        }else if(cube.hasMoves()){
            final GameCube.Move move = cube.getLastMove();
            if(move.getX() == x && move.getY() == y && move.getZ() == z){
                return fieldColorLast;
            }
        }
        return previousColor;
    }

    protected final int getColor(int colorRes){
        return activity.getContext().getResources().getColor(colorRes);
    }

    public abstract void onInit();

}
