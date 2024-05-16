package top.ruilink.realworld.article.domain;

import java.util.Objects;

public class Tag {
	private Long id;
	private String name;

	public Tag() {
	}

	public Tag(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Tag(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Tag other && Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name);
	}

	@Override
	public String toString() {
		return "Tag {" + "id=" + id + ", name='" + name + "'}";
	}
}
