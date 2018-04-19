package NNProject.mapper;

import NNProject.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface NNMapper {

    String INSERT_RABBIT_NAME = "INSERT INTO `nnproject`.rabbits (name) " +
            "VALUES (#{name})";
    String INSERT_USER = "INSERT INTO `nnproject`.users (user, password) " +
            "VALUES (#{user}, #{password})";
    String GET_USER = "SELECT * FROM `nnproject`.users where username = #{username}";
    String GET_NAME_BY_ID = "SELECT * FROM `nnproject`.rabbits where id = #{id}";
    String UPDATE_NAME = "UPDATE `nnproject`.rabbits SET name = #{name} WHERE id = #{id}";
    String GET_ALL__NAMES = "SELECT * FROM `nnproject`.rabbits where isActive = 1";

    @Insert(INSERT_RABBIT_NAME)
    public int insertRabbitName(String rabbit);

    @Insert(INSERT_USER)
    public int insertUser(User user);

    @Select(GET_USER)
    public User getUser(String username);

    @Update(UPDATE_NAME)
    public int updateName (User user);


}
