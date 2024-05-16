package top.ruilink.realworld.article.application.data;

public class ArticleFacets {
	private String tag;
	private String author;
	private String favorited;
	private Pagination pagination;

	public ArticleFacets(String tag, String author, String favorited, Pagination pagination) {
		this.tag = tag;
		this.author = author;
		this.favorited = favorited;
		this.pagination = pagination;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the favorited
	 */
	public String getFavorited() {
		return favorited;
	}

	/**
	 * @param favorited the favorited to set
	 */
	public void setFavorited(String favorited) {
		this.favorited = favorited;
	}

	/**
	 * @return the pagination
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
