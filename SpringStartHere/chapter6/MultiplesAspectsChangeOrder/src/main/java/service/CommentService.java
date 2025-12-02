package service;

import aspects.ToLog;
import model.Comment;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

@Service
public class CommentService {
    private Logger logger = Logger.getLogger(CommentService.class.getName());

    @ToLog
    public void publisComment(Comment comment) {
        logger.info("Publishing comment: " + comment.getText());
    }

    public void deleteComment(Comment comment) {
        logger.info("Deleting comment: " + comment.getText());
    }

    public void editComment(Comment comment) {
        logger.info("Editing comment: " + comment.getText());
    }
}
