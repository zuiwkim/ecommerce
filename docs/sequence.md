# 1️⃣ 잔액 충전 / 조회 API

```mermaid
sequenceDiagram
    participant U as User
    participant S as Service
    participant DB as Database

    note over U,S: (A) 잔액 충전
    U->>S: 1) POST /points/charge (user_id, charge_amount)
    S->>DB: 2) UPDATE user_point SET user_amount = user_amount + ?
    DB-->>S: 3) DB Update Result
    S-->>U: 4) 충전 성공 응답
  
    note over U,S: (B) 잔액 조회
    U->>S: 5) GET /points?user_id=?
    S->>DB: 6) SELECT user_amount FROM user_point WHERE user_id=?
    DB-->>S: 7) 잔액 정보 반환
    S-->>U: 8) 잔액 조회 결과 반환
```

## 설명
- (A) 잔액 충전 : 사용자로부터 충전할 금액을 받아 DB의 user_point.user_amount를 업데이트합니다.
- (B) 잔액 조회 : user_id로 현재 잔액을 조회하여 반환합니다.

---

# 2️⃣ 상품 조회 API

```mermaid
sequenceDiagram
    participant U as User
    participant S as Service
    participant DB as Database

    note over U,S: 상품 목록 또는 특정 상품 정보 조회
    U->>S: 1) GET /items (혹은 GET /items/{id})
    S->>DB: 2) SELECT items_id, item_name, item_price, item_quantity FROM items WHERE del_yn='N'
    DB-->>S: 3) 상품 정보 반환
    S-->>U: 4) 상품 정보 응답
```

## 설명
- 상품 정보 조회 : DB에서 del_yn='N'인 상품들(또는 특정 items_id)을 조회해 상품명, 가격, 재고 등을 반환합니다.
- 조회 시점의 정확한 재고(item_quantity)가 중요합니다.

---

# 3️⃣ 선착순 쿠폰 발급 API & 보유 쿠폰 목록 조회 API

```mermaid
sequenceDiagram
    participant U as User
    participant S as Service
    participant DB as Database

    note over U,S: (A) 선착순 쿠폰 발급
    U->>S: 1) POST /coupons/issue (user_id, coupon_id)
    S->>DB: 2) SELECT coupon_stock FROM coupons WHERE coupon_id=? FOR UPDATE
    alt coupon_stock > 0
        S->>DB: 3) INSERT INTO user_coupons (user_id, coupon_id, ...)
        S->>DB: 4) UPDATE coupons SET coupon_stock = coupon_stock - 1
        S-->>U: 5) 쿠폰 발급 성공 응답
    else coupon_stock <= 0
        S-->>U: 5) 쿠폰 재고 소진 에러
    end

    note over U,S: (B) 보유 쿠폰 조회
    U->>S: 6) GET /coupons?user_id=?
    S->>DB: 7) SELECT * FROM user_coupons WHERE user_id=? AND del_yn='N'
    DB-->>S: 8) 보유 쿠폰 목록 반환
    S-->>U: 9) 쿠폰 목록 반환
```

## 설명
- (A) 선착순 쿠폰 발급 :
  coupons.coupon_stock를 확인해 발급 가능하면 user_coupons에 발급 기록을 남기고, coupon_stock를 차감합니다.
  동시성 이슈를 막기 위해 DB에서 FOR UPDATE나 락을 사용할 수 있습니다.
- (B) 보유 쿠폰 조회 : user_coupons에서 해당 유저가 가진 쿠폰 목록을 조회합니다.

---

# 4️⃣ 주문 / 결제 API

```mermaid
sequenceDiagram
    participant U as User
    participant S as Service
    participant DB as Database
    participant DP as Data Platform

    note over U,S: 주문 / 결제 요청 (상품 여러개, 쿠폰 사용 가능)
    U->>S: 1) POST /orders (user_id, [ {item_id, quantity}... ], coupon_id?)
    S->>DB: 2) (Optional) 쿠폰 유효성 / 사용 여부 확인
    S->>DB: 3) 각 item_id 재고 확인 (items.item_quantity)
    alt 재고 부족
        S-->>U: 재고 부족 에러
    else 재고 충분
        S->>DB: 4) user_point 조회 (잔액 확인)
        alt 잔액 부족
            S-->>U: 잔액 부족 에러
        else 잔액 충분
            S->>S: 5) 결제금액 계산 (쿠폰 할인 적용)
            note over S,DB: 6) DB 트랜잭션 시작
            S->>DB: 7) 재고 차감 (items.item_quantity -= ?)
            S->>DB: 8) 잔액 차감 (user_point.user_amount -= 결제금액)
            S->>DB: 9) 주문 내역 생성 (user_oder)
            S->>DB: 10) 쿠폰 사용 처리 (user_coupons.used_yn='Y')
            DB-->>S: 11) Commit
            S->>DP: 12) 주문 정보 실시간 전송
            DP-->>S: 13) 응답(OK)
            S-->>U: 14) 결제 성공 (주문완료 정보)
        end
    end
```

