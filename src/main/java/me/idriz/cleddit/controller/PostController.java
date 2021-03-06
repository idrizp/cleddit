package me.idriz.cleddit.controller;

import static me.idriz.cleddit.util.AuthenticationUtils.getAuthenticatedProfile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import me.idriz.cleddit.model.Post;
import me.idriz.cleddit.model.Profile;
import me.idriz.cleddit.model.Subcleddit;
import me.idriz.cleddit.model.Vote;
import me.idriz.cleddit.model.response.PostResponse;
import me.idriz.cleddit.model.response.CommentResponse;
import me.idriz.cleddit.service.CommentService;
import me.idriz.cleddit.service.PostService;
import me.idriz.cleddit.service.SubcledditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	private final CommentService commentService;
	private final PostService postService;
	private final SubcledditService subcledditService;
	
	public PostController(CommentService commentService, PostService postService,
			SubcledditService subcledditService) {
		this.commentService = commentService;
		this.postService = postService;
		this.subcledditService = subcledditService;
	}
	
	@GetMapping("/{subcleddit}/{page}")
	public ResponseEntity<?> listPosts(@PathVariable String subcleddit, @PathVariable int page) {
		if (page < 1) {
			return ResponseEntity.badRequest().build();
		}
		
		// Whether the subcleddit is "all", which is the posts from the entire website
		boolean isAll = subcleddit.equalsIgnoreCase("all");
		
		if (!isAll && subcledditService.findByName(subcleddit) == null) {
			return ResponseEntity.status(404).build();
		}
		
		Profile profile = getAuthenticatedProfile();
		List<Post> posts = isAll ? postService.getPosts(page - 1)
				: postService.getPosts(subcleddit, page - 1);
		
		return ResponseEntity.ok(
				new PostListResponse(
						posts.stream().map(post -> {
							Vote vote = null;
							if (profile != null) {
								vote = postService.getVoteByProfile(profile, post);
							}
							return PostResponse.fromPost(post, vote, null);
						}).collect(Collectors.toList()))
		);
	}
	
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getPost(@PathVariable UUID id) {
		Post post = postService.getPostById(id);
		if (post == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<CommentResponse> initialComments = commentService.getComments(post.getId(), 1)
				.stream()
				.map(CommentResponse::new)
				.collect(Collectors.toList());
		
		Profile profile = getAuthenticatedProfile();
		return ResponseEntity.ok(
				PostResponse.fromPost(post, profile == null ? null : postService.getVoteByProfile(profile, post),
						initialComments));
	}
	
	@PostMapping("/")
	public ResponseEntity<?> createPost(@RequestBody CreatePostRequest request) {
		Profile profile = getAuthenticatedProfile();
		
		Subcleddit subcleddit = subcledditService.findByName(request.subcledditName());
		if (subcleddit == null) {
			return ResponseEntity.notFound().build();
		}
		
		Post post = postService.createPost(profile, subcleddit, request.title(), request.content());
		return ResponseEntity.ok(new PostCreateResponse(post.getId()));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(@PathVariable UUID id) {
		Post post = postService.getPostById(id);
		if (post == null) {
			return ResponseEntity.notFound().build();
		}
		if (!post.getPoster().getId().equals(getAuthenticatedProfile().getId())) {
			return ResponseEntity.status(403).build();
		}
		if (!postService.deletePost(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/vote")
	public ResponseEntity<?> vote(@RequestBody VoteRequest request) {
		Profile profile = getAuthenticatedProfile();
		Post post = postService.getPostById(request.postId());
		if (post == null) {
			return ResponseEntity.notFound().build();
		}
		Vote updatedVote = postService.updateVote(profile, post, request.positive());
		return ResponseEntity.ok(new VoteResponse(updatedVote.isPositive()));
	}
	
	record PostListResponse(List<PostResponse> posts) {
	
	}
	
	record PostCreateResponse(UUID postId) {
	
	}
	
	record CreatePostRequest(String subcledditName, String title, String content) {
	
	}
	
	record VoteRequest(UUID postId, boolean positive) {
	
	}
	
	record VoteResponse(boolean positive) {
	
	}
	
}
