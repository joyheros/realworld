package top.ruilink.realworld.article.infra.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import top.ruilink.realworld.article.domain.Article;
import top.ruilink.realworld.article.domain.Tag;

@Mapper
public interface ArticleMapper {
	@Insert("insert into article_tags (article_id, tag_id) values(#{articleId}, #{tagId})")
	void insertArticleTag(Long articleId, Long tagId);

	@Insert("insert into articles (slug, title, description, body, user_id, created_at, updated_at) "
		+ "values(#{article.slug}, #{article.title}, #{article.description}, #{article.body}, #{article.userId}, #{article.createdAt}, #{article.updatedAt})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	Long insert(@Param("article") Article article);

	@Results(id = "article", value = {
		@Result(id=true, column="id", property="id"),
		@Result(column="user_id", property="userId"),
		@Result(column="title", property="title"),
		@Result(column="slug", property="slug"),
		@Result(column="description", property="description"),
		@Result(column="body", property="body"),
		@Result(column="created_at", property="createdAt"),
		@Result(column="updated_at", property="updatedAt"),
		@Result(column="id", property="tags", javaType=Set.class, many = @Many(select = "selectTagForArticle"))
	})
	@Select("select id, slug, title, description, body, user_id, created_at, updated_at from articles where id = #{id}")
	Article findById(@Param("id") Long id);

	@Results(id = "tag", value = {
		@Result(id=true, column="id", property="id"),
		@Result(column="name", property="name")
	})
	@Select("select id, name from tags where id in (select tag_id from article_tags where article_id=#{articleId})")
	Set<Tag> selectTagForArticle(@Param("articleId") Long articleId);

	@ResultMap("article")
	@Select("select id, slug, title, description, body, user_id, created_at, updated_at from articles where slug = #{slug}")
	Article findBySlug(@Param("slug") String slug);

	@Update({"<script>",
		"update articles ",
	    "  <set>",
	    "      <if test=\"article.title != ''\">title = #{article.title},</if>",
	    "      <if test=\"article.title != ''\">slug = #{article.slug},</if>",
	    "      <if test=\"article.description != ''\">description = #{article.description},</if>",
	    "      <if test=\"article.body != ''\">body = #{article.body}</if>",
	    "  </set>",
	    "where id = #{article.id}",
	"</script>"})
	void update(@Param("article") Article article);

	@Delete("delete from articles where id = #{id}")
	void delete(@Param("id") Long id);

	@ResultMap("article")
	@Select({"<script>",
		"select DISTINCT A.id, A.slug, A.title, A.description, A.body, A.user_id, A.created_at, A.updated_at ",
		"from articles A",
		"left join article_tags AT on A.id = AT.article_id ",
		"left join tags T on T.id = AT.tag_id ",
		"left join article_favorites AF on AF.article_id = A.id ",
		"left join users AU on AU.id = A.user_id ",
		"left join users AFU on AFU.id = AF.user_id ",
		"<where>",
	    "    <if test=\"tag != null\">T.name = #{tag}</if>",
	    "    <if test=\"author != null\">AND AU.username = #{author}</if>",
	    "    <if test=\"favorited != null\">AND AFU.username = #{favorited}</if>",
	    "</where>",
	    "order by A.created_at desc",
	    "limit #{offset}, #{limit}",
	"</script>"})
	List<Article> findByFacets(@Param("tag") String tag, @Param("author") String author,
			@Param("favorited") String favorited, @Param("offset") long offset, @Param("limit") int limit);

	@Select({"<script>",
		"select count(DISTINCT A.id) ",
		"from articles A ",
		"left join article_tags AT on A.id = AT.article_id ",
		"left join tags T on T.id = AT.tag_id ",
		"left join article_favorites AF on AF.article_id = A.id ",
		"left join users AU on AU.id = A.user_id ",
		"left join users AFU on AFU.id = AF.user_id ",
		"<where>",
	    "    <if test=\"tag != null\">T.name = #{tag}</if>",
	    "    <if test=\"author != null\">AND AU.username = #{author}</if>",
	    "    <if test=\"favorited != null\">AND AFU.username = #{favorited}</if>",
	    "</where>",
	"</script>"})
	long countByFacets(@Param("tag") String tag, @Param("author") String author, @Param("favorited") String favorited);

	@ResultMap("article")
	@Select("""
		select DISTINCT A.id, A.slug, A.title, A.description, A.body, A.user_id, A.created_at, A.updated_at 
		from articles A
		where A.user_id in (select F.follow_id from follows F where F.user_id = #{authorId}) 
        limit #{offset}, #{limit} 
	""")
	List<Article> findByAuthor(@Param("authorId") Long authorId, @Param("offset") long offset, @Param("limit") int limit);

	@Select("select count(DISTINCT A.id) from articles A where A.user_id in (select F.follow_id from follows F where F.user_id = #{authorId})")
	long countByAuthor(@Param("authorId") Long authorId);
}
