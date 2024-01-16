import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import xyz.shi.config.SpringConfig;
import xyz.shi.entity.User;
import xyz.shi.service.UserService1;

import java.util.List;

public class JdbcTest {
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

    UserService1 userService = context.getBean(UserService1.class);
    @Test
    public void save() {
        int id = userService.save("test1234","22222");
        System.out.println(id);
    }
    @Test
    public void find() {
      User user = userService.getUser(46);
      System.out.println(user);
    }
    @Test
    public void queryUserList() {
        List<User> users = userService.queryUserList(1, 2);
        System.out.println(users);
    }
    @Test
    public void update() {
        userService.update(46, "哈哈");
    }
    @Test
    public void delete() {
        boolean flag = userService.delete(46);
        System.out.println(flag);

    }
    @Test
    public void execute1() {
        List<User> users1 = userService.execute1(1,3);
        System.out.println(users1);
    }
}
