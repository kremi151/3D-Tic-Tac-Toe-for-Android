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

package lu.kremi151.a3dtictactoe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lu.kremi151.a3dtictactoe.R
import lu.kremi151.a3dtictactoe.adapter.GameModeAdapter
import lu.kremi151.a3dtictactoe.fragment.GameFragment.Companion.newLocalMultiplayer
import lu.kremi151.a3dtictactoe.fragment.GameFragment.Companion.newTutorial
import java.util.*

class WelcomeFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var layoutManager: LinearLayoutManager? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        recyclerView = inflater.inflate(R.layout.fragment_recycler_view, container, false) as RecyclerView
        return recyclerView
    }

    override fun onStart() {
        super.onStart()
        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = GameModeAdapter(
                this.layoutInflater,
                Arrays.asList(
                        object : GameModeAdapter.Item(
                                R.string.gm_tutorial,
                                R.string.gm_tutorial_desc,
                                R.drawable.ic_school_black_24dp
                        ) {
                            override fun onClick() {
                                fragmentManager
                                        ?.beginTransaction()
                                        ?.add(R.id.container, newTutorial())
                                        ?.addToBackStack(null)
                                        ?.commit()
                            }
                        },
                        object : GameModeAdapter.Item(
                                R.string.gm_single,
                                R.string.gm_single_desc,
                                R.drawable.ic_person_black_24dp
                        ) {
                            override fun onClick() {
                                fragmentManager
                                        ?.beginTransaction()
                                        ?.add(R.id.container, SingleplayerDifficultyFragment())
                                        ?.addToBackStack(null)
                                        ?.commit()
                            }
                        },
                        object : GameModeAdapter.Item(
                                R.string.gm_multi_local,
                                R.string.gm_multi_local_desc,
                                R.drawable.ic_people_black_24dp
                        ) {
                            override fun onClick() {
                                fragmentManager
                                        ?.beginTransaction()
                                        ?.add(R.id.container, newLocalMultiplayer())
                                        ?.addToBackStack(null)
                                        ?.commit()
                            }
                        }
                )
        )
    }
}