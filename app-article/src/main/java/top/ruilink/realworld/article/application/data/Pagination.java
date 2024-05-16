package top.ruilink.realworld.article.application.data;

public class Pagination {
	private long offset;
	private int limit;

	public Pagination(long offset, int limit) {
		this.offset = (offset > 0) ? offset : 0;
		this.limit = (limit < 0 || limit > 100) ? 20 : limit;
	}

	/**
	 * @return the offset
	 */
	public long getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(long offset) {
		this.offset = offset;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
