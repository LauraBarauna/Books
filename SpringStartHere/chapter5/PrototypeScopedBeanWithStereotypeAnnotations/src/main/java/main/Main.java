package main;

import config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var c = new AnnotationConfigApplicationContext(
                ProjectConfig.class);

        var commentService = c.getBean(CommentService.class);
        var userService = c.getBean(UserService.class);

        boolean b = commentService.getCommentRepository() == userService.getCommentRepository();

        System.out.println(b);

    }
}
