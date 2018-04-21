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

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import lu.kremi151.a3dtictactoe.fragment.BaseFragment;
import lu.kremi151.a3dtictactoe.fragment.GameFragment;
import lu.kremi151.a3dtictactoe.fragment.SingleplayerDifficultyFragment;
import lu.kremi151.a3dtictactoe.fragment.WelcomeFragment;
import lu.kremi151.a3dtictactoe.mode.GameModeLocalMultiplayer;
import lu.kremi151.a3dtictactoe.mode.GameModeSingleplayer;
import lu.kremi151.a3dtictactoe.util.GameCube;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onStart(){
        super.onStart();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                final boolean showHome = getSupportFragmentManager().getBackStackEntryCount() > 0;
                getSupportActionBar().setDisplayHomeAsUpEnabled(showHome);
                getSupportActionBar().setDisplayShowHomeEnabled(showHome);
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new WelcomeFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final Fragment top = getSupportFragmentManager().findFragmentById(R.id.container);
        if(!(top instanceof BaseFragment) || !((BaseFragment)top).onBackPressed()){
            if(getSupportFragmentManager().getBackStackEntryCount() > 1){
                getSupportFragmentManager().popBackStack();
            }else{
                super.onBackPressed();
            }
        }
    }
}
