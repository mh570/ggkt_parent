package com.fengling.ggkt.vo.live;

import com.fengling.ggkt.model.live.LiveCourseConfig;
import com.fengling.ggkt.model.live.LiveCourseGoods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "LiveCourseConfig")
public class LiveCourseConfigVo extends LiveCourseConfig {

	@ApiModelProperty(value = "ๅๅๅ่กจ")
	private List<LiveCourseGoods> liveCourseGoodsList;
}