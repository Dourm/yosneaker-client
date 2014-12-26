package com.rainy.yosneaker.client.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rainy.yosneaker.client.R;
import com.rainy.yosneaker.client.utils.CommonUtils;
import com.rainy.yosneaker.client.view.MyScrollView;
import com.rainy.yosneaker.client.view.MyScrollView.OnScrollListener;

/**
 * 详情页面
 * 
 * @author rainy
 *
 */
public class ArticleDetailActivity extends Activity implements OnClickListener,
		OnScrollListener {

	private TextView mTitleTv;
	private ImageView mBackImg;
	private EditText mContentEdit;

	/**
	 * 自定义的MyScrollView
	 */
	private MyScrollView myScrollView;
	/**
	 * 在MyScrollView里面的购买布局
	 */
	private LinearLayout mBuyLayout;
	/**
	 * 位于顶部的购买布局
	 */
	private LinearLayout mTopBuyLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_article_detail);

		init();
		initEvents();
	}

	private void init() {
		mTitleTv = (TextView) findViewById(R.id.title_tv);
		mTitleTv.setText(R.string.article_detail);
		mBackImg = (ImageView) findViewById(R.id.back_img);
		mBackImg.setVisibility(View.VISIBLE);
		mContentEdit = (EditText) findViewById(R.id.content_edit);

		myScrollView = (MyScrollView) findViewById(R.id.scrollView);
		mBuyLayout = (LinearLayout) findViewById(R.id.buy);
		mTopBuyLayout = (LinearLayout) findViewById(R.id.top_buy_layout);

		myScrollView.setOnScrollListener(this);

		// 当布局的状态或者控件的可见性发生改变回调的接口
		findViewById(R.id.activity_article_detail_layout).getViewTreeObserver()
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// 这一步很重要，使得上面的购买布局和下面的购买布局重合
						onScroll(myScrollView.getScrollY());

						System.out.println(myScrollView.getScrollY());
					}
				});
	}

	private void initEvents() {
		mBackImg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_img:
			// 返回按钮
			this.finish();
			break;
		/*
		 * case R.id.right_img: // 标题栏的确定按钮 CommonUtils.startShakeAnim(this,
		 * mContentEdit); break;
		 */

		default:
			break;
		}
	}

	@Override
	public void onScroll(int scrollY) {
		int mBuyLayout2ParentTop = Math.max(scrollY, mBuyLayout.getTop());
		mTopBuyLayout.layout(0, mBuyLayout2ParentTop, mTopBuyLayout.getWidth(),
				mBuyLayout2ParentTop + mTopBuyLayout.getHeight());
	}

}
