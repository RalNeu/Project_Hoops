package ulm.hochschule.project_hoops.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.ListAdapterBet;
import ulm.hochschule.project_hoops.utilities.MyBet;

/**
 * Created by Johann on 20.06.2016.
 */
public class ListViewBet extends Activity {

    private ArrayAdapter<MyBet> adapter;
    private ListView listView;
    private List<MyBet> list;
    private ImageView iv_bbu;
    private ImageView iv_opponent;
    private TextView tv_result;
    private TextView tv_rightBet;
    private TextView tv_myBet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tippspiel_listview);

        init();
        update(new MyBet());
    }

    private void init(){
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<MyBet>(this , R.layout.listitem_mybet, R.id.tv_yourBetResult, list);
        iv_bbu = (ImageView) findViewById(R.id.Iv_bbu);
        iv_opponent = (ImageView) findViewById(R.id.Iv_opponent);
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_rightBet = (TextView) findViewById(R.id.tv_rightBet);
        tv_myBet = (TextView) findViewById(R.id.tv_yourBetResult);

        listView.setAdapter(adapter);
    }

    public void update(MyBet myBet){
        list.add(myBet);
        adapter.notifyDataSetChanged();
    }
}

