package com.ticketsystem.service;

import com.ticketsystem.exceptionHandler.RuleException;
import com.ticketsystem.mapper.CommentMapper;
import com.ticketsystem.model.domain.Comment;
import com.ticketsystem.model.domain.Ticket;
import com.ticketsystem.model.dto.CommentDTO;
import com.ticketsystem.model.dto.ListDTO;
import com.ticketsystem.model.enumeration.Status;
import com.ticketsystem.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final TicketService ticketService;
    private final CommentMapper commentMapper;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository,
                          TicketService ticketService,
                          CommentMapper commentMapper,
                          UserService userService) {
        this.commentRepository = commentRepository;
        this.ticketService = ticketService;
        this.commentMapper = commentMapper;
        this.userService = userService;
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment saveOrUpdate(CommentDTO commentDTO) {
        if (commentDTO.getId() != null) {
            Comment comment = getOne(commentDTO.getId());
            comment.setBody(commentDTO.getBody());
            comment.setCreator(userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
            return save(comment);
        }
        Ticket ticket = ticketService.getOne(commentDTO.getTicketId());
        if (ticket.getStatus().equals(Status.OPEN.isValue())) {
            Comment comment = commentMapper.toEntity(commentDTO);

            return commentRepository.save(comment);
        } else {
            throw new RuleException("ticket status is not open");
        }

    }


    public Comment getOne(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuleException("comment not found"));
    }

    public void deleteById(Long id) {
        Comment comment = getOne(id);
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (comment.getCreator().getUsername().equals(currentUsername))
            commentRepository.deleteById(id);
        else
            throw new RuleException("comment with given id dose not belong to current user");
    }

    public ListDTO getAllByTicketId(Long ticketId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        ListDTO listDTO = new ListDTO();
        Page<Comment> comments = commentRepository.findAllByTicketId(ticketId, pageable);
        List<CommentDTO> data = comments.stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());

        listDTO.setData(data);
        listDTO.setTotal(comments.getTotalElements());

        return listDTO;
    }
}
