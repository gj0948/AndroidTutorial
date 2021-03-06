package com.jack.android.tutorial;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jack.android.tutorial.ui.custom.TopBar;


public class TopBarActivity extends ActionBarActivity {

    private TopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_bar);

        topBar = (TopBar) findViewById(R.id.topbar);
        topBar.setTopBarClickListener(new TopBar.TopBarOnClickListener() {
            @Override
            public void onLeftButtonClicked() {
                Toast.makeText(TopBarActivity.this, "left button clicked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRightButtonClicked() {
                Toast.makeText(TopBarActivity.this, "left button clicked", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);
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
