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

    private final int dimension;
    private final FieldValue cube[][][];
    private final List<CubeRow> possibilities;
    private final LinkedList<Move> history = new LinkedList<>();

    public GameCube(int dimension){
        this.cube = new FieldValue[dimension][dimension][dimension];
        this.dimension = dimension;
        clear();
        this.possibilities = PossibilitiesCalculator.calculatePossibilities(dimension);
    }

    public GameCube(){
        this(4);
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
        return this.dimension;
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
        final FieldValue comp = valueAt(row.getField(0));
        for(int i = 1 ; i < row.fieldCount() ; i++){
            if(comp != valueAt(row.getField(i))){
                return FieldValue.EMPTY;
            }
        }
        return comp;
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

        for(int i = 0 ; i < row.fieldCount() ; i++){
            final CubeField field = row.getField(i);
            if(valueAt(field) == player){
                index += 1.0f;
            }else if(valueAt(field) == FieldValue.EMPTY){
                index += 0.5;
            }else{
                return 0f;
            }
        }

        index = Math.min(1.0f, index / row.fieldCount());
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
