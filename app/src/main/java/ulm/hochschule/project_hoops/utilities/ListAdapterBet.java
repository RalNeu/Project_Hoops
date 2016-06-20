package ulm.hochschule.project_hoops.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.ArrayList;

import ulm.hochschule.project_hoops.R;

/**
 * Created by Johann on 20.06.2016.
 */
public class ListAdapterBet extends CustomListAdapter {

    private Context context;
    private View layout;

    public ListAdapterBet(Context context, ArrayList list){
        super(context, list);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layout = LayoutInflater.from(context).inflate(R.layout.list, null);
        return layout;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
