package org.springboot.workorder.dao.res;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TemporaryBakDao {

	int insertRecord(@Param("map") Map map);

	int deleteRecord(@Param("map") Map map);

	List<Map<String, Object>> findAll();
}
