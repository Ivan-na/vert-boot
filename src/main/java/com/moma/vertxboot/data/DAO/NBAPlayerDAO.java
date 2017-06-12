/**  
 * @Title: NBAPlayerDAO.java
 * @Package: com.moma.vertxboot.data
 * @author: Ivan
 * @date: Jun 12, 2017 2:44:34 PM
 * @version: V1.0  
 */

package com.moma.vertxboot.data.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moma.vertxboot.data.entity.NBAPlayer;

/**
 * <p>Company: itic</p>
 * 
 * @author: Ivan
 * @date: Jun 12, 2017 2:44:34 PM
 * @version: V1.0
 */
@Repository
public interface NBAPlayerDAO
        extends CrudRepository<NBAPlayer, Long> {

}
