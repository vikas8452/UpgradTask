package kick.career.upgradtask.RoomBuilder;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DaoForHashMap {

    @Query("SELECT * FROM user")
    LiveData<List<StoredDatabase>> getAll();

    @Query("SELECT * FROM user where uid LIKE  :id")
    StoredDatabase findByName(String id);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insert(StoredDatabase users);

    @Query("DELETE FROM user")
    void nukeDatabase();
}
