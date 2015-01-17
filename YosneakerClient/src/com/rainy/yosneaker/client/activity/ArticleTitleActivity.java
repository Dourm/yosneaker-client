package com.rainy.yosneaker.client.activity;

import com.rainy.yosneaker.client.R;
import com.rainy.yosneaker.client.utils.CommonUtils;

import android.os.Bundle;
import android.view.View;

public class ArticleTitleActivity extends AbstractActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_article_title_add);
		initBar("≤‚∆¿±ÍÃ‚");
		initEvents();
	}
	
	@Override
	protected void initEvents() {
		mOkImg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonUtils.launchActivity(ArticleTitleActivity.this,ArticleItemActivity.class);
			}
		});
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub

	}

}
