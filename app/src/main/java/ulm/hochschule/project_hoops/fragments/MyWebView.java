package ulm.hochschule.project_hoops.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import ulm.hochschule.project_hoops.R;

/**
 * Created by Johann on 06.05.2016.
 */
public class MyWebView extends Fragment {

    private android.webkit.WebView wv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.web_view, container, false);
        wv = (android.webkit.WebView) v.findViewById(R.id.web_view);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.loadUrl("http://www.ratiopharmulm.com/news-archiv/1616-entscheidung-in-letzter-sekunde-vertagt");
    }
}
