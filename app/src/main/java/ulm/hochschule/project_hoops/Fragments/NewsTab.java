package ulm.hochschule.project_hoops.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import ulm.hochschule.project_hoops.utilities.ListItem;
import ulm.hochschule.project_hoops.R;


/**
 * Created by Johann on 06.05.2016.
 */
public class NewsTab extends Fragment {

    private ListView listView;
    private ImageView imageView;
    private String[] headline;
    private int[] images;

    private SwipeRefreshLayout swipeRefresh;

    Button load_img;
    Bitmap bitmap;
    ProgressDialog pDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tippspiel_listview, container, false);
        listView = (ListView) layout.findViewById(R.id.listView);
        //swipeRefresh = (SwipeRefreshLayout) layout.findViewById(R.id.swiperefresh);
       // imageView = (ImageView) layout.findViewById(R.id.img);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Created by Johann
                //TODO: Load Data from Server
            }
        });
        */

       // ArrayList<ListItem> listData = getListData();

        //listView.setAdapter(new CustomListAdapter(getContext(), listData));

       /*new LoadImage().execute("http://www.ratiopharmulm.com/images/stories/Hauptnews/2016_05/2016_05_14_OLDvsULM_02.jpg");

        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<10;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", "wuhu");

            //get the image from Website
            hm.put("img", Integer.toString(R.mipmap.ic_launcher));
            aList.add(hm);
        }

        String[] from = new String[]{"img", "txt"};
        int[] to = new int[]{R.id.img, R.id.txt};
        SimpleAdapter adapter = new SimpleAdapter(getContext(), aList, R.layout.fragment_news, from, to);

        listView.setAdapter(adapter);
        */

    }

    private ArrayList<ListItem> getListData() {
        /*ArrayList<ListItem> listMockData = new ArrayList<ListItem>();
        String[] images = getResources().getStringArray(R.array.images_array);
        String[] headlines = getResources().getStringArray(R.array.headline_array);

        for (int i = 0; i < images.length; i++) {
            ListItem newsData = new ListItem();
            newsData.setUrl(images[i]);
            newsData.setHeadline(headlines[i]);
            newsData.setReporterName("Pankaj Gupta");
            newsData.setDate("May 26, 2013, 13:35");
            listMockData.add(newsData);
        }
        return listMockData;*/
        return null;
    }


    /*
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                imageView.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(getActivity(), "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }
    */
}

