package top.ruilink.realworld.article.infra.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import top.ruilink.realworld.article.domain.Tag;

@Mapper
public interface TagMapper {
	@ResultType(String.class)
	@Select("select name from tags")
	List<String> allTags();

	@ResultType(Tag.class)
	@Select("select id, name from tags where name = #{tagName}")
	Tag findTag(@Param("tagName") String tagName);

	@Insert("insert into tags (name) values (#{tag.name})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	Long insertTag(@Param("tag") Tag tag);
}
