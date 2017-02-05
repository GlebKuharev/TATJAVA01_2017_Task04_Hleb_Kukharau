package catalog.bean;

public class News {
	
	private String category;
	private String releaseDate;
	private String name;
	private String description;
	
	public News(String category, String releaseDate, String name, String description) {
		super();
		this.category = category;
		this.releaseDate = releaseDate;
		this.name = name;
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
