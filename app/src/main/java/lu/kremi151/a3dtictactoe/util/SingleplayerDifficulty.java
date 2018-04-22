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

public enum SingleplayerDifficulty {
    VERY_EASY("veasy", 0.8f),
    EASY("easy", 0.7f),
    CHALLENGING("challenging", 0.6f),
    HARD("hard", 0.5f),
    VERY_HARD("vhard", 0.4f),
    FRUSTRATING("mommy", 0.3f);

    public final String savIdentifier;
    public final float attack;

    private SingleplayerDifficulty(String savIdentifier, float attack){
        this.savIdentifier = savIdentifier;
        this.attack = attack;
    }

}
