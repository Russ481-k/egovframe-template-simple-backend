-- 마이그레이션 버전 정보 테이블 생성
CREATE TABLE IF NOT EXISTS flyway_schema_history (
    installed_rank INT NOT NULL PRIMARY KEY,
    version VARCHAR(50),
    description VARCHAR(200) NOT NULL,
    type VARCHAR(20) NOT NULL,
    script VARCHAR(1000) NOT NULL,
    checksum INT,
    installed_by VARCHAR(100) NOT NULL,
    installed_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    execution_time INT NOT NULL,
    success BOOLEAN NOT NULL
);

-- 기존 테이블 삭제 (필요한 경우)
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS LETTNFILEDETAIL;
DROP TABLE IF EXISTS LETTNFILE;
DROP TABLE IF EXISTS LETTNBBS;
DROP TABLE IF EXISTS LETTNBBSUSE;
DROP TABLE IF EXISTS LETTNBBSMASTER;
DROP TABLE IF EXISTS LETTNGNRLMBER;
DROP TABLE IF EXISTS LETTNEMPLYRINFO;
DROP TABLE IF EXISTS LETTCCMMNDETAILCODE;
DROP TABLE IF EXISTS LETTCCMMNCODE;
DROP TABLE IF EXISTS LETTCCMMNCLCODE;

SET FOREIGN_KEY_CHECKS = 1;

-- 새로운 스키마 적용
SOURCE src/main/resources/db/schema.sql; 