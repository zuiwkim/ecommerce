package kr.hhplus.be.server.app.infrastructure.user;

import kr.hhplus.be.server.app.domain.user.UserRepository;
import kr.hhplus.be.server.app.domain.user.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public Users findById(Long id) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 id의 유저가 존재하지 않습니다."));
    }
}