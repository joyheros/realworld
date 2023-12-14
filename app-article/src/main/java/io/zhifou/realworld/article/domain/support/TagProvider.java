package io.zhifou.realworld.article.domain.support;

import java.util.List;
import java.util.Optional;

import io.zhifou.realworld.article.domain.Tag;

public interface TagProvider {

	List<String> allTags();

	Optional<Tag> findTag(String tagName);

	Optional<Tag> save(Tag tag);
}
