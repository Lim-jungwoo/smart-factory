-- 초기화: 관계 역순으로 테이블 데이터 삭제
DELETE FROM product_part;
DELETE FROM device_status;
DELETE FROM part;
DELETE FROM product;
DELETE FROM device;

-- Device (설비)
INSERT INTO device (id, name, target_temperature, target_humidity, base_production_count) VALUES
(1, 'Engine Factory', 90, 40, 50),
(2, 'Tire Factory', 25, 50, 200),
(3, 'Door Plant', 22, 55, 100),
(4, 'Glass Works', 24, 45, 80),
(5, 'Battery Lab', 21, 50, 120);


-- Part (부품)
INSERT INTO part (id, name, device_id) VALUES
(1, 'Engine', 1),
(2, 'Tire', 2),
(3, 'Door', 3),
(4, 'Windshield', 4),
(5, 'Battery', 5);

-- Product (최종 상품)
INSERT INTO product (id, name) VALUES
(1, 'Car');

-- ProductPart (부품 조합)
INSERT INTO product_part (id, product_id, part_id, quantity) VALUES
(1, 1, 1, 1), -- Engine x1
(2, 1, 2, 4), -- Tire x4
(3, 1, 3, 4), -- Door x4
(4, 1, 4, 1), -- Windshield x1
(5, 1, 5, 1); -- Battery x1

-- DeviceStatus (설비 상태)
INSERT INTO device_status (id, device_id, temperature, humidity, timestamp) VALUES
(1, 1, 95, 45, CURRENT_TIMESTAMP), -- Engine Factory: 온도/습도 약간 높음
(2, 2, 24, 48, CURRENT_TIMESTAMP), -- Tire Factory: 양호
(3, 3, 27, 60, CURRENT_TIMESTAMP), -- Door Plant: 오차 있음
(4, 4, 30, 55, CURRENT_TIMESTAMP), -- Glass Works: 온도 높음
(5, 5, 20, 49, CURRENT_TIMESTAMP); -- Battery Lab: 정상

-- device_status의 시퀀스를 마지막 id로 리셋
SELECT setval('device_status_seq', (SELECT MAX(id) FROM device_status));
