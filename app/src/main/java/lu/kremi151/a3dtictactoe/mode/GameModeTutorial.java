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

import android.graphics.Color;

import lu.kremi151.a3dtictactoe.R;
import lu.kremi151.a3dtictactoe.enums.FieldValue;
import lu.kremi151.a3dtictactoe.interfaces.ActivityInterface;
import lu.kremi151.a3dtictactoe.util.AlertHelper;
import lu.kremi151.a3dtictactoe.util.CubeField;
import lu.kremi151.a3dtictactoe.util.CubeRow;
import lu.kremi151.a3dtictactoe.util.GameCube;

public class GameModeTutorial extends GameMode {

    private final FieldValue player = FieldValue.CROSS;
    private Runnable runAfter;
    private CubeRow highlightingRow = null, hintRow = null;

    private final int fieldHintColor;

    public GameModeTutorial(ActivityInterface activity, GameCube cube) {
        super(activity, cube);

        this.fieldHintColor = getColor(R.color.fieldBackgroundHint);
    }

    @Override
    public boolean onTap(int x, int y, int z) {
        cube.setValueAt(x, y, z, player, false);
        runAfter.run();
        return true;
    }

    @Override
    public void onInit() {
        lesson1();
    }

    @Override
    public int getFieldColor(int x, int y, int z, int previousColor){
        if(hintRow != null && hintRow.contains(x, y, z)){
            return fieldHintColor;
        }else{
            return previousColor;
        }
    }

    @Override
    public int getFieldValueColor(int x, int y, int z, int previousColor){
        if(highlightingRow != null && highlightingRow.contains(x, y, z)){
            return fieldColorWin;
        }else{
            return fieldColorDefault;
        }
    }

    private void lesson1(){
        cube.clear();
        cube.setValueAt(0, 0, 0, player, false);
        cube.setValueAt(1, 0, 0, player, false);
        cube.setValueAt(3, 0, 0, player, false);
        highlightingRow = null;
        hintRow = new CubeRow(
                new CubeField(0, 0, 0),
                new CubeField(1, 0, 0),
                new CubeField(2, 0, 0),
                new CubeField(3, 0, 0)
        );
        updateBoard();
        AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective, R.string.tutorial_build_row).show();
        runAfter = new Runnable() {
            @Override
            public void run() {
                highlightingRow = cube.searchWinningRow();
                if(highlightingRow != null){
                    AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective_success, R.string.tutorial_build_row_success, new Runnable() {
                        @Override
                        public void run() {
                            lesson2();
                        }
                    }).show();
                }
            }
        };
    }

    private void lesson2(){
        cube.clear();
        cube.setValueAt(0, 0, 0, player, false);
        cube.setValueAt(0, 2, 0, player, false);
        cube.setValueAt(0, 3, 0, player, false);
        highlightingRow = null;
        hintRow = new CubeRow(
                new CubeField(0, 0, 0),
                new CubeField(0, 1, 0),
                new CubeField(0, 2, 0),
                new CubeField(0, 3, 0)
        );
        updateBoard();
        AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective, R.string.tutorial_build_row).show();
        runAfter = new Runnable() {
            @Override
            public void run() {
                highlightingRow = cube.searchWinningRow();
                if(highlightingRow != null){
                    AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective_success, R.string.tutorial_build_row_success, new Runnable() {
                        @Override
                        public void run() {
                            lesson3();
                        }
                    }).show();
                }
            }
        };
    }

    private void lesson3(){
        cube.clear();
        cube.setValueAt(0, 0, 0, player, false);
        cube.setValueAt(1, 1, 0, player, false);
        cube.setValueAt(2, 2, 0, player, false);
        highlightingRow = null;
        hintRow = null;
        updateBoard();
        AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective, R.string.tutorial_build_row).show();
        runAfter = new Runnable() {
            @Override
            public void run() {
                highlightingRow = cube.searchWinningRow();
                if(highlightingRow != null){
                    AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective_success, R.string.tutorial_build_row_success, new Runnable() {
                        @Override
                        public void run() {
                            lesson4();
                        }
                    }).show();
                }
            }
        };
    }

    private void lesson4(){
        cube.clear();
        cube.setValueAt(0, 0, 1, player, false);
        cube.setValueAt(0, 0, 2, player, false);
        cube.setValueAt(0, 0, 3, player, false);
        highlightingRow = null;
        hintRow = null;
        updateBoard();
        AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective, R.string.tutorial_build_row).show();
        runAfter = new Runnable() {
            @Override
            public void run() {
                highlightingRow = cube.searchWinningRow();
                if(highlightingRow != null){
                    AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective_success, R.string.tutorial_build_row_success, new Runnable() {
                        @Override
                        public void run() {
                            lesson5();
                        }
                    }).show();
                }
            }
        };
    }

    private void lesson5(){
        cube.clear();
        cube.setValueAt(0, 0, 0, player, false);
        cube.setValueAt(1, 1, 1, player, false);
        cube.setValueAt(3, 3, 3, player, false);
        highlightingRow = null;
        hintRow = null;
        updateBoard();
        AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective, R.string.tutorial_build_row).show();
        runAfter = new Runnable() {
            @Override
            public void run() {
                highlightingRow = cube.searchWinningRow();
                if(highlightingRow != null){
                    AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective_success, R.string.tutorial_build_row_success, new Runnable() {
                        @Override
                        public void run() {
                            lesson6();
                        }
                    }).show();
                }
            }
        };
    }

    private void lesson6(){
        cube.clear();
        cube.setValueAt(3, 0, 0, player, false);
        cube.setValueAt(1, 0, 2, player, false);
        cube.setValueAt(0, 0, 3, player, false);
        highlightingRow = null;
        hintRow = null;
        updateBoard();
        AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective, R.string.tutorial_build_row).show();
        runAfter = new Runnable() {
            @Override
            public void run() {
                highlightingRow = cube.searchWinningRow();
                if(highlightingRow != null){
                    announceWinner(false);
                    AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective_success, R.string.tutorial_complete).show();
                }
            }
        };
    }
}
