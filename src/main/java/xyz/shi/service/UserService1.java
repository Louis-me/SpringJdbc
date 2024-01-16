package xyz.shi.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import xyz.shi.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService1 {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(String name, String pwd) {
        //该类返回新增记录时的自增长主键值。
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcTemplate.update((connection) -> {
            PreparedStatement ps = connection.prepareStatement("insert into users(name,password) values(?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setObject(1, name);
            ps.setObject(2, pwd);
            return ps;
        }, keyHolder);
        if (update > 0){
            System.out.println("保存成功...user id:"+keyHolder.getKey());
        }
        return keyHolder.getKey().intValue();
    }

    /**
     * 根据用户id 查询
     * @param id
     * @return
     */
    public User getUser(int id){
        User user = jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id},new BeanPropertyRowMapper<>(User.class));
        return user;
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    public List<User> queryUserList(int page, int pageSize){
        int index = (page-1)*pageSize;
        int size = pageSize;
        List<User> list = jdbcTemplate.query("select * from users limit ?,?", new Object[]{index, size}, new BeanPropertyRowMapper<>(User.class));
        return list;
    }

    /**
     * 更新
     * @param id
     * @param name
     * @return
     */
    public boolean update(int id,String name){
        int update = jdbcTemplate.update("update users set name=? where id=?", name, id);
        if (update > 0){
            return true;
        }
        throw new RuntimeException("update error");
    }

    public boolean delete(int id){
        int deleteCount = jdbcTemplate.update("delete from users where id=?", id);
        return deleteCount > 0;
    }
    // execute的用法
    public List<User> execute1(int page,int pageSize) {
        return jdbcTemplate.execute((Connection conn) -> {
            PreparedStatement ps = conn.prepareStatement("select * from users limit ?,?");
            ps.setObject(1, (page - 1) * pageSize);
            ps.setObject(2, pageSize);
            ResultSet resultSet = ps.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                userList.add(user);
            }
            return userList;
        });
    }
}
