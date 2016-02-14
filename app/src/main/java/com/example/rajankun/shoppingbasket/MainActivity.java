package com.example.rajankun.shoppingbasket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvItems;
    private ItemsAdapter itemsAdapter;
    private Firebase fireBaseItemsRef;
    private ArrayList<ItemModel> items;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting the firebase context
        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.pbLoading);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        //setting up list view
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();

        //Firebase reference to Items
        fireBaseItemsRef = new Firebase(Constants.fireBaseItemsRef);
        setupFirebaseListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        ItemModel itemModel = itemsAdapter.getItem(pos);
                        itemsAdapter.remove(itemModel);
                        fireBaseItemsRef.child(itemModel.getUniqueId()).setValue(null);
                        return true;
                    }
                });
    }

    private void setupFirebaseListener(){
        //http://stackoverflow.com/questions/27978078/how-to-separate-initial-data-load-from-incremental-children-with-firebase
        // This method is triggered after the 'child_added' events is triggered for pre-existing child nodes.
        // Also since firebase has smart cache data is not loaded twice.
        fireBaseItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        fireBaseItemsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ItemModel item  = dataSnapshot.getValue(ItemModel.class);
                itemsAdapter.add(item);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ItemModel itemModel = dataSnapshot.getValue(ItemModel.class);

                if(items.contains(itemModel)){
                    ItemModel item = items.get(items.indexOf(itemModel));
                    item.setItemName(itemModel.getItemName());
                }
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ItemModel itemModel = dataSnapshot.getValue(ItemModel.class);
                if(items.contains(itemModel)){
                    items.remove(itemModel);
                }
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        etNewItem.setText("");

        //writing to the firebase database
        Firebase itemRef = fireBaseItemsRef.push();
        ItemModel item = new ItemModel(itemRef.getKey(), itemText);
        itemRef.setValue(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
