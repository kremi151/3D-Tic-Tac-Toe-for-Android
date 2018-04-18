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

import java.util.ArrayList;
import java.util.List;

public class PossibilitiesCalculator {

    public static List<CubeRow> calculatePossibilities(int size){
        ArrayList<CubeRow> list = new ArrayList<>(76);
        for(int z = 0 ; z < size ; z++){
            list.add(new CubeRow(
                    new CubeField(0, 0, z),
                    new CubeField(1, 1, z),
                    new CubeField(2, 2, z),
                    new CubeField(3, 3, z)
            ));
            list.add(new CubeRow(
                    new CubeField(0, 3, z),
                    new CubeField(1, 2, z),
                    new CubeField(2, 1, z),
                    new CubeField(3, 0, z)
            ));
            for(int x = 0 ; x < size ; x++){
                list.add(new CubeRow(
                        new CubeField(x, 0, z),
                        new CubeField(x, 1, z),
                        new CubeField(x, 2, z),
                        new CubeField(x, 3, z)
                ));
            }
            for(int y = 0 ; y < size ; y++){
                list.add(new CubeRow(
                        new CubeField(0, y, z),
                        new CubeField(1, y, z),
                        new CubeField(2, y, z),
                        new CubeField(3, y, z)
                ));
            }
        }
        for(int x = 0 ; x < size ; x++){
            list.add(new CubeRow(
                    new CubeField(x, 0, 0),
                    new CubeField(x, 1, 1),
                    new CubeField(x, 2, 2),
                    new CubeField(x, 3, 3)
            ));
            list.add(new CubeRow(
                    new CubeField(x, 3, 0),
                    new CubeField(x, 2, 1),
                    new CubeField(x, 1, 2),
                    new CubeField(x, 0, 3)
            ));
            for(int y = 0 ; y < size ; y++){
                list.add(new CubeRow(
                        new CubeField(x, y, 0),
                        new CubeField(x, y, 1),
                        new CubeField(x, y, 2),
                        new CubeField(x, y, 3)
                ));
            }
        }
        for(int y = 0 ; y < size ; y++){
            list.add(new CubeRow(
                    new CubeField(0, y, 0),
                    new CubeField(1, y, 1),
                    new CubeField(2, y, 2),
                    new CubeField(3, y, 3)
            ));
            list.add(new CubeRow(
                    new CubeField(3, y, 0),
                    new CubeField(2, y, 1),
                    new CubeField(1, y, 2),
                    new CubeField(0, y, 3)
            ));
        }
        list.add(new CubeRow(
                new CubeField(0, 0, 0),
                new CubeField(1, 1, 1),
                new CubeField(2, 2, 2),
                new CubeField(3, 3, 3)
        ));
        list.add(new CubeRow(
                new CubeField(0, 0, 3),
                new CubeField(1, 1, 2),
                new CubeField(2, 2, 1),
                new CubeField(3, 3, 0)
        ));
        list.add(new CubeRow(
                new CubeField(3, 0, 0),
                new CubeField(2, 1, 1),
                new CubeField(1, 2, 2),
                new CubeField(0, 3, 3)
        ));
        list.add(new CubeRow(
                new CubeField(3, 0, 3),
                new CubeField(2, 1, 2),
                new CubeField(1, 2, 1),
                new CubeField(0, 3, 0)
        ));
        return list;
    }
}
