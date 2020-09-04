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
import lu.kremi151.a3dtictactoe.TTTApp
import lu.kremi151.a3dtictactoe.adapter.DifficultyAdapter
import lu.kremi151.a3dtictactoe.enums.SingleplayerDifficulty
import lu.kremi151.a3dtictactoe.fragment.GameFragment.Companion.newSingleplayer
import kotlin.math.min

class SingleplayerDifficultyFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: DifficultyAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        recyclerView = inflater.inflate(R.layout.fragment_recycler_view, container, false) as RecyclerView
        return recyclerView
    }

    override fun onStart() {
        super.onStart()
        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        adapter = DifficultyAdapter(
                this.layoutInflater,
                (activity!!.application as TTTApp).savegame,
                listOf(
                        SingleplayerDifficultyItem(
                                R.string.difficulty_very_easy,
                                R.string.difficulty_very_easy_desc,
                                2.5f,
                                SingleplayerDifficulty.VERY_EASY
                        ),
                        SingleplayerDifficultyItem(
                                R.string.difficulty_easy,
                                R.string.difficulty_easy_desc,
                                3.0f,
                                SingleplayerDifficulty.EASY
                        ),
                        SingleplayerDifficultyItem(
                                R.string.difficulty_challenging,
                                R.string.difficulty_challenging_desc,
                                3.5f,
                                SingleplayerDifficulty.CHALLENGING
                        ),
                        SingleplayerDifficultyItem(
                                R.string.difficulty_hard,
                                R.string.difficulty_hard_desc,
                                4.0f,
                                SingleplayerDifficulty.HARD
                        ),
                        SingleplayerDifficultyItem(
                                R.string.difficulty_very_hard,
                                R.string.difficulty_very_hard_desc,
                                4.5f,
                                SingleplayerDifficulty.VERY_HARD
                        ),
                        SingleplayerDifficultyItem(
                                R.string.difficulty_frustrating,
                                R.string.difficulty_frustrating_desc,
                                5.0f,
                                SingleplayerDifficulty.FRUSTRATING
                        )
                )
        )
        recyclerView!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter!!.notifyDataSetChanged()
    }

    private inner class SingleplayerDifficultyItem(
            titleRes: Int,
            descriptionRes: Int,
            rating: Float,
            difficulty: SingleplayerDifficulty
    ) : DifficultyAdapter.Item(titleRes, descriptionRes, rating) {

        private val attack: Float = min(1f, difficulty.attack)

        override fun onClick() {
            fragmentManager
                    ?.beginTransaction()
                    ?.add(R.id.container, newSingleplayer(attack, 1f - attack, tag))
                    ?.addToBackStack(null)
                    ?.commit()
        }

        init {
            tag = difficulty.savIdentifier
        }
    }
}