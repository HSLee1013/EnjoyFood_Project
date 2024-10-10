package cafeteria;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "cafeNum", "cafeScoreNum","cafeScore", "cafeComment", "userId", "userPayment", "userPic", "date"})
@Generated("jsonschema2pojo")
public class CafeScore {
	@JsonProperty("cafeNum")
	private Integer cafeNum;
	@JsonProperty("cafeScoreNum")
	private String cafeScoreNum;
	@JsonProperty("cafeScore")
	private Integer cafeScore;
	@JsonProperty("cafeComment")
	private String cafeComment;
	@JsonProperty("userId")
	private String userId;
	@JsonProperty("userPayment")
	private Integer userPayment;
	@JsonProperty("userPic")
	private String userPic;
	
}
