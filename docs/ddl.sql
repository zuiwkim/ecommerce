CREATE TABLE `cart` (
                        `cart_id` bigint NOT NULL AUTO_INCREMENT COMMENT '장바구니 일련번호',
                        `quantity` int NOT NULL DEFAULT '0' COMMENT '장바구니에 담은 상품 수량',
                        `user_id` bigint NOT NULL COMMENT '유저 일련번호',
                        `item_id` bigint NOT NULL COMMENT '상품 일련번호',
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                        `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                        `del_yn` tinyint(1) NOT NULL DEFAULT '0' COMMENT '삭제 여부 (0: N, 1: Y)',
                        PRIMARY KEY (`cart_id`),
                        KEY `user_cart_items_items_id_fk` (`item_id`),
                        KEY `user_cart_users_user_id_fk` (`user_id`),
                        CONSTRAINT `user_cart_items_items_id_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`items_id`),
                        CONSTRAINT `user_cart_users_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='장바구니 테이블 입니다.'

CREATE TABLE `coupons` (
                           `coupon_id` bigint NOT NULL AUTO_INCREMENT COMMENT '쿠폰 일련번호',
                           `coupon_name` varchar(200) NOT NULL COMMENT '쿠폰 이름',
                           `discount_type` varchar(50) NOT NULL COMMENT '할인 타입(AMOUNT or RATE 등)',
                           `discount_value` int NOT NULL COMMENT '할인 값(정액 or 정율)',
                           `coupon_stock` int NOT NULL DEFAULT '0' COMMENT '발급 가능(남은) 수량',
                           `start_date` datetime NOT NULL COMMENT '쿠폰 사용 가능 시작일',
                           `end_date` datetime NOT NULL COMMENT '쿠폰 사용 가능 종료일',
                           `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                           `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                           `del_yn` tinyint(1) NOT NULL DEFAULT '0' COMMENT '삭제 여부 (0: N, 1: Y)',
                           PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='쿠폰 기본정보 테이블 입니다.'

CREATE TABLE `items` (
                         `items_id` bigint NOT NULL AUTO_INCREMENT COMMENT '상품 일련번호',
                         `item_name` varchar(200) NOT NULL COMMENT '상품명',
                         `item_price` int NOT NULL COMMENT '상품가격',
                         `item_quantity` int NOT NULL DEFAULT '0' COMMENT '상품 재고 수량',
                         `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                         `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                         `del_yn` tinyint(1) NOT NULL DEFAULT '0' COMMENT '삭제 여부 (0: N, 1: Y)',
                         PRIMARY KEY (`items_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품 테이블 입니다.'

CREATE TABLE `oder` (
                        `oder_id` bigint NOT NULL AUTO_INCREMENT COMMENT '주문 일련번호',
                        `user_id` bigint NOT NULL COMMENT '유저 일련번호',
                        `item_id` bigint NOT NULL COMMENT '상품 일련번호',
                        `order_price` int NOT NULL DEFAULT '0' COMMENT '결제 금액',
                        `quantity` int NOT NULL DEFAULT '0' COMMENT '상품 수량',
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                        `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                        `del_yn` tinyint(1) NOT NULL DEFAULT '0' COMMENT '삭제 여부 (0: N, 1: Y)',
                        PRIMARY KEY (`oder_id`),
                        KEY `user_oder_items_items_id_fk` (`item_id`),
                        KEY `user_oder_users_user_id_fk` (`user_id`),
                        CONSTRAINT `user_oder_items_items_id_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`items_id`),
                        CONSTRAINT `user_oder_users_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문(결제) 테이블 입니다.'

CREATE TABLE `point` (
                         `point_id` bigint NOT NULL AUTO_INCREMENT,
                         `user_id` bigint NOT NULL,
                         `user_amount` bigint NOT NULL DEFAULT '0',
                         `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         `del_yn` tinyint(1) NOT NULL DEFAULT '0',
                         PRIMARY KEY (`point_id`),
                         KEY `point_users_user_id_fk` (`user_id`),
                         CONSTRAINT `point_users_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `user_coupons` (
                                `user_coupon_id` bigint NOT NULL AUTO_INCREMENT COMMENT '유저 쿠폰 일련번호',
                                `user_id` bigint NOT NULL COMMENT '유저 일련번호',
                                `coupon_id` bigint NOT NULL COMMENT '쿠폰 일련번호',
                                `issued_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '쿠폰 발급 일시',
                                `used_yn` tinyint(1) NOT NULL DEFAULT '0' COMMENT '사용 여부 (0: N, 1: Y)',
                                `used_at` datetime DEFAULT NULL COMMENT '쿠폰 사용 일시',
                                `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                                `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                                `del_yn` tinyint(1) NOT NULL DEFAULT '0' COMMENT '삭제 여부 (0: N, 1: Y)',
                                PRIMARY KEY (`user_coupon_id`),
                                KEY `user_coupons_users_user_id_fk` (`user_id`),
                                KEY `user_coupons_coupons_coupon_id_fk` (`coupon_id`),
                                CONSTRAINT `user_coupons_coupons_coupon_id_fk` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`coupon_id`),
                                CONSTRAINT `user_coupons_users_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='사용자별 보유 쿠폰 테이블 입니다.'

CREATE TABLE `users` (
                         `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '유저 일련번호',
                         `user_mail` varchar(255) NOT NULL,
                         `user_name` varchar(255) NOT NULL,
                         `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                         `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                         `del_yn` tinyint(1) NOT NULL DEFAULT '0' COMMENT '삭제 여부 (0: N, 1: Y)',
                         PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='유저 테이블 입니다.'