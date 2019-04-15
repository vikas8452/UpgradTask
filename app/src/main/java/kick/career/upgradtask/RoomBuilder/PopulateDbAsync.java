package kick.career.upgradtask.RoomBuilder;

import android.os.AsyncTask;
import android.util.Log;

public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final DaoForHashMap mDao;

    PopulateDbAsync(DatabaseHolder db) {
        mDao = db.userDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {

        mDao.nukeDatabase();
        for(int i=0;i<3;i++) {
            StoredDatabase word = new StoredDatabase();
            word.setUid(i+"");
            word.setSpecialityName("lkllkkl");
            word.setSpecialityQuestionLink("djsshdjhs");

            mDao.insert(word);
        }
        return null;
    }
}