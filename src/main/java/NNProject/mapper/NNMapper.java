package NNProject.mapper;

import NNProject.model.Rabbit;
import NNProject.model.User;
import NNProject.model.UserRabbit;
import org.apache.ibatis.annotations.*;

import javax.persistence.JoinTable;
import java.util.ArrayList;


@Mapper
public interface NNMapper {

    String INSERT_RABBIT_NAME = "INSERT INTO `nnproject`.rabbits (name) " +
            "VALUES (#{name})";
    String INSERT_USER = "INSERT INTO `nnproject`.user (user, password) " +
            "VALUES (#{user}, #{password})";
    String GET_USER = "SELECT * FROM `nnproject`.user where username = #{username}";
    String GET_NAME_BY_ID = "SELECT * FROM `nnproject`.rabbits where rabbit_id = #{rabbit_id}";
    String UPDATE_NAME = "UPDATE `nnproject`.rabbits SET name = #{name} WHERE rabbit_id = #{rabbit_id}";
    String UPDATE_ID = "UPDATE `nnproject`.rabbits SET rabbit_id = #{id} WHERE name = #{name}";
    String GET_ALL__NAMES = "SELECT * FROM `nnproject`.rabbits";
    String DELETE_NAME= "DELETE FROM `nnproject`.rabbits where rabbit_id = #{rabbit_id}";
    String JOIN_USERRABBIT= "SELECT r.name, u.username FROM `nnproject`.rabbits r inner join user_rabbits ur on(r.rabbit_id=ur.rabbit_id) inner join user u on(u.user_id=ur.user_id) where r.name= #{name} and u.username= #{username}";

    @Insert(INSERT_RABBIT_NAME)
    public int insertRabbitName(String rabbit);

    @Insert(INSERT_USER)
    public int insertUser(User user);

    @Select(GET_USER)
    public User getUser(String username);

    @Update(UPDATE_NAME)
    public int updateName(Rabbit rabbit);

    @Update(UPDATE_ID)
    public int updateID(Rabbit rabbit);

    @Select(GET_ALL__NAMES)
    public ArrayList<Rabbit> getAllNames();

    @Select(GET_NAME_BY_ID)
    public Rabbit getByID(int id);

    @Delete(DELETE_NAME)
    public void deleteName(int id);

    @Select(JOIN_USERRABBIT)
    public void joinUR(String rabbitname, String username);

}
