package top.ruilink.realworld.article.infra.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import top.ruilink.realworld.article.domain.ArticleFavorite;

@Mapper
public interface ArticleFavoriteMapper {
	@Results(id = "articleFavorite", value = {
		@Result(column="article_id", property="articleId"),
		@Result(column="user_id", property="userId")
	})
	@Select("select article_id, user_id from article_favorites where article_id = #{articleId} and user_id = #{userId}")
	ArticleFavorite find(@Param("articleId") Long articleId, @Param("userId") Long userId);

	@Insert("insert into article_favorites (article_id, user_id) values (#{articleFavorite.articleId}, #{articleFavorite.userId})")
	void insert(@Param("articleFavorite") ArticleFavorite articleFavorite);

	@Delete("delete from article_favorites where article_id = #{favorite.articleId} and user_id = #{favorite.userId}")
	void delete(@Param("favorite") ArticleFavorite favorite);

	@Select("select count(1) from article_favorites where article_id = #{articleId}")
	long favoriteCount(@Param("articleId") Long articleId);
}
