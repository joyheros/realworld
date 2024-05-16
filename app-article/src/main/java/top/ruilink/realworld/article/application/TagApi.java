package top.ruilink.realworld.article.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import top.ruilink.realworld.article.application.service.TagService;

@RestController
public class TagApi {
	private final TagService tagService;

	@Autowired
	public TagApi(TagService tagService) {
		this.tagService = tagService;
	}

	@GetMapping("/api/tags")
	public ResponseEntity<?> getTags() {
		List<String> tags = tagService.allTags();
		return ResponseEntity.status(HttpStatus.OK).body(tags);
	}
}
