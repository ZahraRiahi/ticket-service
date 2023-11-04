package com.ticketsystem.controller;

import com.ticketsystem.model.domain.Comment;
import com.ticketsystem.model.dto.CommentDTO;
import com.ticketsystem.model.dto.ListDTO;
import com.ticketsystem.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = CommentController.PATH)
@Tag(name = "CommentController", description = "مجموعه وب سرویسهای ایجاد، ویرایش ، حذف و واکشی کامنتها بر اساس شناسه تیکت ")
public class CommentController {
    public static final String PATH = "/comment";
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER','SUPPORTER')")
    @Operation(summary = "ایجاد یا ویرایش کامنت", description = "این متد برای ایجاد یا ویرایش کامنت استفاده می شود")
    public ResponseEntity<String> saveOrUpdate(@RequestBody @Valid CommentDTO request) {
        Comment comment = commentService.saveOrUpdate(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(comment.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/delete/{commentId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "حذف کامنت", description = "این متد برای حذف کامنت استفاده می شود")
    public ResponseEntity<String> delete(@PathVariable Long commentId) {
        commentService.deleteById(commentId);
        return ResponseEntity.ok().body("comment.was.deleted");
    }

    @GetMapping("/list/{ticketId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "واکشی کامنتها براساس شناسه تیکت", description = "این متد برای واکشی کامنتها براساس شناسه تیکت استفاده می شود")
    public ResponseEntity<ListDTO> getAllByTicketId(@PathVariable Long ticketId,
                                                    @RequestParam(defaultValue = "0") int pageNumber,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        ListDTO responseList = commentService.getAllByTicketId(ticketId, pageNumber, pageSize);
        return ResponseEntity.ok().body(responseList);
    }


}
