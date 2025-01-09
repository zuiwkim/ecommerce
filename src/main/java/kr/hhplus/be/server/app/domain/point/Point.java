package kr.hhplus.be.server.app.domain.point;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "point")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Id
    @Column(name = "point_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_amount", nullable = false)
    private Long userAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "del_yn", nullable = false)
    private Boolean delYn;

    public void chargeUserPoint(Long chargePoint) {
        this.userAmount += chargePoint;
    }
}