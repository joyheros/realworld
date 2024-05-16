package top.ruilink.realworld.article.infra.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import top.ruilink.realworld.article.domain.Comment;

@Mapper
public interface CommentMapper {
	@Insert("insert into comments(body, user_id, article_id, created_at, updated_at) "
		+ "values (#{comment.body}, #{comment.userId}, #{comment.articleId}, #{comment.createdAt}, #{comment.createdAt})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	Long insert(@Param("comment") Comment comment);

	@Delete("delete from comments where id = #{id}")
	void delete(@Param("id") Long id);

	@Results(id = "comment", value = {
		@Result(id=true, column="id", property="id"),
		@Result(column="body", property="body"),
		@Result(column="user_id", property="userId"),
		@Result(column="article_id", property="articleId"),
		@Result(column="created_at", property="createdAt")
	})
	@Select("select id, body, user_id, article_id, created_at from comments where id = #{id}")
	Comment findById(@Param("id") Long id);

	@ResultMap("comment")
	@Select("select id, body, user_id, article_id, created_at from comments where article_id = #{articleId}")
	List<Comment> findByArticle(@Param("articleId") Long articleId);
}
