package com.rainy.yosneaker.client.fragment;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rainy.yosneaker.client.Article;
import com.rainy.yosneaker.client.R;
import com.rainy.yosneaker.client.activity.ArticleDetailActivity;
import com.rainy.yosneaker.client.utils.CommonUtils;
import com.rainy.yosneaker.client.view.XListView;
import com.rainy.yosneaker.client.view.XListView.IXListViewListener;


public class FirstFragment extends Fragment implements IXListViewListener{

	private View viewFragment;
	private XListView xListView=null;
	private ArticleAdapter mAdapter;
	ArrayList<Article> items = new ArrayList<Article>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		viewFragment=inflater.inflate(R.layout.fragment_first, null);
		geneItems();
		initViews();
		return viewFragment;
	}
	
	private void initViews(){
		xListView=(XListView) viewFragment.findViewById(R.id.xListView);
		xListView.setPullLoadEnable(true);
/*		for(int i=0;i<100;i++){
			items.add(new Article("hello"+i,i));
		}*/
		mAdapter = new ArticleAdapter(items);
		xListView.setAdapter(mAdapter);
		xListView.setXListViewListener(this);
		xListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CommonUtils.launchActivity(getActivity(), ArticleDetailActivity.class);
			}
			
		});
		mHandler = new Handler();
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				items.clear();
				geneItems();
				mAdapter = new ArticleAdapter(items);
				xListView.setAdapter(mAdapter);
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
	
	private void geneItems() {
		for (int i = 0; i != 5; ++i) {
			items.add(new Article("樱花AJ 测评"+(++start),(++start),"五分钟以前"));
		}
	}

	private void onLoad() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime("刚刚");
	}
	
	
    private class ArticleAdapter extends ArrayAdapter<Article> {
        public ArticleAdapter(ArrayList<Article> Articles) {
            super(getActivity(), android.R.layout.simple_list_item_1, Articles);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
        	Log.i("==========",""+convertView);
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.xlistview_item, null);
            }

            // configure the view for this Article
            Article c = getItem(position);

            TextView titleTextView =
                (TextView)convertView.findViewById(R.id.title);
            titleTextView.setText(c.getTitle());
            TextView reader =
                    (TextView)convertView.findViewById(R.id.r_count);
            	reader.setText(""+c.getReader());
            	TextView date =
            			(TextView)convertView.findViewById(R.id.date);
            	date.setText(""+c.getDate());
            return convertView;
        }
    }
}
