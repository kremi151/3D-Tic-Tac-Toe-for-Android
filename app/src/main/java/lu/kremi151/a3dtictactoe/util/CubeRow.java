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

    /**
     * Builds a row
     * @param startX The x coordinate of the first field
     * @param startY The y coordinate of the first field
     * @param startZ The z coordinate of the first field
     * @param dx The offset the x coordinates of the successive fields will be located at. Shall only be -1, 0 or 1.
     * @param dy The offset the y coordinates of the successive fields will be located at. Shall only be -1, 0 or 1.
     * @param dz The offset the z coordinates of the successive fields will be located at. Shall only be -1, 0 or 1.
     * @return Returns the built row
     */
    public static CubeRow spread(int startX, int startY, int startZ, int dx, int dy, int dz){
        return new CubeRow(
                new CubeField(startX, startY, startZ),
                new CubeField(startX + dx, startY + dy, startZ + dz),
                new CubeField(startX + (2 * dx), startY + (2 * dy), startZ + (2 * dz)),
                new CubeField(startX + (3 * dx), startY + (3 * dy), startZ + (3 * dz))
        );
    }
}
