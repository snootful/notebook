package com.example.notebook;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemDAO itemDAO;
    private List<Item> items;
    private ItemAdapter itemAdapter;
    private int selectedCount = 0;
    private MenuItem delete_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.notebook.ADD_ITEM");
                startActivityForResult(intent, 0);
            }
        });

        ListView item_list = (ListView) findViewById(R.id.item_list);
        int layoutId = android.R.layout.simple_list_item_1;
        itemDAO = new ItemDAO(getApplicationContext());
        if (itemDAO.getCount() == 0) {
            itemDAO.sample();
        }
        items = itemDAO.getAll();
        itemAdapter = new ItemAdapter(this, layoutId, items);
        item_list.setAdapter(itemAdapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Item item = itemAdapter.getItem(position);

                if (selectedCount > 0) {
                    processMenu(item);
                    itemAdapter.set(position, item);
                } else {
                    Intent intent = new Intent("com.example.notebook.EDIT_ITEM");
                    intent.putExtra("position", position);
                    intent.putExtra("com.example.notebook.Item", item);
                    startActivityForResult(intent, 1);
                }
            }
        };
        item_list.setOnItemClickListener(itemClickListener);

        AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Item item = itemAdapter.getItem(position);
                processMenu(item);
                itemAdapter.set(position, item);
                return true;
            }
        };

        item_list.setOnItemLongClickListener(itemLongClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        delete_item = menu.findItem(R.id.delete_item);
        processMenu(null);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Item item = (Item) data.getExtras().getSerializable("com.example.notebook.Item");
            if (requestCode == 0) {
                item = itemDAO.insert(item);
                items.add(item);
                itemAdapter.notifyDataSetChanged();
            } else if (requestCode == 1) {
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    itemDAO.update(item);
                    items.set(position, item);
                    itemAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void processMenu(Item item) {
        if (item != null) {
            item.setSelected(!item.isSelected());

            if (item.isSelected()) {
                selectedCount++;
            } else {
                selectedCount--;
            }
        }
        delete_item.setVisible(selectedCount > 0);
    }

    public void clickMenuItem(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.delete_item:
                if (selectedCount == 0) {
                    break;
                }

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                String message = getString(R.string.delete_item);
                dialog.setTitle(R.string.action_delete)
                        .setMessage(String.format(message, selectedCount));
                dialog.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int index = itemAdapter.getCount() - 1;
                                while (index > -1) {
                                    Item item = itemAdapter.get(index);

                                    if (item.isSelected()) {
                                        itemAdapter.remove(item);
                                        itemDAO.delete(item.getId());
                                    }
                                    index--;
                                }

                                itemAdapter.notifyDataSetChanged();
                                selectedCount = 0;
                                processMenu(null);
                            }
                        });
                dialog.setNegativeButton(android.R.string.no, null);
                dialog.show();

                break;
        }
    }
}