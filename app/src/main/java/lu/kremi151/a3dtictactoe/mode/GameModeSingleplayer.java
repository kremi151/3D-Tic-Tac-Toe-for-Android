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
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import lu.kremi151.a3dtictactoe.enums.FieldValue;
import lu.kremi151.a3dtictactoe.util.CubeField;
import lu.kremi151.a3dtictactoe.util.CubeRow;
import lu.kremi151.a3dtictactoe.util.GameCube;

public class GameModeSingleplayer extends GameMode {

    private final FieldValue player = FieldValue.CIRCLE;
    private final List<CubeRow> possibilities;
    private final List<CubeField> fields;
    private final Random random = new Random(System.currentTimeMillis());
    private float attack = 0.5f;
    private float defense = 0.5f;
    private final float mind[][][] = new float[cube.width()][cube.height()][cube.depth()];
    private float mindMax = 1.0f;

    public GameModeSingleplayer(Context context, GameCube cube) {
        super(context, cube);
        possibilities = new ArrayList<>(cube.getRows());
        fields = new ArrayList<>(cube.width() * cube.height() * cube.depth());
        for(int x = 0 ; x < cube.width() ; x++){
            for(int y = 0 ; y < cube.height() ; y++){
                for(int z = 0 ; z < cube.depth() ; z++){
                    fields.add(new CubeField(x, y, z));
                }
            }
        }
    }

    private void resetMind(){
        mindMax = 1.0f;
        for(int x = 0 ; x < cube.width() ; x++){
            for(int y = 0 ; y < cube.height() ; y++){
                for(int z = 0 ; z < cube.depth() ; z++){
                    mind[x][y][z] = 0.0f;
                }
            }
        }
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

    private float rowProbability(CubeRow row){
        /*return Math.max(
                attack * cube.chanceToWinOnRow(player.opposite(), row),
                defense * cube.chanceToWinOnRow(player, row)
        );*/
        return attack * cube.chanceToWinOnRow(player.opposite(), row) +
                defense * cube.chanceToWinOnRow(player, row);
    }

    @Override
    public boolean onTap(int x, int y, int z) {
        if(cube.valueAt(x, y, z) == FieldValue.EMPTY){
            cube.setValueAt(x, y, z, player);
            if(!announceWinner()){
                resetMind();
                Iterator<CubeRow> it = possibilities.iterator();
                while(it.hasNext()){
                    CubeRow row = it.next();

                    final float probability = Math.min(1f, rowProbability(row));
                    if(probability == 0.0f){
                        it.remove();
                    }else{
                        for(int fieldIndex = 0 ; fieldIndex < row.fieldCount() ; fieldIndex++){
                            CubeField field = row.getField(fieldIndex);
                            float value = mind[field.getX()][field.getY()][field.getZ()];
                            value = Math.max(value, probability);
                            mind[field.getX()][field.getY()][field.getZ()] = value;
                            if(value > mindMax){
                                mindMax = value;
                            }
                        }
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

    @Override
    public int getFieldColor(int x, int y, int z){
        /*int factor = (int)Math.ceil((255f * mind[x][y][z]) / mindMax);
        return Color.rgb(255, 255 - factor, 255 - factor);*/
        return super.getFieldColor(x, y, z);
    }
}
