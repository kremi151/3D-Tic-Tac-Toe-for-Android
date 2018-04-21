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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import lu.kremi151.a3dtictactoe.adapter.GameModeAdapter;
import lu.kremi151.a3dtictactoe.R;

public class WelcomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_view, container, false);
        return recyclerView;
    }

    @Override
    public void onStart(){
        super.onStart();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new GameModeAdapter(
                this.getLayoutInflater(),
                Arrays.asList(
                        new GameModeAdapter.Item(
                                R.string.gm_single,
                                R.string.gm_single_desc,
                                R.drawable.ic_person_black_24dp
                        ){

                            @Override
                            protected void onClick() {
                                getFragmentManager()
                                        .beginTransaction()
                                        .add(R.id.container, new SingleplayerDifficultyFragment())
                                        .addToBackStack(null)
                                        .commit();
                            }
                        },
                        new GameModeAdapter.Item(
                                R.string.gm_multi_local,
                                R.string.gm_multi_local_desc,
                                R.drawable.ic_people_black_24dp
                        ){

                            @Override
                            protected void onClick() {
                                getFragmentManager()
                                        .beginTransaction()
                                        .add(R.id.container, GameFragment.newLocalMultiplayer())
                                        .addToBackStack(null)
                                        .commit();
                            }
                        }
                )
        ));
    }

}
