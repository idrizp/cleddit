package me.idriz.cleddit.service.sql;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import me.idriz.cleddit.model.Post;
import me.idriz.cleddit.model.Profile;
import me.idriz.cleddit.model.Subcleddit;
import me.idriz.cleddit.model.Vote;
import me.idriz.cleddit.repository.sql.PostRepository;
import me.idriz.cleddit.repository.sql.VoteRepository;
import me.idriz.cleddit.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SqlPostService implements PostService {
	
	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	
	public SqlPostService(VoteRepository voteRepository, PostRepository postRepository) {
		this.voteRepository = voteRepository;
		this.postRepository = postRepository;
	}
	
	@Override
	public Post createPost(Profile poster, Subcleddit subcleddit, String title, String content) {
		Post post = new Post(title, content, poster, new HashSet<>(), new HashSet<>(), subcleddit);
		post.getVotes().add(new Vote(poster, post, true));
		return postRepository.save(post);
	}
	
	@Override
	public boolean deletePost(UUID postId) {
		return postRepository.deletePostById(postId) != null;
	}
	
	@Override
	public Post getPostById(UUID postId) {
		return postRepository.findById(postId).orElse(null);
	}
	
	@Override
	public List<Post> getPosts(int page) {
		return postRepository.findAll(Pageable.ofSize(10).withPage(page)).getContent();
	}
	
	@Override
	public List<Post> getPosts(String subcledditName, int page) {
		return postRepository.findBySubcledditName(subcledditName, Pageable.ofSize(10).withPage(page)).getContent();
	}
	
	@Override
	public Vote getVoteByProfile(Profile profile, Post post) {
		return voteRepository.findByProfileIdAndPostId(profile.getId(), post.getId());
	}
	
	@Override
	public Vote updateVote(Profile profile, Post post, boolean positive) {
		Vote vote = voteRepository.findByProfileIdAndPostId(profile.getId(), post.getId());
		if (vote != null) {
			vote.setPositive(positive);
		} else {
			vote = new Vote(profile, post, positive);
		}
		voteRepository.save(vote);
		return vote;
	}
}
