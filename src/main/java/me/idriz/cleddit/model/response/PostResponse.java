package me.idriz.cleddit.model.response;

import java.util.List;
import java.util.UUID;
import me.idriz.cleddit.model.Post;
import me.idriz.cleddit.model.Vote;

public record PostResponse(
		UUID postId,
		Boolean vote,
		String title,
		String content,
		String posterName,
		String subcledditName,
		UUID posterId,
		List<CommentResponse> initialComments,
		int upvotes,
		int downvotes
) {
	
	public static PostResponse fromPost(Post post, Vote vote, List<CommentResponse> initialComments) {
		return new PostResponse(
				post.getId(),
				vote == null ? null : vote.isPositive(),
				post.getTitle(),
				post.getContent(),
				post.getPoster().getUsername(),
				post.getSubcleddit().getName(),
				post.getPoster().getId(),
				initialComments,
				post.getUpvotes(),
				post.getDownvotes()
		);
	}
}
