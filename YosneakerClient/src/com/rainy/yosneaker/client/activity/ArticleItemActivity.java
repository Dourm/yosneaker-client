package com.rainy.yosneaker.client.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rainy.yosneaker.client.R;
import com.rainy.yosneaker.client.utils.CreateBmpFactory;

public class ArticleItemActivity extends Activity implements OnClickListener {
	/** ���磬���ڶ�̬��ʾ���ɾ��ͼƬ */
	private GridView gv;
	/** ������ */
	private MyGridViewAdapter adapter;
	/** �����б� */
	private List<Bitmap> viewList;
	/** ��������� */
	private LayoutInflater inflater;
	/** ���͸���Ĳ��֣������ṩ��ᣬ������Ĳ���ѡ� */
	private LinearLayout sendmoreLyt;
	/** BMP���칤�������ڻ������ͼ�������������������ɵ�ͼƬ�������Լ��� */
	private CreateBmpFactory mCreateBmpFactory;
	
	private ImageView mBackImg;
	
	private TextView mTitleTv;
	
	private ImageView mOkImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_article_item_add_gv_main);
		// ͼ�������BMPҵ��
		sendmoreLyt = (LinearLayout) this.findViewById(R.id.layout_sendmore);
		this.findViewById(R.id.sendCamera).setOnClickListener(this);
		this.findViewById(R.id.sendPic).setOnClickListener(this);
		mCreateBmpFactory = new CreateBmpFactory(this);
		
		//��ʼ��bar
		mBackImg = (ImageView) findViewById(R.id.back_img);
		mBackImg.setVisibility(View.VISIBLE);
		mTitleTv = (TextView) findViewById(R.id.title_tv);
		mTitleTv.setText("������");
		mOkImg = (ImageView) findViewById(R.id.right_img);
		mOkImg.setImageResource(R.drawable.check_mark_btn);
		mOkImg.setVisibility(View.VISIBLE);
		
		initEvents();
		// ��ɾͼƬ����ҵ��
		gv = (GridView) this.findViewById(R.id.gridView);
		viewList = new ArrayList<Bitmap>();
		viewList.add(null);
		adapter = new MyGridViewAdapter();
		gv.setAdapter(adapter);
		super.onCreate(savedInstanceState);
	}
	
	private void initEvents() {
		mBackImg.setOnClickListener(this);
	}
	public class MyGridViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (viewList == null) {
				return 0;
			}
			return viewList.size();
		}

		@Override
		public Object getItem(int position) {
			return viewList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			inflater = LayoutInflater.from(ArticleItemActivity.this);
			if (position == viewList.size() - 1) {
				View addView = inflater.inflate(
						R.layout.activity_article_item_add_gv_item_add, null);
				addView.findViewById(R.id.add).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								if (viewList.size() == 4) {
									Toast.makeText(getApplicationContext(),
											"�Ѵﵽ���ͼƬ����", 0).show();
									return;
								} else {
									sendmoreLyt.setVisibility(View.VISIBLE);
								}
							}
						});
				return addView;
			} else {
				View picView = inflater.inflate(
						R.layout.activity_article_item_add_gv_item_pic, null);
				ImageButton picIBtn = (ImageButton) picView
						.findViewById(R.id.pic);
				picIBtn.setImageBitmap(viewList.get(position));
				picIBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new Builder(
								ArticleItemActivity.this);
						builder.setTitle("�鿴��ͼ");
						View view = inflater.inflate(
								R.layout.activity_article_item_add_dlg_view,
								null);
						((ImageView) view.findViewById(R.id.bigPic))
								.setImageBitmap(viewList.get(position));
						builder.setView(view);
						builder.setNegativeButton("����", null);
						builder.show();
					}
				});
				picView.findViewById(R.id.delete).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								viewList.remove(position);
								adapter.notifyDataSetChanged();
							}
						});
				return picView;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sendCamera:
			mCreateBmpFactory.OpenCamera();
			sendmoreLyt.setVisibility(View.GONE);
			break;

		default:
			mCreateBmpFactory.OpenGallery();
			sendmoreLyt.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String picPath = mCreateBmpFactory.getBitmapFilePath(requestCode,
				resultCode, data);
		Bitmap bmp = mCreateBmpFactory.getBitmapByOpt(picPath);
		if (bmp != null) {
			viewList.add(0, bmp);
			adapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
