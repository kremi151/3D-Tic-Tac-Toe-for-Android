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

package lu.kremi151.a3dtictactoe.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import lu.kremi151.a3dtictactoe.GameModeAdapter;
import lu.kremi151.a3dtictactoe.R;

public class WelcomeFragment extends Fragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listView = (ListView) inflater.inflate(R.layout.fragment_welcome, container, false);
        return listView;
    }

    @Override
    public void onStart(){
        super.onStart();
        listView.setAdapter(new GameModeAdapter(
                this.getLayoutInflater(),
                Arrays.asList(
                        new GameModeAdapter.Item(
                                R.string.gm_single,
                                R.string.gm_single_desc,
                                R.drawable.ic_person_black_24dp
                        ),
                        new GameModeAdapter.Item(
                                R.string.gm_multi_local,
                                R.string.gm_multi_local_desc,
                                R.drawable.ic_people_black_24dp
                        )
                )
        ));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new SingleplayerDifficultyFragment())
                                .commit();
                        break;
                    case 1:
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, GameFragment.newLocalMultiplayer())
                                .commit();
                        break;
                }
            }
        });
    }

}
