package kick.career.upgradtask.RoomBuilder;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class DataViewModel extends AndroidViewModel {

    private DataBaseRepo mRepository;

    private LiveData<List<StoredDatabase>> mAllWords;

    public DataViewModel (Application application) {
        super(application);
        mRepository = new DataBaseRepo(application);
        mAllWords = mRepository.getAllWords();
    }

  public LiveData<List<StoredDatabase>> getAllWords() { return mAllWords; }

    public void insert(StoredDatabase word) { mRepository.insert(word); }
}
