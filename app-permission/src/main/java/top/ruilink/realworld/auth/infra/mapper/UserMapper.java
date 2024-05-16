package top.ruilink.realworld.auth.infra.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import top.ruilink.realworld.auth.domain.FollowRelation;
import top.ruilink.realworld.auth.domain.User;

@Mapper
public interface UserMapper {
	@Insert("insert into users (username, email, password, bio, image) "
		+ "values(#{user.username},#{user.email},#{user.password},#{user.bio},#{user.image})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	Long insert(@Param("user") User user);

	@ResultType(User.class)
	@Select("select * from users where username = #{username}")
	User findByUsername(@Param("username") String username);

	@ResultType(User.class)
	@Select("select id, username, email, password, bio, image from users where email = #{email}")
	User findByEmail(@Param("email") String email);

	@ResultType(User.class)
	@Select("select id, username, email, password, bio, image from users where id = #{id}")
	User findById(@Param("id") Long id);

	@Update({"<script>",
        "update users",
        "<set>",
        "    <if test=\"user.username != ''\">username = #{user.username},</if>",
        "    <if test=\"user.email != ''\">email = #{user.email},</if>",
        "    <if test=\"user.password != ''\">password = #{user.password},</if>",
        "    <if test=\"user.bio != ''\">bio = #{user.bio},</if>",
        "    <if test=\"user.image != ''\">image = #{user.image}</if>",
        "</set>",
        "where id = #{user.id}",
	"</script>"})
	void update(@Param("user") User user);

	@Delete("delete from users where id = #{id}")
	void delete(@Param("id") Long id);

	@Results(id = "follow", value = {
		@Result(column="followUserId", property="userId"),
		@Result(column="followTargetId", property="targetId")
	})
	@Select("SELECT F.user_id followUserId, F.follow_id followTargetId "
			+ "from follows F where F.user_id = #{userId} and F.follow_id = #{targetId}")
	FollowRelation findRelation(@Param("userId") Long userId, @Param("targetId") Long targetId);

	@Insert("insert into follows(user_id, follow_id) values (#{followRelation.userId}, #{followRelation.targetId})")
	void saveRelation(@Param("followRelation") FollowRelation followRelation);

	@Delete("delete from follows where user_id = #{followRelation.userId} and follow_id = #{followRelation.targetId}")
	void deleteRelation(@Param("followRelation") FollowRelation followRelation);
}
