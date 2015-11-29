package com.example.notebook;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by john on 11/28/15.
 */
public class ItemAdapter extends ArrayAdapter<Item> {
    private int resource;
    private List<Item> items;

    public ItemAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = super.getView(position, convertView, parent);

        final Item item = getItem(position);

        TextView titleView = (TextView) itemView.findViewById(android.R.id.text1);
        titleView.setText(item.getTitle());

        return itemView;
    }

    public Item get(int index) {
        return items.get(index);
    }

    public void set(int index, Item item) {
        if (index >= 0 && index < items.size()) {
            items.set(index, item);
            notifyDataSetChanged();
        }
    }
}
