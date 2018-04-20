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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GameModeAdapter extends BaseAdapter{

    private final List<Item> items;
    private final LayoutInflater inflater;

    public GameModeAdapter(LayoutInflater inflater, List<Item> items){
        this.inflater = inflater;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = inflater.inflate(R.layout.layout_gamemode_item, viewGroup, false);
            holder = new ViewHolder();
            holder.title = view.findViewById(R.id.title);
            holder.description = view.findViewById(R.id.description);
            holder.preview = view.findViewById(R.id.preview);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        Item item = items.get(i);
        holder.title.setText(item.titleRes);
        holder.description.setText(item.descriptionRes);
        holder.preview.setImageResource(item.previewRes);
        return view;
    }

    private static class ViewHolder{
        TextView title, description;
        ImageView preview;
    }

    public static class Item{
        private final int titleRes, descriptionRes, previewRes;

        public Item(int titleRes, int descriptionRes, int previewRes){
            this.titleRes = titleRes;
            this.descriptionRes = descriptionRes;
            this.previewRes = previewRes;
        }
    }
}
