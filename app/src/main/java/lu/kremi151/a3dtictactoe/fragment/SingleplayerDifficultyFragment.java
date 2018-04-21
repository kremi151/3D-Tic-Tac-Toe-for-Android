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
import android.widget.Button;

import lu.kremi151.a3dtictactoe.R;

public class SingleplayerDifficultyFragment extends Fragment {

    private Button btnVEasy, btnEasy, btnChallenging, btnHard, btnVHard, btnFrustrating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_singleplayer_difficulty, container, false);
        btnVEasy = root.findViewById(R.id.btnDiffVeryEasy);
        btnEasy = root.findViewById(R.id.btnDiffEasy);
        btnChallenging = root.findViewById(R.id.btnDiffChallenging);
        btnHard = root.findViewById(R.id.btnDiffHard);
        btnVHard = root.findViewById(R.id.btnDiffVeryHard);
        btnFrustrating = root.findViewById(R.id.btnDiffFrustrating);

        btnVEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, GameFragment.newSingleplayer(0.8f, 0.2f))
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, GameFragment.newSingleplayer(0.7f, 0.3f))
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnChallenging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, GameFragment.newSingleplayer(0.6f, 0.4f))
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, GameFragment.newSingleplayer(0.5f, 0.5f))
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnVHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, GameFragment.newSingleplayer(0.4f, 0.6f))
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnFrustrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, GameFragment.newSingleplayer(0.3f, 0.7f))
                        .addToBackStack(null)
                        .commit();
            }
        });

        return root;
    }
}
