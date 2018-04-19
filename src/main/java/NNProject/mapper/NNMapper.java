package NNProject.mapper;

import NNProject.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;



@Mapper
public interface NNMapper {

    String INSERT_RABBIT_NAME = "INSERT INTO `nnproject`.rabbits (name) " +
            "VALUES (#{name})";
    String INSERT_USER = "INSERT INTO `nnproject`.users (user, password) " +
            "VALUES (#{user}, #{password})";
    String GET_USER = "SELECT * FROM `nnproject`.users where username = #{username}";

    @Insert(INSERT_RABBIT_NAME)
    public int insertRabbitName(String rabbit);

    @Insert(INSERT_USER)
    public int insertUser(User user);

    @Select(GET_USER)
    public User getUser(String username);


}
