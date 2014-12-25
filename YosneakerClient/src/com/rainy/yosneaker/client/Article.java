package com.rainy.yosneaker.client;

public class Article {
	private String mTitle;
	private int reader;
	private String date;
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Article(String title, int reader, String date) {
		super();
		mTitle = title;
		this.reader = reader;
		this.date = date;
	}

	public String getTitle() {
		return mTitle;
	}

	public Article(String title, int reader) {
		super();
		mTitle = title;
		this.reader = reader;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public int getReader() {
		return reader;
	}

	public void setReader(int reader) {
		this.reader = reader;
	}
}
