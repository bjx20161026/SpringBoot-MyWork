package org.springboot.esbcollecthelper.dao.def;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface EsbMsgCountDao {

	List<Map<String, Object>> countEffectiveMsg();

	Map<String, Object> messageComponents();

	List<Map<String, Object>> transError();

	List<Map<String, Object>> errorReason();

	List<Map<String, Object>> msgDetail(@Param("protocol") String protocol, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

}
