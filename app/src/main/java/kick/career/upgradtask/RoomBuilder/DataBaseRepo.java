package kick.career.upgradtask.RoomBuilder;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class DataBaseRepo {
    private DaoForHashMap mWordDao;
    private LiveData<List<StoredDatabase>> mAllWords;


    DataBaseRepo(Application application) {
        DatabaseHolder db = DatabaseHolder.getAppDatabase(application);
        mWordDao = db.userDao();
        mAllWords =  mWordDao.getAll();
    }

    LiveData<List<StoredDatabase>> getAllWords() {
        return mAllWords;
    }

    public void insert (StoredDatabase word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    private static  class insertAsyncTask extends AsyncTask<StoredDatabase, Void, Void> {

        private DaoForHashMap mAsyncTaskDao;

        insertAsyncTask(DaoForHashMap dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final StoredDatabase... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


}
