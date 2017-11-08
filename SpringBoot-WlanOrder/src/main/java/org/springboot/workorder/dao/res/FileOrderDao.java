package org.springboot.workorder.dao.res;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * res.work_order_file
 * @author lenovo
 *
 */
public interface FileOrderDao {
	
	List<Map<String,Object>> findUnhandled();//find esbmsg with file and unhandled
	int updateFlagByuuid(@Param("uuid") String uuid);//update state to handled

}
