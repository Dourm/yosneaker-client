package com.rainy.yosneaker.client.activity;

import com.rainy.yosneaker.client.R;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class AbstractActivity extends Activity {
	protected abstract void initEvents();

	protected abstract void initViews();

	protected ImageView mBackImg;

	protected TextView mTitleTv;

	protected ImageView mOkImg;

	protected void initBar(String barName) {
		// ≥ı ºªØbar
		mBackImg = (ImageView) findViewById(R.id.back_img);
		mBackImg.setVisibility(View.VISIBLE);
		mTitleTv = (TextView) findViewById(R.id.title_tv);
		mTitleTv.setText(barName);
		mOkImg = (ImageView) findViewById(R.id.right_img);
		mOkImg.setImageResource(R.drawable.check_mark_btn);
		mOkImg.setVisibility(View.VISIBLE);
	}
}
