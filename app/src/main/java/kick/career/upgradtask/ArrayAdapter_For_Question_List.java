package kick.career.upgradtask;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kick.career.upgradtask.RoomBuilder.StoredDatabase;

public class ArrayAdapter_For_Question_List extends ArrayAdapter<StoredDatabase> {

    Context context;

    public ArrayAdapter_For_Question_List(Context context, int resource, List<StoredDatabase> objects) {
        super(context, resource, objects);
        this.context=context;
    }
    private class ViewHolder {
        TextView paperNum;

    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        StoredDatabase rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            assert mInflater != null;
            convertView = mInflater.inflate(R.layout.questions_layout, null);
            holder = new ViewHolder();
            holder.paperNum = (TextView) convertView.findViewById(R.id.paperyear);


            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        assert rowItem != null;
        holder.paperNum.setText(rowItem.getSpecialityName());
        return convertView;
    }

}
