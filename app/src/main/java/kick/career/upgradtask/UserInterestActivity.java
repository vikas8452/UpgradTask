package kick.career.upgradtask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.okdroid.checkablechipview.CheckableChipView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kick.career.upgradtask.RoomBuilder.DaoForHashMap;
import kick.career.upgradtask.RoomBuilder.DatabaseHolder;
import kick.career.upgradtask.RoomBuilder.StoredDatabase;

public class UserInterestActivity extends AppCompatActivity {

    private static final String PROFILE_URL = "https://api.linkedin.com/v1/people/~";
    private static final String OAUTH_ACCESS_TOKEN_PARAM = "oauth2_access_token";
    private static final String QUESTION_MARK = "?";
    private static final String EQUALS = "=";

    private TextView welcomeText;
    private TextView msg;
    private ProgressDialog pd;

    String selected="";

    static ProgressDialog loading;
    CheckableChipView chip1;
    CheckableChipView chip2;
    CheckableChipView chip3;
    CheckableChipView chip4;
    CheckableChipView chip5;
    CheckableChipView chip6;
    CheckableChipView chip7;





    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interest);

        initialiseChip();

        SharedPreferences preferences = UserInterestActivity.this.getSharedPreferences("user_inf", 0);
        String s = preferences.getString("s", null);
        if(s!=null)
        {
            Intent startProfileActivity = new Intent(UserInterestActivity.this, QuestionListActivity.class);
            UserInterestActivity.this.startActivity(startProfileActivity);
            finish();
        }
        submit = findViewById(R.id.submit);
        msg = findViewById(R.id.msg);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = 0;
                String s = "";
                if (chip1.isChecked()) {
                    s += chip1.getText() + "/";
                    i++;
                }
                if (chip2.isChecked()) {
                    s += chip2.getText() + "/";
                    i++;
                }
                if (chip3.isChecked()) {
                    s += chip3.getText() + "/";
                    i++;
                }
                if (chip4.isChecked()) {
                    s += chip4.getText() + "/";
                    i++;
                }
                if (chip5.isChecked()) {
                    s += chip5.getText() + "/";
                    i++;
                }
                if (chip6.isChecked()) {
                    s += chip6.getText() + "/";
                    i++;
                }
                if (chip7.isChecked()) {
                    s += chip7.getText() + "/";
                    i++;
                }


                if (!(i == 4)) {
                    msg.setText("* Please select any four !");
                    msg.setVisibility(View.VISIBLE);
                } else {
                    msg.setVisibility(View.INVISIBLE);
                   // JSONObject json = null;
                         //readJsonFromUrl("https://api.stackexchange.com/2.2/questions?tagged=c;java&site=stackoverflow");


                    SharedPreferences preferences = UserInterestActivity.this.getSharedPreferences("user_inf", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("s",s);
                    editor.commit();



                    String s1[]=s.split("/");
               DatabaseHolder.getAppDatabase(UserInterestActivity.this).userDao().nukeDatabase();


                        new GetProfileRequestAsyncTask().execute("https://api.stackexchange.com/2.2/questions?tagged="+s1[0]+";"+s1[1]+"&site=stackoverflow");



                    Intent startProfileActivity = new Intent(UserInterestActivity.this, QuestionListActivity.class);
                    startActivity(startProfileActivity);
                    finish();

                   // System.out
                           // .println(jsonGetRequest("https://api.stackexchange.com/2.2/questions?tagged=c;java&site=stackoverflow"));

                }
            }

        });
    /*    welcomeText = (TextView) findViewById(R.id.activity_profile_welcome_text);

        //Request basic profile of the user
        SharedPreferences preferences = this.getSharedPreferences("user_info", 0);
        String accessToken = preferences.getString("accessToken", null);
        if(accessToken!=null){
            String profileUrl = getProfileUrl(accessToken);
           // new GetProfileRequestAsyncTask().execute(profileUrl);
        }*/


    }


    private class GetProfileRequestAsyncTask extends AsyncTask<String, Void, JsonObject> {

        @Override
        protected void onPreExecute() {
        //    pd = ProgressDialog.show(UserInterestActivity.this, "", "Loading...", true);
        }

        @Override
        protected JsonObject doInBackground(String... urls) {

            JsonObject rootobj = null;
            if (urls.length > 0) {
                String url1 = urls[0];
                String sURL = url1; //just a string

                // Connect to the URL using java's native library
                URL url = null;
                try {
                    url = new URL(sURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                URLConnection request = null;
                try {
                    request = url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    request.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Convert to a JSON object to print data

                JsonParser jp = new JsonParser(); //from gson
                JsonElement root = null; //Convert the input stream to a json element
                try {
                    root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                rootobj = root.getAsJsonObject(); //May be an array, may be an object.
                }
            Log.d("qwwqw",rootobj.toString());

            return rootobj;
        }

        @Override
        protected void onPostExecute(JsonObject data) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            if (data != null) {
                Log.e("data", data.toString());
                final DaoForHashMap mDao= DatabaseHolder.getAppDatabase(UserInterestActivity.this).userDao();
        mDao.nukeDatabase();
                JsonArray jsonArray = (JsonArray) data.get("items");
                int tag=0;

                for(int i = 0; i < jsonArray.size(); i++){

                    tag++;


                    JsonObject obj = (JsonObject) jsonArray.get(i);
                    String link = obj.get("link").toString();
                    String title = obj.get("title").toString();
                    String questionId = obj.get("question_id").toString();

                    Log.e("linktitle",link+ " "+ title);

                    StoredDatabase word = new StoredDatabase();
                    word.setUid("c"+questionId);
                    word.setSpecialityName(title);
                    word.setSpecialityQuestionLink(link);
                    mDao.insert(word);


                }


            }
        }


    }

    ;

    private void initialiseChip() {
        chip1 = findViewById(R.id.chip);
        chip2 = findViewById(R.id.chip1);
        chip3 = findViewById(R.id.chip2);
        chip4 = findViewById(R.id.chip3);
        chip5 = findViewById(R.id.chip4);
        chip6 = findViewById(R.id.chip5);
        chip7 = findViewById(R.id.chip6);
    }





}


