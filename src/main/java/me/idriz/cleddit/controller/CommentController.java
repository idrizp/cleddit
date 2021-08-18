package me.idriz.cleddit.controller;

import static me.idriz.cleddit.util.AuthenticationUtils.getAuthenticatedProfile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import me.idriz.cleddit.model.Comment;
import me.idriz.cleddit.model.response.CommentResponse;
import me.idriz.cleddit.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	private final CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("/{postId}")
	public ResponseEntity<?> createComment(@PathVariable UUID postId, @RequestBody CreateCommentRequest request) {
		Comment comment = commentService.createComment(postId, getAuthenticatedProfile(), request.comment());
		if (comment == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(new me.idriz.cleddit.model.response.CommentResponse(comment));
	}
	
	@PostMapping("/reply/{commentId}")
	public ResponseEntity<?> createReply(@PathVariable UUID commentId, @RequestBody CreateCommentRequest request) {
		Comment reply = commentService.createReply(commentId, getAuthenticatedProfile(), request.comment());
		if (reply == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(new me.idriz.cleddit.model.response.CommentResponse(reply));
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable UUID commentId) {
		Comment comment = commentService.findById(commentId);
		if (!comment.getPoster().getId().equals(getAuthenticatedProfile().getId())) {
			return ResponseEntity.status(403).build();
		}
		if (!commentService.deleteComment(commentId)) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{postId}/{page}")
	public ResponseEntity<?> getComments(@PathVariable UUID postId, @PathVariable int page) {
		return ResponseEntity.ok(new CommentResponseList(
				commentService.getComments(postId, page)
						.stream()
						.map(CommentResponse::new)
						.collect(Collectors.toList()))
		);
	}
	
	record CreateCommentRequest(String comment) {
	
	}
	
	record CommentResponseList(List<CommentResponse> comments) {
	
	}
	
}
