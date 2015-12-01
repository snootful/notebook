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
    private int textViewResourceId;
    private List<Item> items;

    public ItemAdapter(Context context, int resource, int textViewResourceId, List<Item> items) {
        super(context, resource, textViewResourceId, items);
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = super.getView(position, convertView, parent);

        final Item item = getItem(position);

        TextView titleView = (TextView) itemView.findViewById(android.R.id.text1);
        TextView dateView = (TextView) itemView.findViewById(android.R.id.text2);

        titleView.setText(item.getTitle());
        dateView.setText(item.getLocaleDatetime());

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
