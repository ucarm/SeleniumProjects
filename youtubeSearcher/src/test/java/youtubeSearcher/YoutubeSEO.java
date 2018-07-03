package youtubeSearcher;

public class YoutubeSEO {
	private String name;
	private int searchCount;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
	public YoutubeSEO(String name, int searchCount) {
		this.name = name;
		this.searchCount = searchCount;
	}
	@Override
	public String toString() {
		return "[name=" + name + ", searchCount=" + searchCount + "]";
	}
	
	
}
