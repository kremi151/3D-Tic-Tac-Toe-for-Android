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

package lu.kremi151.a3dtictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import lu.kremi151.a3dtictactoe.fragment.GameFragment;
import lu.kremi151.a3dtictactoe.fragment.SingleplayerDifficultyFragment;
import lu.kremi151.a3dtictactoe.mode.GameModeLocalMultiplayer;
import lu.kremi151.a3dtictactoe.mode.GameModeSingleplayer;
import lu.kremi151.a3dtictactoe.util.GameCube;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.gm_single:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new SingleplayerDifficultyFragment())
                        .commit();
                return true;
            case R.id.gm_multi_local:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, GameFragment.newLocalMultiplayer())
                        .commit();
                return true;
        }
        return false;
    }
}
