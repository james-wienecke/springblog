package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PostController {
    private PostRepository postRepo;

    public PostController(PostRepository postRepo) {
        this.postRepo = postRepo;
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
    public String postsCreateForm() {
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String postsCreateSubmit(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body
    ) {
        Post post = new Post(title, body);
        postRepo.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String postsEditForm(@PathVariable long id, Model model) {
        Post post = postRepo.getById(id);
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String postsEditSubmit(@PathVariable long id,
                                  @RequestParam(name = "title") String title,
                                  @RequestParam(name = "body") String body) {
        Post post = postRepo.getById(id);

        post.setTitle(title);
        post.setBody(body);

        postRepo.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{id}/delete")
    public String delete(@PathVariable long id) {
        Post post = postRepo.getById(id);
        postRepo.delete(post);
        return "redirect:/posts";
    }
}
