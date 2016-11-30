package com.wujiming.music;

import org.springframework.data.repository.CrudRepository;

/**
 * @author 武继明 [wzslw@163.com]
 * @since 16-11-30 下午1:07
 */
interface SongRepository extends CrudRepository<Song,Long> {
    Song findTopByOrderById();
}
