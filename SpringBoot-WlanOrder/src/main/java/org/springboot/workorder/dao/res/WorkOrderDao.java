package org.springboot.workorder.dao.res;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WorkOrderDao {
	
	List<Map<String, Object>> findById(@Param("id") String id);

	List<Map<String, Object>> findByStateAndType(@Param("state") String state, @Param("type") String type);

	int insertFailRecord(@Param("failed_reason") String failed_reason, @Param("workjob_id") String workjob_id);

	int insertRecord(@Param("map") Map map);

	int updateStateById(@Param("state") String state, @Param("id") String id);
	
	int updateFailById(@Param("statement") String statement,@Param("failed_reason") String failed_reason,@Param("order_user") String order_user,@Param("workjob_id") String workjob_id);

}
