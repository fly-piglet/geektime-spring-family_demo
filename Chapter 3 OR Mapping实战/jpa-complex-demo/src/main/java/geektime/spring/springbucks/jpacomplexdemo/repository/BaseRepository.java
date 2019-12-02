package geektime.spring.springbucks.jpacomplexdemo.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

// 不需要为这个ben做处理
@NoRepositoryBean
public interface BaseRepository<T, Long> extends PagingAndSortingRepository<T, Long> {

    /**
     * 查询前三条，根据更新时间和id排序
     * @return
     */
    List<T> findTop3ByOrderByUpdateTimeDescIdAsc();

//    List<T> findTop3ByOrderByUpdateTimeDescIdAsc();
}
