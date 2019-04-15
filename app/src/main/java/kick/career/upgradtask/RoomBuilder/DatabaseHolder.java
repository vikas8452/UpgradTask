package kick.career.upgradtask.RoomBuilder;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;


@Database(entities = {StoredDatabase.class}, version = 1, exportSchema = false)
    public abstract class DatabaseHolder extends RoomDatabase {

        private static DatabaseHolder INSTANCE;
        public abstract DaoForHashMap userDao();
        public static DatabaseHolder getAppDatabase(Context context) {
            if (INSTANCE == null) {

                Log.d("qqqq","Pros");
                synchronized (DatabaseHolder.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseHolder.class, "user_database")
                                        // allow queries on the main thread.
                                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                                        .allowMainThreadQueries()
                                        .build();
                    }
                }
            }
            return INSTANCE;
        }

        public static void destroyInstance() {
            INSTANCE = null;
        }

    private static RoomDatabase.Callback sRoomDatabaseCallback=
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    Log.d("qqqq","Process");
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    }
