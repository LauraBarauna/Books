package main;

import model.Comment;
import proxies.CommentNotificationProxy;
import proxies.EmailCommentNotificationProxy;
import repositories.CommentRepository;
import repositories.DBCommentRepository;
import services.CommentService;

public class Main {
    public static void main(String[] args) {
        CommentRepository repo = new DBCommentRepository();
        CommentNotificationProxy notification = new EmailCommentNotificationProxy();

        CommentService service = new CommentService(repo, notification);

        Comment comment = new Comment();
        comment.setAuthor("Laura");
        comment.setText("Demo Comment");

        service.publishComment(comment);
    }
}
