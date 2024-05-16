package top.ruilink.realworld.article.infra;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import top.ruilink.realworld.article.domain.Tag;
import top.ruilink.realworld.article.domain.support.TagProvider;
import top.ruilink.realworld.article.infra.mapper.TagMapper;

@Repository
public class TagRepository implements TagProvider {

	private final TagMapper tagMapper;

	@Autowired
	public TagRepository(TagMapper tagMapper) {
		this.tagMapper = tagMapper;
	}

	@Override
	public List<String> allTags() {
		return tagMapper.allTags();
	}

	@Override
	public Optional<Tag> findTag(String tagName) {
		return Optional.ofNullable(tagMapper.findTag(tagName));
	}

	@Override
	public Optional<Tag> save(Tag tag) {
		Long id = tagMapper.insertTag(tag);
		tag.setId(id);
		return Optional.ofNullable(tag);
	}
}
