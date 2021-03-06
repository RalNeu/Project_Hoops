package ulm.hochschule.project_hoops.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.NotifyManager;

/**
 * Created by Teddy on 18.05.2016.
 */
public class TestTab extends Fragment {

    Button btn_Test;
    EditText et_Test;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tests, container, false);

        btn_Test = (Button) layout.findViewById(R.id.btnTests);
        et_Test = (EditText) layout.findViewById(R.id.editText);
        return layout;
    }

    NotifyManager m = new NotifyManager();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m.sendNotify(1, "Hallo", "nein", getActivity(), R.drawable.ic_ticket_icon);
             /*   SqlManager sqlManager = SqlManager.getInstance();
                if(et_Test.getText().toString().isEmpty()){
                    System.out.println("Empty");
                }else
                    System.out.println("not Empty");
                XMLReader xml = new XMLReader();*/


                m.sendNotify(1, "Hallo", "nein", getActivity(), R.drawable.ic_ticket_icon);


            }
        });
    }

}
