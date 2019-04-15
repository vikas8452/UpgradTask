package kick.career.upgradtask.RoomBuilder;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public class StoredDatabase {
    @NonNull
    @PrimaryKey
    private String uid;

    @ColumnInfo(name = "speciality_name")
    private String specialityName;

    @ColumnInfo(name = "speciality_question_link")
    private String specialityQuestionLink;



    public StoredDatabase() {
    }

    public String getSpecialityQuestionLink() {
        return specialityQuestionLink;
    }

    public void setSpecialityQuestionLink(String specialityQuestionLink) {
        this.specialityQuestionLink = specialityQuestionLink;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }
}
