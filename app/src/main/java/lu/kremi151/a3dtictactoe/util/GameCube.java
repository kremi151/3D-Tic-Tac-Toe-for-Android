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

package lu.kremi151.a3dtictactoe.util;

import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lu.kremi151.a3dtictactoe.enums.FieldValue;

public class GameCube {

    private final FieldValue cube[][][] = new FieldValue[4][4][4];
    private final List<CubeRow> possibilities;
    private final LinkedList<Move> history = new LinkedList<>();

    public GameCube(){
        clear();
        this.possibilities = PossibilitiesCalculator.calculatePossibilities(4);
    }

    public void clear(){
        for(int x = 0 ; x < width() ; x++){
            for(int y = 0 ; y < height() ; y++){
                for(int z = 0 ; z < depth() ; z++){
                    cube[x][y][z] = FieldValue.EMPTY;
                }
            }
        }
        history.clear();
    }

    public FieldValue valueAt(int x, int y, int z){
        return cube[x][y][z];
    }

    public FieldValue valueAt(CubeField field){
        return cube[field.getX()][field.getY()][field.getZ()];
    }

    public void setValueAt(int x, int y, int z, FieldValue value, boolean log){
        cube[x][y][z] = value;
        if(log) history.add(new Move(value, x, y, z));
    }

    public void setValueAt(int x, int y, int z, FieldValue value){
        setValueAt(x, y, z, value, true);
    }

    @Nullable
    public Move getLastMove(){
        if(history.size() > 0){
            return history.getLast();
        }else{
            return null;
        }
    }

    public boolean hasMoves(){
        return history.size() > 0;
    }

    public int dimension(){
        return 4;
    }

    @Deprecated
    public int width(){
        return dimension();
    }

    @Deprecated
    public int height(){
        return dimension();
    }

    @Deprecated
    public int depth(){
        return dimension();
    }

    public List<CubeRow> getRows(){
        return Collections.unmodifiableList(possibilities);
    }

    @Nullable
    public CubeRow searchWinningRow(){
        for(CubeRow row : possibilities){
            if(evaluateRow(row) != FieldValue.EMPTY){
                return row;
            }
        }
        return null;
    }

    public FieldValue evaluateRow(CubeRow row){
        if(valueAt(row.getA()) == valueAt(row.getB())
                && valueAt(row.getB()) == valueAt(row.getC())
                && valueAt(row.getC()) == valueAt(row.getD())){
            return valueAt(row.getA());
        }else{
            return FieldValue.EMPTY;
        }
    }

    public float chanceToWinOnField(FieldValue player, CubeField field){
        float chance = 0.0f;
        int rows = 0;
        for(CubeRow row : possibilities){
            if(row.contains(field)){
                chance += chanceToWinOnRow(player, row);
                rows++;
            }
        }
        return rows > 0 ? (chance / rows) : 0f;
    }

    public float chanceToWinOnRow(FieldValue player, CubeRow row){
        float index = 0.0f;
        if(valueAt(row.getA()) == player){
            index += 0.25f;
        }else if(valueAt(row.getA()) == FieldValue.EMPTY){
            index += 0.1f;
        }else{
            return 0f;
        }
        if(valueAt(row.getB()) == player){
            index += 0.25f;
        }else if(valueAt(row.getB()) == FieldValue.EMPTY){
            index += 0.1f;
        }else{
            return 0f;
        }
        if(valueAt(row.getC()) == player){
            index += 0.25f;
        }else if(valueAt(row.getC()) == FieldValue.EMPTY){
            index += 0.1f;
        }else{
            return 0f;
        }
        if(valueAt(row.getD()) == player){
            index += 0.25f;
        }else if(valueAt(row.getD()) == FieldValue.EMPTY){
            index += 0.1f;
        }else{
            return 0f;
        }
        index = Math.min(index, 1.0f);
        return index * index;
    }

    public class Move extends CubeField{

        private final FieldValue player;

        public Move(FieldValue player, int x, int y, int z) {
            super(x, y, z);
            this.player = player;
        }
    }
}
