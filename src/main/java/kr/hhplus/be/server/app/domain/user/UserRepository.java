package kr.hhplus.be.server.app.domain.user;

public interface UserRepository {
    Users findById(Long id);
}
