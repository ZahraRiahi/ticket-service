package com.ticketsystem.service;

import com.ticketsystem.exceptionHandler.RuleException;
import com.ticketsystem.mapper.UserMapper;
import com.ticketsystem.model.domain.User;
import com.ticketsystem.model.dto.ListDTO;
import com.ticketsystem.model.dto.UserDTO;
import com.ticketsystem.model.enumeration.Role;
import com.ticketsystem.model.enumeration.Status;
import com.ticketsystem.model.projection.SupporterProjection;
import com.ticketsystem.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User getOne(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuleException("user not found"));
    }

    public User save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userRepository.save(user);

    }

    public User changeRole(Long userId, String role) {
        User user = getOne(userId);
        user.setRole(role);
        return userRepository.save(user);
    }

    public ListDTO getAll(int page, int size) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, size));
        ListDTO listDTO = new ListDTO();
        List<UserDTO> data = users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
        listDTO.setData(data);
        listDTO.setTotal(users.getTotalElements());

        return listDTO;
    }

    public User saveToken(String username, String token) {
        User user = userRepository.findByUsername(username);
        user.setToken(token);
        return userRepository.save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Collection<User> getByRole(String role) {
        return userRepository.findByRole(role);
    }

    public List<SupporterProjection> getSupporterWithTotalTickets() {
        return userRepository.findSupporterWithTotalTickets(Role.SUPPORTER.getValue(), Status.OPEN.isValue());
    }

}
