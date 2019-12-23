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

import androidx.annotation.Nullable;

import lu.kremi151.a3dtictactoe.enums.FieldValue;
import lu.kremi151.a3dtictactoe.interfaces.Acceptor;
import lu.kremi151.a3dtictactoe.interfaces.ActivityInterface;
import lu.kremi151.a3dtictactoe.util.GameCube;
import lu.kremi151.a3dtictactoe.util.SingleplayerState;

public class GameModeSingleplayer extends GameMode {

    private final FieldValue player = FieldValue.random(random);
    private String tag = null;
    private Acceptor<GameModeSingleplayer> onWinListener = null;
    private OptimizedSingleplayer singleplayerState;

    public GameModeSingleplayer(ActivityInterface activity, GameCube cube) {
        super(activity, cube);
        this.singleplayerState = new OptimizedSingleplayer(player.opposite(), cube);
    }

    public GameModeSingleplayer setAttack(float attack){
        this.singleplayerState.setAttack(attack);
        return this;
    }

    public GameModeSingleplayer setDefense(float defense){
        this.singleplayerState.setDefense(defense);
        return this;
    }

    public GameModeSingleplayer setTag(String tag){
        this.tag = tag;
        return this;
    }

    public GameModeSingleplayer setOnWinListener(Acceptor<GameModeSingleplayer> listener){
        this.onWinListener = listener;
        return this;
    }

    @Nullable
    public String getTag(){
        return tag;
    }

    @Override
    public void onInit() {
        announceNextPlayer(player);
    }

    @Override
    public boolean onTap(int x, int y, int z) {
        if(cube.valueAt(x, y, z) == FieldValue.EMPTY){
            cube.setValueAt(x, y, z, player);
            if(!announceWinner()){
                announceThinking(player.opposite());
                lockGame(true);
                new ThinkerThread().start();
            }else if(this.onWinListener != null){
                this.onWinListener.onAccept(this);
            }
            return true;
        }
        return false;
    }

    @Override
    public int getFieldColor(int x, int y, int z, int previousColor){
        return super.getFieldColor(x, y, z, previousColor);
    }

    private class ThinkerThread extends Thread{

        @Override
        public void run(){
            singleplayerState.doTurn();
        }
    }

    private class OptimizedSingleplayer extends SingleplayerState{

        public OptimizedSingleplayer(FieldValue sign, GameCube cube) {
            super(sign, cube);
        }

        @Override
        protected void giveUp(){
            enqueueTask(new Runnable() {
                @Override
                public void run() {
                    GameModeSingleplayer.this.giveUp(player.opposite());
                }
            });
        }

        @Override
        protected void onTurnFinished(final int x, final int y, final int z){
            enqueueTask(new Runnable() {
                @Override
                public void run() {
                    cube.setValueAt(x, y, z, player.opposite());
                    if(!announceWinner()){
                        announceNextPlayer(player);
                        lockGame(false);
                    }
                    updateBoard();
                }
            });
        }
    }
}
