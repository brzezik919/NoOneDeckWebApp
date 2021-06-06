package io.github.brzezik919.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepository {
    Page<User> findAllByOrderByNicknameAsc(Pageable pageable);
    List<User> findById(int id);
    User findByLogin(String login);
    User findByNickname(String nickname);
    Page<User> findByNicknameOrderByNicknameAsc(String nickname, Pageable pageable);
    List<User> findByTeam_IdAndStatus(int id, boolean status);
    User save(User user);
}
