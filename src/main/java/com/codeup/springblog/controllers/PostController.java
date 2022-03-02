package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PostController {
    private PostRepository postRepo;
    private UserRepository userRepo;

    private final EmailService emailService;

    public PostController(PostRepository postRepo, UserRepository userRepo, EmailService emailService) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.emailService = emailService;
    }

    @GetMapping("/posts")
    public String postsIndex(Model model) {
        model.addAttribute("posts", postRepo.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String postsIndividualPage(@PathVariable long id, Model model) {
        Post post = postRepo.getById(id);
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String postsCreateForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String postsCreateSubmit(@ModelAttribute Post post) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setPoster(user);
        emailService.prepareAndSend(post, post.getTitle(), post.getBody());
        return savePost(post);
    }

    @GetMapping("/posts/{id}/edit")
    public String postsEditForm(@PathVariable long id, Model model) {
        Post post = postRepo.getById(id);
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String postsEditSubmit(@PathVariable long id, @ModelAttribute Post post) {
        return savePost(post);
    }

    @PostMapping("/posts/{id}/delete")
    public String delete(@PathVariable long id) {
        Post post = postRepo.getById(id);
        postRepo.delete(post);
        return "redirect:/posts";
    }

    private String savePost(Post post) {
        postRepo.save(post);
        return "redirect:/posts";
    }
}
