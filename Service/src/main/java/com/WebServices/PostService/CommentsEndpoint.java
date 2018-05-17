package com.WebServices.PostService;

import com.WebServices.PostService.ServiceFault.ServiceFault;
import com.WebServices.PostService.ServiceFault.ServiceFaultException;
import com.WebServices.PostService.models.Comment;
import com.WebServices.PostService.repositories.CommentRepository;
import com.WebServices.PostService.repositories.PostRepository;
import com.WebServices.PostService.repositories.UserRepository;
import com.userspostscomments.comments.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CommentsEndpoint {
	private static final String NAMESPACE_URI = "http://usersPostsComments.com/comments";

	@Autowired
	UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCommentsRequest")
	@ResponsePayload
	public GetAllCommentsResponse getAllComments(@RequestPayload GetAllCommentsRequest request) {
        GetAllCommentsResponse response = new GetAllCommentsResponse();

		List<Comment> comments = commentRepository.findAll();
		for(Comment comment : comments){
            com.userspostscomments.comments.Comment commentDTO = new com.userspostscomments.comments.Comment();
			commentDTO.setId((int)comment.getId());
			commentDTO.setUserId((int)comment.getUserId());
            commentDTO.setPostId((int)comment.getPostId());
            commentDTO.setBody(comment.getBody());
			response.getComments().add(commentDTO);
		}

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCommentByIdRequest")
	@ResponsePayload
	public GetCommentByIdResponse getCommentById(@RequestPayload GetCommentByIdRequest request) {
        GetCommentByIdResponse response = new GetCommentByIdResponse();

        Comment comment =  commentRepository.findById((long)request.getId()).orElseThrow(() ->
                new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A comment with id: " + request.getId() + " was not found.")));

        com.userspostscomments.comments.Comment commentDTO = new com.userspostscomments.comments.Comment();
        commentDTO.setId((int)comment.getId());
        commentDTO.setUserId((int)comment.getUserId());
        commentDTO.setPostId((int)comment.getPostId());
        commentDTO.setBody(comment.getBody());

        response.setComment(commentDTO);

		return response;
	}

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createCommentRequest")
    @ResponsePayload
    public CreateCommentResponse createUser(@RequestPayload CreateCommentRequest request) {
        CreateCommentResponse response = new CreateCommentResponse();

        userRepository.findById((long)request.getUserId()).orElseThrow(() ->
                new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A user with id: " + request.getUserId() + " was not found.")));

        postRepository.findById((long)request.getPostId()).orElseThrow(() ->
                new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A post with id: " + request.getPostId() + " was not found.")));

        if(request.getBody() == null){
            throw new ServiceFaultException("ERROR", new ServiceFault("BAD_REQUEST", "Please, specify the body of the comment."));
        }

        Comment comment = new Comment();
        comment.setUserId(request.getUserId());
        comment.setPostId(request.getPostId());
        comment.setBody(request.getBody());

        commentRepository.save(comment);

        com.userspostscomments.comments.Comment commentDTO = new com.userspostscomments.comments.Comment();
        commentDTO.setId((int)comment.getId());
        commentDTO.setUserId((int)comment.getUserId());
        commentDTO.setPostId((int)comment.getPostId());
        commentDTO.setBody(comment.getBody());

        response.setCreatedComment(commentDTO);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCommentRequest")
    @ResponsePayload
    public UpdateCommentResponse updateUser(@RequestPayload UpdateCommentRequest request) {
        UpdateCommentResponse response = new UpdateCommentResponse();

        Comment comment =  commentRepository.findById((long)request.getId()).orElseThrow(() ->
                new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A comment with id: " + request.getId() + " was not found.")));

        if (request.getUserId() != 0){
            userRepository.findById((long)request.getUserId()).orElseThrow(() ->
                    new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A user with id: " + request.getUserId() + " was not found.")));
            comment.setUserId(request.getUserId());
        }

        if (request.getPostId() != 0){
            postRepository.findById((long)request.getPostId()).orElseThrow(() ->
                    new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A post with id: " + request.getPostId() + " was not found.")));
            comment.setPostId(request.getPostId());
        }

        if(request.getBody() != null){
            comment.setBody(request.getBody());
        }

        commentRepository.save(comment);

        com.userspostscomments.comments.Comment commentDTO = new com.userspostscomments.comments.Comment();
        commentDTO.setId((int)comment.getId());
        commentDTO.setUserId((int)comment.getUserId());
        commentDTO.setPostId((int)comment.getPostId());
        commentDTO.setBody(comment.getBody());

        response.setUpdatedComment(commentDTO);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCommentRequest")
    @ResponsePayload
    public DeleteCommentResponse deleteUser(@RequestPayload DeleteCommentRequest request) {
        DeleteCommentResponse response = new DeleteCommentResponse();

        Comment comment = commentRepository.findById((long)request.getId()).orElseThrow(() ->
                new ServiceFaultException("ERROR", new ServiceFault("NOT_FOUND", "A comment with id: " + request.getId() + " was not found.")));

        commentRepository.delete(comment);

        return response;
    }
}