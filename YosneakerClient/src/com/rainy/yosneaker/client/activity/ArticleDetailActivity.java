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
 * ����ҳ��
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
	 * �Զ����MyScrollView
	 */
	private MyScrollView myScrollView;
	/**
	 * ��MyScrollView����Ĺ��򲼾�
	 */
	private LinearLayout mBuyLayout;
	/**
	 * λ�ڶ����Ĺ��򲼾�
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

		// �����ֵ�״̬���߿ؼ��Ŀɼ��Է����ı�ص��Ľӿ�
		findViewById(R.id.activity_article_detail_layout).getViewTreeObserver()
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// ��һ������Ҫ��ʹ������Ĺ��򲼾ֺ�����Ĺ��򲼾��غ�
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
			// ���ذ�ť
			this.finish();
			break;
		/*
		 * case R.id.right_img: // ��������ȷ����ť CommonUtils.startShakeAnim(this,
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
