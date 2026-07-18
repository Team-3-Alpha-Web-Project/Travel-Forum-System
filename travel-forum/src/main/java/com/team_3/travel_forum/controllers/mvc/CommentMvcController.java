package com.team_3.travel_forum.controllers.mvc;

import com.team_3.travel_forum.helpers.CommentMapper;
import com.team_3.travel_forum.models.Comment;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.models.dtos.CommentRequestDto;
import com.team_3.travel_forum.services.CommentService;
import com.team_3.travel_forum.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/comments")
public class CommentMvcController {

    private final CommentService commentService;
    private final UserService userService;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentMvcController(CommentService commentService, UserService userService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/{id}")
    public String showSingleComment(@PathVariable int id, Model model) {
        Comment comment = commentService.get(id);
        model.addAttribute("comment", comment);
        return "comment-details";
    }

    @GetMapping("/new")
    public String showNewCommentPage(Model model) {
        model.addAttribute("comment", new Comment());
        return "create-comment";
    }

    @PostMapping("/new")
    public String createComment(@Valid @ModelAttribute("comment")CommentRequestDto dto,
                                BindingResult bindingResult,
                                Principal principal) {
        User user = userService.get(principal.getName());

        if (bindingResult.hasErrors()) {
            return "create-comment";
        }

        Comment comment = commentMapper.fromDto(dto);
        commentService.create(comment, user);
        return "redirect:/comments"; //TODO check if it should redirect to a different page
    }

}
