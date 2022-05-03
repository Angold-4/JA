package hk.example.projectapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.widget.NestedScrollView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import android.util.Log;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView recyclerView;
    SAdapter sadapter;
    SAdapter enadapter;
    SAdapter zhadapter;

    boolean loading = true; // while searching, do not load any data
    boolean global  = true; // global filtering ?
    boolean s1 = false;     // s1 other than ALL_REGIONS? (chosen)
    boolean s2 = false;     // s2 other than ALL_SCHOOLS? (chosen)

    int i1 = -1;
    int i2 = -1;

    int index;

    private ImageButton butRefresh;
    ProgressBar progressBar; // removed
    NestedScrollView nestedScrollView;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    public static String language;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获得系统当前语言，并不是app语言
        Locale locale = Locale.getDefault();
        language = locale.getLanguage();


        //-Obtain the “ListView” object reference in the “onCreate” method
        spinner1 = (Spinner) findViewById(R.id.region);
        spinner2 = (Spinner) findViewById(R.id.category);
        spinner3 = (Spinner) findViewById(R.id.pn);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        butRefresh = (ImageButton) findViewById(R.id.refresh);
        progressBar = findViewById(R.id.progress_bar);
        nestedScrollView = findViewById(R.id.scroll_view);

        //读取json文件
        JsonHandler jsonHandler = new JsonHandler();
        String jsonContent = jsonHandler.readJsonFile("School_Information.json",this);
        jsonHandler.createArray(jsonContent);


        ArrayList<HashMap<String, String>> IndexList = new ArrayList<>();

        // lazy loading, when app start, load only first 100 data
        // for performance
        // but search will work in its appropriate way :)
        for (int i = 0; i < 100; i++) IndexList.add(School.schoolList.get(i));
        index = 100;

        enadapter = new SAdapter(MainActivity.this, IndexList, "en");
        zhadapter = new SAdapter(MainActivity.this, IndexList, "zh");

        if (language == "en")  setUpRecyclerView(enadapter);
        if (language == "zh")  setUpRecyclerView(zhadapter);;


        // Onclick
        zhadapter.setOnItemClickListener(new SAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                HashMap<String, String> school = sadapter.MapObjectList.get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                String schoolNo = school.get(School.SCHOOL_NO);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, schoolNo);
                startActivity(intent);
            }
        });

        enadapter.setOnItemClickListener(new SAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                HashMap<String, String> school = sadapter.MapObjectList.get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                String schoolNo = school.get(School.SCHOOL_NO);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, schoolNo);
                startActivity(intent);
            }
        });

        //languageButton
        setListener();

        // 1
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] regions = getResources().getStringArray(R.array.region);
                if(regions[pos].equals("ALL REGIONS") || regions[pos].equals("所有地區")) {
                    s1 = false; // false indicate that not enable spinner 1 filtering
                    String[] categories = getResources().getStringArray(R.array.Category);
                    if (s2) {
                        // we have another filtering
                        // perform it

                        sadapter.update(sadapter.PrevMapObjectList); // previous search list
                        sadapter.filteringBy(0, categories[i2], global);
                    }
                } else {
                    if (!s2 && !global) {
                        // if s2 is not be chosen
                        sadapter.update(sadapter.PrevMapObjectList);
                    }

                    i1 = pos; // store the s1 index

                    s1 = true;

                    // else just direct filtering
                    if (s2 && global) {
                        // double filtering
                        sadapter.filteringBy(1, regions[pos], false);
                    } else {
                        sadapter.filteringBy(1, regions[pos], global);
                    }

                    loading = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                s1 = false;
            }
        });


        // 0
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] categories = getResources().getStringArray(R.array.Category);
                if (categories[pos].equals("ALL SCHOOLS") || categories[pos].equals("所有學校")) {
                    s2 = false; // false indicate that not enable spinner 1 filtering
                    String[] regions = getResources().getStringArray(R.array.region);
                    if (s1) {
                        // we have another filtering
                        // perform it
                        sadapter.update(sadapter.PrevMapObjectList); // previous search list
                        sadapter.filteringBy(1, regions[i1], global);
                    }
                }
                else {
                    if (!s1 && !global) {
                        // if s1 is not be chosen
                        sadapter.update(sadapter.PrevMapObjectList);
                    }

                    i2 = pos; // store the s2 index

                    s2 = true;

                    // else just direct filtering
                    if (s1 && global) {
                        // double filtering for global
                        sadapter.filteringBy(0, categories[pos], false);
                    } else {
                        sadapter.filteringBy(0, categories[pos], global);
                    }

                    loading = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // 1. clear all data first
                sadapter.clear();
                // 2. as initializing
                index = 0;
                sadapter.append(index);
                // 3. enable loading and global filtering
                loading = true;
                global = true;
            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] order = getResources().getStringArray(R.array.pn);
                if (order[pos].equals("Default") || order[pos].equals("默認")) {}
                else if(order[pos].equals("POSITIVE ORDER") || order[pos].equals("正序")){
                    // Log.println(Log.DEBUG, "2", "Positive Clicked");
                    sortArrayList();
                }
                else if(order[pos].equals("REVERSE ORDER") || order[pos].equals("倒序")){
                    // Log.println(Log.DEBUG, "2", "Reverse Clicked");
                    sortArrayListReverse();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // check condition
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    //progressBar.setVisibility(View.INVISIBLE);
                    // add data to index page
                    if (loading) {
                       // progressBar.setVisibility(View.VISIBLE);
                        loadIndexData(index);
                        index += 100;
                    }
                }
            }
        });


    }

    private void sortArrayList() {
        Collections.sort(sadapter.MapObjectList, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                return o1.get("English_name").compareTo(o2.get("English_name"));
            }
        });
        sadapter.notifyDataSetChanged();
    }

    private void sortArrayListReverse() {
        Collections.sort(sadapter.MapObjectList, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                return o2.get("English_name").compareTo(o1.get("English_name"));
            }
        });
        sadapter.notifyDataSetChanged();
    }


    private void loadIndexData(int index) {
        // load the index data
        // index specify the starting index
        sadapter.append(index);
    }


    private void setListener(){
        onClick onclick = new onClick();
        //翻译按钮
        butRefresh.setOnClickListener(onclick);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        sadapter.by = "level";
        sadapter.getFilter().filter(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class onClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.refresh:
                    reFreshMain();
            }
        }
    }

    // Set the recycler view -> for searching
    private void setUpRecyclerView(SAdapter tadapter) {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        sadapter = tadapter;

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(sadapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();


        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                global = false;
                if (query == null || query == "") {
                    loading = true;
                    // forget last searching result
                    sadapter.PrevMapObjectList = new ArrayList<>();
                }
                else loading = false;
                sadapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                global = false;
                loading = false;
                if (newText.length() <= 5) return false;
                // Only filtering when > 5 chars
                sadapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    // 手动语言转换函数
    private void languageSwitch(View view){
        // 获得资源
        Resources resources = getResources();
        // 获得设置
        Configuration config = resources.getConfiguration();
        // 获得显示设备
        DisplayMetrics dm = resources.getDisplayMetrics();

        //English的情况
        if(language.equals("en")){
            //UI语言改变
            config.locale = Locale.CHINESE;
            setUpRecyclerView(zhadapter);
            language="zh";
        } else {
            config.locale = Locale.ENGLISH;
            setUpRecyclerView(enadapter);
            language="en";
        }
        //更新设置，语言转换生效
        resources.updateConfiguration(config, dm);
    }

    public void reFreshMain(){
        startActivity(new Intent().setClass(MainActivity.this,
                MainActivity.class));
        MainActivity.this.finish();
    }


}
