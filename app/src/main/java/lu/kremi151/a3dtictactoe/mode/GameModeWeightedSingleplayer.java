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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import lu.kremi151.a3dtictactoe.enums.FieldValue;
import lu.kremi151.a3dtictactoe.interfaces.ActivityInterface;
import lu.kremi151.a3dtictactoe.util.CubeField;
import lu.kremi151.a3dtictactoe.util.CubeRow;
import lu.kremi151.a3dtictactoe.util.GameCube;

public class GameModeWeightedSingleplayer extends GameMode {

    private final FieldValue player = FieldValue.CIRCLE;
    private final List<CubeRow> possibilities;
    private final Random random = new Random(System.currentTimeMillis());
    private float attack = 0.5f;
    private float defense = 0.5f;

    public GameModeWeightedSingleplayer(ActivityInterface activity, GameCube cube) {
        super(activity, cube);
        possibilities = new ArrayList<>(cube.getRows());
    }

    @Override
    public void onInit() {
        announceNextPlayer(player);
    }

    private boolean isRowLost(CubeRow row){
        boolean cross = false, circle = false;
        FieldValue a = cube.valueAt(row.getA());
        FieldValue b = cube.valueAt(row.getB());
        FieldValue c = cube.valueAt(row.getC());
        FieldValue d = cube.valueAt(row.getD());
        if(a != FieldValue.EMPTY){
            if(a == FieldValue.CIRCLE){
                circle = true;
            }else if(a == FieldValue.CROSS){
                cross = true;
            }
        }
        if(b != FieldValue.EMPTY){
            if(b == FieldValue.CIRCLE){
                circle = true;
            }else if(b == FieldValue.CROSS){
                cross = true;
            }
        }
        if(c != FieldValue.EMPTY){
            if(c == FieldValue.CIRCLE){
                circle = true;
            }else if(c == FieldValue.CROSS){
                cross = true;
            }
        }
        if(d != FieldValue.EMPTY){
            if(d == FieldValue.CIRCLE){
                circle = true;
            }else if(d == FieldValue.CROSS){
                cross = true;
            }
        }
        return circle && cross;
    }

    @Nullable
    private CubeField findEmptyField(){
        int targetX = random.nextInt(cube.width());
        int targetY = random.nextInt(cube.height());
        int targetZ = random.nextInt(cube.depth());
        if(cube.valueAt(targetX, targetY, targetZ) != FieldValue.EMPTY){
            for(int mx = 0 ; mx < cube.width() ; mx++){
                for(int my = 0 ; my < cube.height() ; my++){
                    for(int mz = 0 ; mz < cube.depth() ; mz++){
                        if(cube.valueAt(mx, my, mz) == FieldValue.EMPTY){
                            return new CubeField(mx, my, mz);
                        }
                    }
                }
            }
            return null;
        }
        return new CubeField(targetX, targetY, targetZ);
    }

    @Override
    public boolean onTap(int x, int y, int z) {
        if(cube.valueAt(x, y, z) == FieldValue.EMPTY){
            cube.setValueAt(x, y, z, player);
            if(!announceWinner()){
                float mind[][][] = new float[cube.width()][cube.height()][cube.depth()];
                Iterator<CubeRow> it = possibilities.iterator();
                while(it.hasNext()){
                    CubeRow row = it.next();
                    if(isRowLost(row)){
                        it.remove();
                    }else{
                        float attackChance = cube.chanceToWinOnRow(player.opposite(), row);
                        float defenceChance = cube.chanceToWinOnRow(player, row);
                        float weightedChance = true
                                ? Math.max(attackChance, defenceChance)
                                : ((attack * attackChance) + (defense * defenceChance));
                        mind[row.getA().getX()][row.getA().getY()][row.getA().getZ()] += weightedChance;
                        mind[row.getB().getX()][row.getB().getY()][row.getB().getZ()] += weightedChance;
                        mind[row.getC().getX()][row.getC().getY()][row.getC().getZ()] += weightedChance;
                        mind[row.getD().getX()][row.getD().getY()][row.getD().getZ()] += weightedChance;
                    }
                }

                CubeField targetField = findEmptyField();
                if(targetField != null){
                    int targetX = targetField.getX();
                    int targetY = targetField.getY();
                    int targetZ = targetField.getZ();
                    float highestChance = mind[targetX][targetY][targetZ];
                    for(int mx = 0 ; mx < cube.width() ; mx++){
                        for(int my = 0 ; my < cube.height() ; my++){
                            for(int mz = 0 ; mz < cube.depth() ; mz++){
                                if(mind[mx][my][mz] > highestChance && cube.valueAt(mx, my, mz) == FieldValue.EMPTY){
                                    targetX = mx;
                                    targetY = my;
                                    targetZ = mz;
                                    highestChance = mind[mx][my][mz];
                                }
                            }
                        }
                    }
                    cube.setValueAt(targetX, targetY, targetZ, player.opposite());
                    announceWinner();
                }else{
                    giveUp(player.opposite());
                }
            }
            return true;
        }
        return false;
    }
}
