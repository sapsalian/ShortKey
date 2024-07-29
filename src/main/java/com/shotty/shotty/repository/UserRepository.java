package com.shotty.shotty.repository;

import com.shotty.shotty.Domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;


    public void save(User user) {
        em.persist(user);
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public User findByEmail(String email) {
        return findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);

    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }
}
