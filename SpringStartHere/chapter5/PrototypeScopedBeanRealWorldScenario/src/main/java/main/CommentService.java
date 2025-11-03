package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private ApplicationContext context;

    public void sendComment(Comment comment) {
        CommentProcessor p =
                this.context.getBean(CommentProcessor.class);

        p.setComment(comment);
        p.processComment(comment);
        p.validateComment(comment);

        comment = p.getComment();
    }
}
