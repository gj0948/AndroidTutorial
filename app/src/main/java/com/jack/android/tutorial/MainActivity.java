package com.jack.android.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final Class<?>[] mActivities = new Class<?>[]{
            CalculatorActivity.class,
            FlowLayoutActivity.class,
            TopBarActivity.class,
            ArcMenuActivity.class,
            ScratchCardActivity.class
    };
    AdapterView.OnItemClickListener mMainMenuOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            startActivity(new Intent(MainActivity.this, mActivities[i]));
        }
    };
    private ListView mListView;
    private ArrayAdapter<String> mMainMenuAdapter;
    private String[] mMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainMenu = getResources().getStringArray(R.array.main_menu);
        mMainMenuAdapter = new ArrayAdapter<>(this, R.layout.item_list_main, mMainMenu);

        mListView = (ListView) findViewById(R.id.listMainMenu);
        mListView.setAdapter(mMainMenuAdapter);

        mListView.setOnItemClickListener(mMainMenuOnItemClickListener);
    }
}
