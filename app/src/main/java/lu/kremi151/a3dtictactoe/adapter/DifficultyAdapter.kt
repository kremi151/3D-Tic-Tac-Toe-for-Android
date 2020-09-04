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

package lu.kremi151.a3dtictactoe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import lu.kremi151.a3dtictactoe.R
import lu.kremi151.a3dtictactoe.interfaces.Savegame

class DifficultyAdapter(
        private val inflater: LayoutInflater,
        private val savegame: Savegame,
        private val items: List<Item>
) : RecyclerView.Adapter<DifficultyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.layout_difficulty, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.item = item
        holder.title.setText(item.titleRes)
        holder.description.setText(item.descriptionRes)
        holder.rating.rating = item.rating
        val tag = item.tag
        holder.trophy.visibility = if (tag != null && savegame.hasMastered(tag)) View.VISIBLE else View.INVISIBLE
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val rating: RatingBar = itemView.findViewById(R.id.ratingBar)
        val trophy: ImageView = itemView.findViewById(R.id.trophy)
        var item: Item? = null

        init {
            itemView.setOnClickListener { item?.onClick() }
        }
    }

    abstract class Item(val titleRes: Int, val descriptionRes: Int, val rating: Float) {

        var tag: String? = null

        abstract fun onClick()

    }

}