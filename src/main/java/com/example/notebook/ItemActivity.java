package com.example.notebook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class ItemActivity extends AppCompatActivity {

    private EditText title_text, content_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        title_text = (EditText) findViewById(R.id.title);
        content_text = (EditText) findViewById(R.id.content);

        Intent intent = getIntent();
        String action = intent.getAction();
        if (action.equals("com.example.notebook.EDIT_ITEM")) {
            String titleText = intent.getStringExtra("title");
            title_text.setText(titleText);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {

            Intent result = getIntent();
            String titleText = title_text.getText().toString();
            String contentText = content_text.getText().toString();
            result.putExtra("title", titleText);
            result.putExtra("content", contentText);
            setResult(Activity.RESULT_OK, result);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