## 설명
1. 사용자가 주문/결제 요청 시, (상품 목록, 수량, 쿠폰 ID 등)을 전달합니다.
2. 서버는 쿠폰 상태(used_yn, 재고 등)를 확인하고, 상품 재고(items)와 사용자 잔액(user_point)을 확인합니다.
3. 재고와 잔액이 충분하면, 트랜잭션 내에서
- 재고 차감
- 잔액 차감
- 주문 내역(user_oder) 생성
- 쿠폰 사용 여부(user_coupons) 갱신을 처리합니다.
4. 결제 성공 시, **데이터 플랫폼(DP)**에 주문 정보를 전송합니다. (Mock API, 이벤트, 메시지큐 등 다양)

---
# 5️⃣ 상위 상품 조회 API (최근 3일간 판매량 상위 5개)

```mermaid
sequenceDiagram
    participant U as User
    participant S as Service
    participant DB as Database

    Note over U,S: 최근 3일간 많이 팔린 상위 5개 상품 조회
    U->>S: 1) GET /items/popular

    alt 실시간 조회
        S->>DB: 2) SELECT items_id, SUM(quantity) AS total_sold FROM user_order WHERE order_dt >= (NOW() - INTERVAL 3 DAY) GROUP BY items_id ORDER BY total_sold DESC LIMIT 5
        DB-->>S: 3) 판매량 상위 5개
        S-->>U: 4) 상품 목록/판매량 반환
    else 캐싱/배치 활용
        Note over S: 캐싱이나 배치 테이블 미리 계산 (예: popular_items_cache)
        S->>DB: 2') SELECT * FROM popular_items_cache
        DB-->>S: 3') 미리 계산된 상위 5개
        S-->>U: 4') 상품 목록/판매량 반환
    end
```

## 설명
- 실시간 조회 :
  user_oder 테이블에서 최근 3일간의 판매 데이터를 바로 집계하여 가장 많이 팔린 TOP 5를 추출합니다.
- 캐싱 / 배치 방식 :
  일정 간격으로 배치 작업 등으로 미리 집계 테이블(popular_items_cache)을 만들어두고,
  조회 시 이 테이블에서 빠르게 결과를 반환합니다.

```mermaid
erDiagram
    %% ==========================
    %% 테이블 정의 (엔티티) 부분
    %% ==========================
    items {
        BIGINT items_id PK
        VARCHAR(200) item_name
        INT item_price
        INT item_quantity
        DATETIME created_at
        DATETIME updated_at
        CHAR(1) del_yn
    }

    users {
        BIGINT user_id PK
        VARCHAR(200) user_mail
        VARCHAR(200) user_name
        DATETIME created_at
        DATETIME updated_at
        CHAR(1) del_yn
    }

    user_cart {
        BIGINT cart_id PK
        INT quantity
        BIGINT user_id
        BIGINT item_id
        DATETIME created_at
        DATETIME updated_at
        CHAR(1) del_yn
    }

    user_oder {
        BIGINT oder_id PK
        DATETIME oder_dt
        INT order_price
        INT quantity
        BIGINT user_id
        BIGINT item_id
        DATETIME created_at
        DATETIME updated_at
        CHAR(1) del_yn
    }

    user_point {
        BIGINT user_point_id PK
        BIGINT user_amount
        BIGINT user_id
        DATETIME created_at
        DATETIME updated_at
        CHAR(1) del_yn
    }

    coupons {
        BIGINT coupon_id PK
        VARCHAR(200) coupon_name
        VARCHAR(50) discount_type
        INT discount_value
        INT coupon_stock
        DATETIME start_date
        DATETIME end_date
        DATETIME created_at
        DATETIME updated_at
        CHAR(1) del_yn
    }

    user_coupons {
        BIGINT user_coupon_id PK
        BIGINT user_id
        BIGINT coupon_id
        DATETIME issued_at
        CHAR(1) used_yn
        DATETIME used_at
        DATETIME created_at
        DATETIME updated_at
        CHAR(1) del_yn
    }

    %% ==========================
    %% 관계(FK) 정의 부분
    %% ==========================
    user_cart }o--|| users : "FK user_id -> user_id"
    user_cart }o--|| items : "FK item_id -> items_id"

    user_oder }o--|| users : "FK user_id -> user_id"
    user_oder }o--|| items : "FK item_id -> items_id"

    user_point }o--|| users : "FK user_id -> user_id"

    user_coupons }o--|| users : "FK user_id -> user_id"
    user_coupons }o--|| coupons : "FK coupon_id -> coupon_id"
```