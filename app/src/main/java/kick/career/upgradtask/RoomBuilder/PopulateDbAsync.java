package kick.career.upgradtask.RoomBuilder;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import kick.career.upgradtask.QuestionListActivity;
import kick.career.upgradtask.UserInterestActivity;

public class PopulateDbAsync extends AsyncTask<Void, Void, JsonObject> {

    private final DaoForHashMap mDao;

    PopulateDbAsync(DatabaseHolder db) {
        mDao = db.userDao();
    }


    @Override
    protected JsonObject doInBackground(Void... voids) {

        JsonObject rootobj = null;


            String sURL = "https://api.stackexchange.com/2.2/questions?tagged=c&site=stackoverflow"; //just a string

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

        Log.d("qwwqw",rootobj.toString());

        return rootobj;

    }

    @Override
        protected void onPreExecute() {
            //    pd = ProgressDialog.show(UserInterestActivity.this, "", "Loading...", true);
        }


        @Override
        protected void onPostExecute(JsonObject data) {

            if (data != null) {
                Log.e("data", data.toString());



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

                //Intent startProfileActivity = new Intent(UserInterestActivity.this, QuestionListActivity.class);
                //startActivity(startProfileActivity);
                //finish();

            }
        }





}