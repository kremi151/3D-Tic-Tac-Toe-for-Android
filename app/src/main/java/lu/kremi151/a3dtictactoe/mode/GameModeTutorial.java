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

import android.support.annotation.Nullable;

import java.util.Random;

import lu.kremi151.a3dtictactoe.R;
import lu.kremi151.a3dtictactoe.enums.FieldValue;
import lu.kremi151.a3dtictactoe.interfaces.ActivityInterface;
import lu.kremi151.a3dtictactoe.interfaces.ActivityAction;
import lu.kremi151.a3dtictactoe.util.AlertHelper;
import lu.kremi151.a3dtictactoe.util.CubeField;
import lu.kremi151.a3dtictactoe.util.CubeRow;
import lu.kremi151.a3dtictactoe.util.GameCube;
import lu.kremi151.a3dtictactoe.util.SingleplayerState;

public class GameModeTutorial extends GameMode {

    private final Objective LESSON_7 = new ObjectiveOutplay();

    private final Objective LESSON_6 = new ObjectiveRow(
            new CubeRow(
                    new CubeField(3, 0, 0),
                    new CubeField(2, 0, 1),
                    new CubeField(1, 0, 2),
                    new CubeField(0, 0, 3)
            ), false).setNextObjective(LESSON_7);

    private final Objective LESSON_5 = new ObjectiveRow(
            new CubeRow(
                    new CubeField(0, 0, 0),
                    new CubeField(1, 1, 1),
                    new CubeField(2, 2, 2),
                    new CubeField(3, 3, 3)
            ), false).setNextObjective(LESSON_6);

    private final Objective LESSON_4 = new ObjectiveRow(
            new CubeRow(
                    new CubeField(0, 0, 0),
                    new CubeField(0, 0, 1),
                    new CubeField(0, 0, 2),
                    new CubeField(0, 0, 3)
            ), false).setNextObjective(LESSON_5);

    private final Objective LESSON_3 = new ObjectiveRow(
            new CubeRow(
                    new CubeField(0, 0, 0),
                    new CubeField(1, 1, 0),
                    new CubeField(2, 2, 0),
                    new CubeField(3, 3, 0)
            ), false).setNextObjective(LESSON_4);

    private final Objective LESSON_2 = new ObjectiveRow(
            new CubeRow(
                    new CubeField(0, 0, 0),
                    new CubeField(0, 1, 0),
                    new CubeField(0, 2, 0),
                    new CubeField(0, 3, 0)
            ), true).setNextObjective(LESSON_3);

    private final Objective LESSON_1 = new ObjectiveRow(
            new CubeRow(
                    new CubeField(0, 0, 0),
                    new CubeField(1, 0, 0),
                    new CubeField(2, 0, 0),
                    new CubeField(3, 0, 0)
            ), true).setNextObjective(LESSON_2);

    private final FieldValue player = FieldValue.CROSS;
    private Objective currentObjective = LESSON_1;
    private final Random random = new Random(System.currentTimeMillis());
    private CubeRow highlightingRow = null, hintRow = null;
    private ActivityAction actionNext, actionRetry;

    private final int fieldHintColor;

    public GameModeTutorial(ActivityInterface activity, GameCube cube) {
        super(activity, cube);

        this.fieldHintColor = getColor(R.color.fieldBackgroundHint);
    }

    @Override
    public boolean onTap(int x, int y, int z) {
        return currentObjective.onTap(x, y, z);
    }

    @Override
    public void onInit() {
        actionRetry = getInterface().buildAction(R.string.retry, new Runnable() {
            @Override
            public void run() {
                if(currentObjective != null){
                    currentObjective.onInit();
                }
            }
        }).create();
        actionNext = getInterface().buildAction(R.string.next, new Runnable() {
            @Override
            public void run() {
                if(currentObjective != null && currentObjective.getNextObjective() != null){
                    currentObjective = currentObjective.getNextObjective();
                    currentObjective.onInit();
                    lockGame(false);
                    actionNext.enabled = false;
                    getInterface().updateActions();
                }
            }
        }).setEnabled(false).setVisible(true).create();
        currentObjective.onInit();
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

    private abstract class Objective{

        private Objective nextObjective;

        public abstract void onInit();
        public abstract boolean onTap(int x, int y, int z);

        final Objective setNextObjective(@Nullable Objective objective){
            this.nextObjective = objective;
            return this;
        }

        @Nullable
        final Objective getNextObjective(){
            return nextObjective;
        }

    }

    private class ObjectiveRow extends Objective{
        private final CubeRow row;
        private final boolean hint;

        private ObjectiveRow(CubeRow row, boolean hint){
            this.row = row;
            this.hint = hint;
        }

        @Override
        public void onInit() {
            cube.clear();
            int hide = random.nextInt(cube.dimension());
            for(int i = 0 ; i < cube.dimension() ; i++){
                if(i != hide){
                    CubeField field = this.row.getField(i);
                    cube.setValueAt(field.getX(), field.getY(), field.getZ(), player, false);
                }
            }
            highlightingRow = null;
            hintRow = hint ? this.row : null;
            updateBoard();
            lockGame(false);
            AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective, R.string.tutorial_build_row).show();
        }

        @Override
        public boolean onTap(int x, int y, int z) {
            cube.setValueAt(x, y, z, player, false);
            highlightingRow = cube.searchWinningRow();
            if(highlightingRow != null){
                if(getNextObjective() == null){
                    announceWinner(false);
                    AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective_success, R.string.tutorial_complete).show();
                }else{
                    AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective_success, R.string.tutorial_build_row_success).show();
                    lockGame(true);
                    actionNext.enabled = true;
                    getInterface().updateActions();
                }
            }
            return true;
        }
    }

    private class ObjectiveOutplay extends Objective{

        private SingleplayerState singleplayerState;

        @Override
        public void onInit() {
            cube.clear();
            cube.setValueAt(1, 0, 0, player, false);
            cube.setValueAt(1, 1, 0, player, false);
            cube.setValueAt(2, 3, 0, player, false);
            cube.setValueAt(3, 3, 0, player, false);
            highlightingRow = null;
            hintRow = null;
            updateBoard();
            lockGame(false);
            this.singleplayerState = new SingleplayerState(player.opposite(), cube);
            AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective, R.string.tutorial_build_row).show();
        }

        @Override
        public boolean onTap(int x, int y, int z) {
            if(cube.valueAt(x, y, z) == FieldValue.EMPTY){
                cube.setValueAt(x, y, z, player);
                highlightingRow = cube.searchWinningRow();
                if(highlightingRow == null){
                    singleplayerState.doTurn();
                    highlightingRow = cube.searchWinningRow();
                    if(highlightingRow != null){
                        lockGame(true);
                        AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective, R.string.tutorial_build_row_fail).show();
                    }
                }else{
                    if(getNextObjective() == null){
                        announceWinner(false);
                        AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective_success, R.string.tutorial_complete).show();
                    }else{
                        AlertHelper.buildMessageAlert(getContext(), R.string.tutorial_objective_success, R.string.tutorial_build_row_success).show();
                        lockGame(true);
                        actionNext.enabled = true;
                        getInterface().updateActions();
                    }
                }
                return true;
            }else{
                return false;
            }
        }
    }
}
