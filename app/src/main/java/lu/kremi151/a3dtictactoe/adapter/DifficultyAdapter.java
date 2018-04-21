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

package lu.kremi151.a3dtictactoe.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import lu.kremi151.a3dtictactoe.R;

public class DifficultyAdapter extends RecyclerView.Adapter<DifficultyAdapter.ViewHolder>{

    private final List<? extends Item> items;
    private final LayoutInflater inflater;

    public DifficultyAdapter(LayoutInflater inflater, List<? extends Item> items){
        this.inflater = inflater;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.layout_difficulty, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.item = item;
        holder.title.setText(item.titleRes);
        holder.description.setText(item.descriptionRes);
        holder.rating.setRating(item.rating);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title, description;
        private final RatingBar rating;
        private Item item;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.description = itemView.findViewById(R.id.description);
            this.rating = itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.onClick();
                }
            });
        }
    }

    public abstract static class Item{
        private final int titleRes, descriptionRes;
        private final float rating;

        public Item(int titleRes, int descriptionRes, float rating){
            this.titleRes = titleRes;
            this.descriptionRes = descriptionRes;
            this.rating = rating;
        }

        protected abstract void onClick();
    }
}
