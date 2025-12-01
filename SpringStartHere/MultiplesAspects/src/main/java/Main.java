import config.ProjectConfig;
import model.Comment;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.CommentService;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Comment comment = new Comment();
        comment.setText("This is a comment");
        comment.setAuthor("John Doe");

        CommentService service = context.getBean(CommentService.class);
        service.publisComment(comment);
        System.out.println();
        service.deleteComment(comment);
    }
}
