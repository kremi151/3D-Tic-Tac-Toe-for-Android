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

public class CubeRow {

    private final CubeField a, b, c, d;

    public CubeRow(CubeField a, CubeField b, CubeField c, CubeField d){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public CubeField getA() {
        return a;
    }

    public CubeField getB() {
        return b;
    }

    public CubeField getC() {
        return c;
    }

    public CubeField getD() {
        return d;
    }

    public final int fieldCount(){
        return 4;
    }

    public final CubeField getField(int index){
        switch(index){
            case 0: return a;
            case 1: return b;
            case 2: return c;
            case 3: return d;
            default: throw new IndexOutOfBoundsException("Row only has " + fieldCount() + " fields, index requested for " + index);
        }
    }

    public boolean contains(CubeField field){
        return a.equals(field) || b.equals(field) || c.equals(field) || d.equals(field);
    }

    public boolean contains(int x, int y, int z){
        for(int i = 0 ; i < fieldCount() ; i++){
            CubeField field = getField(i);
            if(field.getX() == x && field.getY() == y && field.getZ() == z){
                return true;
            }
        }
        return false;
    }
}
