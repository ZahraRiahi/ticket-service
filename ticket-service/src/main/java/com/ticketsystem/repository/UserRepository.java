package com.ticketsystem.repository;

import com.ticketsystem.model.domain.User;
import com.ticketsystem.model.projection.SupporterProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Collection<User> findByRole(String role);

    @Query("select u.id as supporterId, count(t.id) as total " +
            "from User u " +
            "left join Ticket t on t.supporter.id=u.id " +
            "and t.status=:status " +
            "where u.role=:role " +
            "group by u.id")
    List<SupporterProjection> findSupporterWithTotalTickets(@Param("role") String role, @Param("status") Boolean status);
}
