package me.idriz.cleddit.service;

import java.util.List;
import java.util.UUID;
import me.idriz.cleddit.model.Post;
import me.idriz.cleddit.model.Profile;
import me.idriz.cleddit.model.Subcleddit;
import me.idriz.cleddit.model.Vote;

public interface PostService {
	
	Post createPost(Profile poster, Subcleddit subcleddit, String title, String content);
	
	boolean deletePost(UUID postId);
	
	Post getPostById(UUID postId);
	
	List<Post> getPosts(int page);
	
	List<Post> getPosts(String subcledditName, int page);
	
	Vote getVoteByProfile(Profile profile, Post post);
	
	Vote updateVote(Profile profile, Post post, boolean positive);
}
