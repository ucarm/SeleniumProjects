package youtubeSeo;

public class YoutubeSEO {
	private String name;
	private int numberOfSearchResults;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumberOfSearchResults() {
		return numberOfSearchResults;
	}
	public void setNumberOfSearchResults(int numberOfSearchResults) {
		this.numberOfSearchResults = numberOfSearchResults;
	}
	@Override
	public String toString() {
		return "YoutubeSEO [name=" + name + ", numberOfSearchResults=" + numberOfSearchResults + "]";
	}
	public YoutubeSEO(String name, int numberOfSearchResults) {
		super();
		this.name = name;
		this.numberOfSearchResults = numberOfSearchResults;
	}
	
	
}
