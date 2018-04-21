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

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import lu.kremi151.a3dtictactoe.R;
import lu.kremi151.a3dtictactoe.TTTApp;
import lu.kremi151.a3dtictactoe.adapter.DifficultyAdapter;

public class SingleplayerDifficultyFragment extends Fragment {

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
        SharedPreferences preferences = ((TTTApp)getActivity().getApplication()).getSingleplayerPreferences();
        recyclerView.setAdapter(new DifficultyAdapter(
                this.getLayoutInflater(),
                Arrays.asList(
                        new SingleplayerDifficulty(
                                R.string.difficulty_very_easy,
                                R.string.difficulty_very_easy_desc,
                                2.5f,
                                0.8f,
                                "veasy"
                        ),
                        new SingleplayerDifficulty(
                                R.string.difficulty_easy,
                                R.string.difficulty_easy_desc,
                                3.0f,
                                0.7f,
                                "easy"
                        ),
                        new SingleplayerDifficulty(
                                R.string.difficulty_challenging,
                                R.string.difficulty_challenging_desc,
                                3.5f,
                                0.6f,
                                "challenging"
                        ),
                        new SingleplayerDifficulty(
                                R.string.difficulty_hard,
                                R.string.difficulty_hard_desc,
                                4.0f,
                                0.5f,
                                "hard"
                        ),
                        new SingleplayerDifficulty(
                                R.string.difficulty_very_hard,
                                R.string.difficulty_very_hard_desc,
                                4.5f,
                                0.4f,
                                "vhard"
                        ),
                        new SingleplayerDifficulty(
                                R.string.difficulty_frustrating,
                                R.string.difficulty_frustrating_desc,
                                5.0f,
                                0.3f,
                                "mommy"
                        )
                )
        ));
    }

    private class SingleplayerDifficulty extends DifficultyAdapter.Item{

        private final float attack;
        private final String identifier;

        public SingleplayerDifficulty(int titleRes, int descriptionRes, float rating, float attack, String identifier) {
            super(titleRes, descriptionRes, rating);
            this.attack = Math.min(1f, attack);
            this.identifier = identifier;
        }

        @Override
        protected void onClick() {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, GameFragment.newSingleplayer(attack, 1f - attack, identifier))
                    .addToBackStack(null)
                    .commit();
        }
    }
}
