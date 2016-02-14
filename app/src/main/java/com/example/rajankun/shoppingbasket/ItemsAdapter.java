package com.example.rajankun.shoppingbasket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An Adapter for displaying items in the List
 */
public class ItemsAdapter extends ArrayAdapter<ItemModel> {

    public ItemsAdapter(Context context, ArrayList<ItemModel> itemList) {
        super(context, 0, itemList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ItemModel item = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.itemName);
        textView.setText(item.getItemName());

        return convertView;
    }
}
