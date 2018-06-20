package com.soundPlayer;

import java.util.ArrayList;

public class YoutubeSearchResults {
	private int searchCount;
	private String name;
	private String fullSearchResult;
	
	
	public int getSearchCount() {
		return searchCount;
	}


	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFullSearchResult() {
		return fullSearchResult;
	}


	public void setFullSearchResult(String fullSearchResult) {
		this.fullSearchResult = fullSearchResult;
	}


	@Override
	public String toString() {
		return "YoutubeSearchResults [searchCount=" + searchCount + ", name=" + name + ", fullSearchResult="
				+ fullSearchResult + "]";
	}


	
	
}
