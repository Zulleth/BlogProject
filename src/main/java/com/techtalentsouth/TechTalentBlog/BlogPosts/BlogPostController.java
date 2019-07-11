package com.techtalentsouth.TechTalentBlog.BlogPosts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class BlogPostController {

	@Autowired
	private BlogPostRepository blogPostRepository;

	private static List<BlogPost> posts = new ArrayList<>();

	@GetMapping(value = "/")
	public String index(BlogPost blogPost, Model model) {
		model.addAttribute("posts", posts);
		return "blogpost/index";
	}

	@PostMapping(value = "/blog_posts/new")
	public String create(BlogPost blogPost, Model model) {
		blogPostRepository.save(blogPost);
		posts.add(blogPost);
		model.addAttribute("id", blogPost.getId());
		model.addAttribute("title", blogPost.getTitle());
		model.addAttribute("author", blogPost.getAuthor());
		model.addAttribute("blogEntry", blogPost.getBlogEntry());
		return "blogpost/result";
	}

	@GetMapping(value = "/blog_posts/new")
	public String newBlog(BlogPost blogPost) {
		return "blogpost/new";
	}
	
	public boolean delete(long id){
        int postIndex = -1;
        for(BlogPost post : posts) {
            if(post.getId() == id) {
                postIndex = posts.indexOf(post);
                continue;
            }
        }
        if(postIndex > -1){
            posts.remove(postIndex);
        }
        return true;
    }
//	Delete URL: localhost:8080/blog_posts/1
    @RequestMapping(value = "/blog_posts/{id}", method = RequestMethod.DELETE)
    public RedirectView deletePostWithId(@PathVariable Long id, BlogPost blogPost) {
    	delete(id);
        blogPostRepository.deleteById(id);
        return new RedirectView("/");
    }
	
}