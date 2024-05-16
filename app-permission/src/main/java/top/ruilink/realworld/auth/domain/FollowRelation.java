package top.ruilink.realworld.auth.domain;

import java.util.Objects;

public class FollowRelation {
	private Long userId;
	private Long targetId;

	public FollowRelation() {
	}

	public FollowRelation(Long userId, Long targetId) {
		this.userId = userId;
		this.targetId = targetId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the targetId
	 */
	public Long getTargetId() {
		return targetId;
	}

	/**
	 * @param targetId the targetId to set
	 */
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof FollowRelation other && Objects.equals(this.userId, other.userId)
				&& Objects.equals(this.targetId, other.targetId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.userId, this.targetId);
	}

	@Override
	public String toString() {
		return "FollowRelation {" + "userId=" + userId + ", targetId=" + targetId + "}";
	}
}
