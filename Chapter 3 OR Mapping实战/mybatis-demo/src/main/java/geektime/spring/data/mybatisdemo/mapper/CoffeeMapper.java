package geektime.spring.data.mybatisdemo.mapper;

import geektime.spring.data.mybatisdemo.model.Coffee;
import org.apache.ibatis.annotations.*;

import javax.swing.plaf.ColorUIResource;

/**
 *  声明是一个mybatis的Mapper
 */
@Mapper
public interface CoffeeMapper {

    /**
     * 保存方法
      */
    @Insert("insert into t_coffee (name, price, create_time, update_time) " +
            "values (#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(Coffee coffee);

    /**
     * 查查方法
     * @return 咖啡的数据库实例数据
     */
    @Select("select * from t_coffee where id = #{id}")
    @Results({
        @Result(id = true, column = "id", property = "id"),
        @Result(column = "create_time", property = "createTime")
        // 注解可以实现一样的效果
    })
    Coffee findById(@Param("id") Long id);
}
