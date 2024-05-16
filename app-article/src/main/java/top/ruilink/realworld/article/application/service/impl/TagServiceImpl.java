package top.ruilink.realworld.article.application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.ruilink.realworld.article.application.service.TagService;
import top.ruilink.realworld.article.domain.support.TagProvider;

@Service
public class TagServiceImpl implements TagService {
	private final TagProvider tagProvider;

	@Autowired
	public TagServiceImpl(TagProvider tagProvider) {
		this.tagProvider = tagProvider;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> allTags() {
		return tagProvider.allTags();
	}

}
