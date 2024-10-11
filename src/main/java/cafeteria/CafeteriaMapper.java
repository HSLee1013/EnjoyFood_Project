package cafeteria;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface CafeteriaMapper {

	@Select("SELECT cafeNum, cafeName, cafeOpenTime, cafePhoneNumber, cafeAddress, cafePrice, cafeOwner FROM Cafeteria")
	@Results(id = "cafeResults", value = {
			@Result(column = "cafeNum", property = "cafeNum", jdbcType = JdbcType.INTEGER),
			@Result(column = "cafeName", property = "cafeName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "cafeOpenTime", property = "cafeOpenTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "cafePhoneNumber", property = "cafePhoneNumber", jdbcType = JdbcType.VARCHAR),
			@Result(column = "cafeAddress", property = "cafeAddress", jdbcType = JdbcType.VARCHAR),
			@Result(column = "cafePrice", property = "cafePrice", jdbcType = JdbcType.INTEGER),
			@Result(column = "cafeOwner", property = "cafeOwner", jdbcType = JdbcType.VARCHAR) })
	List<Cafeteria> selectAll();

	// 메뉴명, 카테고리, 태그, 카페이름, 주소를 검색한 식당 리스트
	@Select({ "SELECT DISTINCT cafeName, cafeAddress, cafePhoneNumber, cafePrice FROM cafeteria",
			"NATURAL JOIN cafecategory NATURAL JOIN category_management NATURAL JOIN cafetag",
			"WHERE cafeNum IN (SELECT cafeNum FROM menu where menuName  LIKE CONCAT('%', #{menuName}, '%'))",
			"OR categoryName=#{categoryName} OR cafetag LIKE CONCAT('%', #{cafetag}, '%')",
			"OR cafeName LIKE CONCAT('%', #{cafeName}, '%') OR cafeAddress LIKE CONCAT('%', #{cafeAddress}, '%')" })
	@ResultMap("cafeResults")
	List<Cafeteria> searchByAll(@Param("menuName") String menuName, @Param("categoryName") String categoryName,
			@Param("cafetag") String cafetag, @Param("cafeName") String cafeName,
			@Param("cafeAddress") String cafeAddress);

//	@Select({ "<script> SELECT DISTINCT cafeName, cafeAddress, cafePhoneNumber, cafePrice \r\n"
//			+ "    FROM cafeteria\r\n"
//			+ "    NATURAL JOIN cafecategory \r\n"
//			+ "    NATURAL JOIN category_management \r\n"
//			+ "    NATURAL JOIN cafetag\r\n"
//			+ "    WHERE cafePrice <= #{cafePrice} \r\n"
//			+ "    AND cafetag IN (\r\n"
//			+ "        <foreach item=\"item\" index=\"index\" collection=\"cafetag\" open=\"(\" separator=\",\" close=\")\">\r\n"
//			+ "            #{item}\r\n"
//			+ "        </foreach>"
//			+ "</script>" })
//	List<Cafeteria> searchByPT(@Param("cafePrice") int cafePrice, @Param("cafetag") List<String> cafetags);

	// 맛집 새로 추가
	@Insert({ "INSERT INTO cafeteria (cafeNum, cafeName, cafeOpenTime, cafePhoneNumber,",
			"cafeAddress, cafePrice, cafeOwner)",
			"VALUES (#{cafeNum}, #{cafeName}, #{cafeOpenTime}, #{cafePhoneNumber},",
			"#{cafeAddress}, #{cafePrice}, #{cafeOwner})" })
	int insert(Cafeteria cafeteria);

	@Select("SELECT * FROM cafeteria WHERE cafeNum=#{cafeNum}")
	@ResultMap("cafeResults")
	Cafeteria selectByNum(@Param("cafeNum") int cafeNum);

	// 맛집명 조회
	@Select("SELECT * FROM cafeteria WHERE cafeName=#{cafeName}")
	@ResultMap("cafeResults")
	Cafeteria selectByName(@Param("cafeName") String cafeName);

	// 맛집 정보 수정
	@Update({ "UPDATE cafeteria SET cafeNum=#{cafeNum}, cafeName=#{cafeName}, ",
			"cafeOpenTime=#{cafeOpenTime}, cafePhoneNumber=#{cafePhoneNumber}, ",
			"cafeAddress=#{cafeAddress}, cafePrice=#{cafePrice} ", "WHERE cafeNum=#{cafeNum}" })
	int update(Cafeteria cafeteria);

	// 삭제
	@Delete("DELETE FROM Cafeteria WHERE cafeNum=#{cafeNum}")
	int delete(int cafeNum);

	// 가게 번호와 해당 이미지 조회
	@Select({ "SELECT cp.picNumber, cp.cafePic, cp.cafeNum, c.cafeName, c.cafeOpenTime, c.cafePhoneNumber",
			"c.cafeAddress, c.cafePrice, c.cafeOwner", "FROM cafePic AS cp",
			"JOIN cafeteria AS c ON cp.cafeNum = c.cafeNum", "WHERE cp.cafeNum = #{cafeNum}" })
	List<Cafeteria> showByPic(@Param("cafeNum") int cafeNum);

//	@Select("SELECT cafe_num, cafe_tag")
//	int selectByTag(Cafe_Tag cafeTag);

//	@Insert("INSERT INTO cafe_pic (cafe_num, cafe_pic) VALUES (#{cafe_num}, #{cafe_pic})")
//	int insertPic(@Param("cafe_num") int cafeNum, @Param("cafe_pic") String cafePic);

	@Insert("INSERT INTO cafeTag (cafeNum, cafeTag) VALUES (#{cafeNum}, #{cafeTag}")
	int insertTag(@Param("cafeNum") int cafeNum, @Param("cafeTag") String cafeTag);

	@Insert({ "INSERT INTO menu (cafeNum, menuNum, menuName, menuPrice, menuNamepic, menuExplain) ",
			"VALUES (#{cafeNum}, #{menuNum}, #{menuName}, #{menuPrice}, #{menuNamepic}, #{menuExplain})" })
	int insertMenu(Menu menu);

	@Select("SELECT avg(cafeScore) FROM cafeReview WHERE cafeNum=#{cafeNum}")
	String selectAvg(@Param("cafeNum") int cafeNum);

	// 리뷰화면 - 해당 cafeNum의 메뉴 출력
	@Select("SELECT menuName, menuPrice FROM menu WHERE cafeNum=#{cafeNum}")
	List<Menu> showCafeMenu(@Param("cafeNum") int cafeNum);

	// 사용자 리뷰 DB저장
	@Insert({ "INSERT INTO cafereview (cafeNum,  cafeScore, cafeComment, userId, userPayment, userPic) ",
			"VALUES (#{cafeNum},  #{cafeScore}, #{cafeComment}, #{userId}, #{userPayment}, #{userPic})" })
	int insertReview(CafeReview cafeReview);

}