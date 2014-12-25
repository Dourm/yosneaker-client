package com.rainy.yosneaker.client.fragment;
import java.io.File;
import java.util.ArrayList;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.rainy.yosneaker.client.Article;
import com.rainy.yosneaker.client.R;
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
	private ImageLoader mImageLoader;
	
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
		mImageLoader = initImageLoader(getActivity().getApplicationContext(), mImageLoader, "test");
		mAdapter = new ArticleAdapter(items);
		xListView.setAdapter(mAdapter);
		xListView.setXListViewListener(this);
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
			items.add(new Article("ӣ��AJ ����"+(++start),(++start),"�������ǰ"));
		}
	}

	private void onLoad() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime("�ո�");
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
/*            RoundImageView networkImage = (RoundImageView)convertView.findViewById(R.id.roundImage_network);
            mImageLoader
				.displayImage("http://c.hiphotos.baidu.com/image/w%3D2048/sign=744a86ae0d3387449cc5287c6537d8f9/ac345982b2b7d0a28e9adc63caef76094a369af9.jpg",
						networkImage);*/
            return convertView;
        }
    }
    
    /**
	 * ��ʼ��ͼƬ��������ͼƬ�����ַ<i>("/Android/data/[app_package_name]/cache/dirName")</i>
	 */
	public ImageLoader initImageLoader(Context context,
			ImageLoader imageLoader, String dirName) {
		imageLoader = ImageLoader.getInstance();
		if (imageLoader.isInited()) {
			// ���³�ʼ��ImageLoaderʱ,��Ҫ�ͷ���Դ.
			imageLoader.destroy();
		}
		imageLoader.init(initImageLoaderConfig(context, dirName));
		return imageLoader;
	}

	/**
	 * ����ͼƬ������
	 * 
	 * @param dirName
	 *            �ļ���
	 */
	private ImageLoaderConfiguration initImageLoaderConfig(
			Context context, String dirName) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(3).memoryCacheSize(getMemoryCacheSize(context))
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.discCache(new UnlimitedDiscCache(new File(dirName)))
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		return config;
	}

	private int getMemoryCacheSize(Context context) {
		int memoryCacheSize;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			int memClass = ((ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE))
					.getMemoryClass();
			memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
															// limit
		} else {
			memoryCacheSize = 2 * 1024 * 1024;
		}
		return memoryCacheSize;
	}
}
