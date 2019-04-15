package kick.career.upgradtask;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kick.career.upgradtask.RoomBuilder.DataViewModel;
import kick.career.upgradtask.RoomBuilder.StoredDatabase;

public class QuestionListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , AdapterView.OnItemClickListener {

    private DataViewModel mWordViewModel;
    ListView listView;
    ArrayList<StoredDatabase> wordsss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


wordsss=new ArrayList<StoredDatabase>();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        addMenuItemInNavMenuDrawer();
        listView = (ListView) findViewById(R.id.paperList);

        mWordViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        listView.setOnItemClickListener(QuestionListActivity.this);

        mWordViewModel.getAllWords().observe(this, new Observer<List<StoredDatabase>>() {
            @Override
            public void onChanged(@Nullable final List<StoredDatabase> words) {
                // Update the cached copy of the words in the adapter.
                if(words.size()>0) {

                    SharedPreferences preferences = QuestionListActivity.this.getSharedPreferences("user_inf", 0);
                    String s = preferences.getString("s", null);

                    String s1[]=s.split("/");

                    wordsss.addAll(words);

                    String s2=s1[0];

                    Log.d("size",wordsss.size()+"   "+s2);
                    ArrayList<StoredDatabase> list=new ArrayList<>();
                    for(int i=0;i<wordsss.size();i++)
                    {
                        Log.d("size", wordsss.get(i).getUid()+"   "+s2);
                        if(wordsss.get(i).getUid().startsWith("c"))
                        {
                            list.add(wordsss.get(i));
                        }
                    }

                    // Toast.makeText(QuestionListActivity.this,,Toast.LENGTH_SHORT).show();
                    Log.d("size",list.size()+"   "+s2);
                    ArrayAdapter_For_Question_List adapter = new ArrayAdapter_For_Question_List(QuestionListActivity.this,
                            R.layout.questions_layout, list);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                 //   Toast.makeText(QuestionListActivity.this, words.get(words.size() - 1).getSpecialityName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.question_list, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

    /*    String s=item.getTitle().toString();
        ArrayList<StoredDatabase> list=new ArrayList<>();
        for(int i=0;i<wordsss.size();i++)
        {
           if(wordsss.get(i).getUid().equals(s))
           {
               assert list != null;
               list.add(wordsss.get(i));
           }
        }

       // Toast.makeText(QuestionListActivity.this,,Toast.LENGTH_SHORT).show();

        ArrayAdapter_For_Question_List adapter = new ArrayAdapter_For_Question_List(QuestionListActivity.this,
                R.layout.questions_layout, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
        ArrayList<StoredDatabase> list=new ArrayList<>();
        for(int i=0;i<wordsss.size();i++)
        {
        //    Log.d("size", wordsss.get(i).getUid()+"   "+s2);
            if(wordsss.get(i).getUid().startsWith("c"))
            {
                list.add(wordsss.get(i));
            }
        }

        // Toast.makeText(QuestionListActivity.this,,Toast.LENGTH_SHORT).show();
        ArrayAdapter_For_Question_List adapter = new ArrayAdapter_For_Question_List(QuestionListActivity.this,
                R.layout.questions_layout, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void addMenuItemInNavMenuDrawer() {
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        SharedPreferences preferences = QuestionListActivity.this.getSharedPreferences("user_inf", 0);
        String s = preferences.getString("s", null);

        String s1[]=s.split("/");
        Menu menu = navView.getMenu();
        Menu submenu = menu.addSubMenu("__Questions Tag__");

        submenu.add(s1[0]);
        submenu.add(s1[1]);
        submenu.add(s1[2]);
        submenu.add(s1[3]);

        navView.invalidate();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        // TextView textView = (TextView) view.findViewById(R.id.clgName);
        //   String text = textView.getText().toString();
        Intent intent =new Intent(this,Webview.class);
         intent.putExtra("url",wordsss.get(position).getSpecialityQuestionLink());
         Log.d("qwas",wordsss.get(position).getSpecialityQuestionLink());
        startActivity(intent);

    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
