package com.rainy.yosneaker.client.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.rainy.yosneaker.client.R;
import com.rainy.yosneaker.client.utils.CreateBmpFactory;

public class ArticleItemActivity extends Activity implements OnClickListener {
	/** 网络，用于动态显示添加删除图片 */
	private GridView gv;
	/** 适配器 */
	private MyGridViewAdapter adapter;
	/** 数据列表 */
	private List<Bitmap> viewList;
	/** 布局填充器 */
	private LayoutInflater inflater;
	/** 发送更多的布局，用于提供相册，照相机的操作选项。 */
	private LinearLayout sendmoreLyt;
	
	/** BMP制造工厂，用于获得来自图库或者照相机拍照所生成的图片。并可以剪切 */
	private CreateBmpFactory mCreateBmpFactory;
	
	private ImageView mBackImg;
	
	private TextView mTitleTv;
	
	private ImageView mOkImg;
	
	//测评项标题
	private EditText mItemTitle;
	
	private PopupWindow pop = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_article_item_add_gv_main);
		// 图库照相机BMP业务
		sendmoreLyt = (LinearLayout) this.findViewById(R.id.layout_sendmore);
		// 常用的测评项标题
		//commondTitleLyt = (LinearLayout) this.findViewById(R.id.layout_commond_title);
		
		mItemTitle = (EditText) this.findViewById(R.id.editItemContent);
		
		pop = new PopupWindow(ArticleItemActivity.this);
		
		View view = getLayoutInflater().inflate(R.layout.activity_article_item_add_commond_title, null);
		
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		pop.setBackgroundDrawable(new BitmapDrawable());
		//pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		
		view.findViewById(R.id.item_title_aspect).setOnClickListener(this);
		view.findViewById(R.id.item_title_protective).setOnClickListener(this);
		view.findViewById(R.id.item_title_weight).setOnClickListener(this);
		view.findViewById(R.id.item_title_holding).setOnClickListener(this);
		view.findViewById(R.id.item_title_comfort).setOnClickListener(this);
		view.findViewById(R.id.item_title_technology).setOnClickListener(this);
		view.findViewById(R.id.item_title_wear).setOnClickListener(this);
		view.findViewById(R.id.item_title_cost_performance).setOnClickListener(this);
		view.findViewById(R.id.item_title_xxx).setOnClickListener(this);
		
		this.findViewById(R.id.sendCamera).setOnClickListener(this);
		this.findViewById(R.id.sendPic).setOnClickListener(this);
		mCreateBmpFactory = new CreateBmpFactory(this);
		
		//初始化bar
		mBackImg = (ImageView) findViewById(R.id.back_img);
		mBackImg.setVisibility(View.VISIBLE);
		mTitleTv = (TextView) findViewById(R.id.title_tv);
		mTitleTv.setText("测评项");
		mOkImg = (ImageView) findViewById(R.id.right_img);
		mOkImg.setImageResource(R.drawable.check_mark_btn);
		mOkImg.setVisibility(View.VISIBLE);
		
		initEvents();
		// 增删图片网格业务
		gv = (GridView) this.findViewById(R.id.gridView);
		viewList = new ArrayList<Bitmap>();
		viewList.add(null);
		adapter = new MyGridViewAdapter();
		gv.setAdapter(adapter);
		super.onCreate(savedInstanceState);
	}
	
	private void initEvents() {
		mBackImg.setOnClickListener(this);
		
		mItemTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {   
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){//如果组件获得焦点
                	//commondTitleLyt.setVisibility(View.VISIBLE);
                	System.out.println("======"+pop);
                	pop.showAtLocation(mItemTitle, Gravity.BOTTOM, 0, 0);
                }else{
                	//commondTitleLyt.setVisibility(View.GONE);
                	pop.dismiss();
                }
            }
        });
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
											"已达到最多图片数量", 0).show();
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
						builder.setTitle("查看大图");
						View view = inflater.inflate(
								R.layout.activity_article_item_add_dlg_view,
								null);
						((ImageView) view.findViewById(R.id.bigPic))
								.setImageBitmap(viewList.get(position));
						builder.setView(view);
						builder.setNegativeButton("返回", null);
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
		case R.id.item_title_aspect:
			mItemTitle.setText(((Button)pop.getContentView().findViewById(R.id.item_title_aspect)).getText());
			break;
		case R.id.item_title_protective:
			mItemTitle.setText(((Button)pop.getContentView().findViewById(R.id.item_title_protective)).getText());
			break;
		case R.id.item_title_weight:
			mItemTitle.setText(((Button)pop.getContentView().findViewById(R.id.item_title_aspect)).getText());
			break;
		case R.id.item_title_holding:
			mItemTitle.setText(((Button)pop.getContentView().findViewById(R.id.item_title_holding)).getText());
			break;
		case R.id.item_title_comfort:
			mItemTitle.setText(((Button)pop.getContentView().findViewById(R.id.item_title_comfort)).getText());
			break;
		case R.id.item_title_technology:
			mItemTitle.setText(((Button)pop.getContentView().findViewById(R.id.item_title_technology)).getText());
			break;
		case R.id.item_title_wear:
			mItemTitle.setText(((Button)pop.getContentView().findViewById(R.id.item_title_wear)).getText());
			break;
		case R.id.item_title_cost_performance:
			mItemTitle.setText(((Button)pop.getContentView().findViewById(R.id.item_title_cost_performance)).getText());
			break;
		case R.id.item_title_xxx:
			mItemTitle.setText(((Button)pop.getContentView().findViewById(R.id.item_title_xxx)).getText());
			break;
		default:
			mCreateBmpFactory.OpenGallery();
			sendmoreLyt.setVisibility(View.GONE);
			pop.dismiss();
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
