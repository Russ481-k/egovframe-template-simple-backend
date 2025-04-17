-- 사용자 관리 테이블
CREATE TABLE IF NOT EXISTS user_role (
    role_id VARCHAR(20) NOT NULL PRIMARY KEY,
    role_name VARCHAR(60) NOT NULL,
    description VARCHAR(200),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS cms_user (
    user_id VARCHAR(20) NOT NULL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    name VARCHAR(60) NOT NULL,
    email VARCHAR(50),
    phone VARCHAR(20),
    role_id VARCHAR(20) NOT NULL,
    status VARCHAR(15) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(20) NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR(20),
    FOREIGN KEY (role_id) REFERENCES user_role(role_id)
);

-- 메뉴 관리 테이블
CREATE TABLE IF NOT EXISTS menu (
    menu_id VARCHAR(20) NOT NULL PRIMARY KEY,
    menu_name VARCHAR(100) NOT NULL,
    menu_type VARCHAR(20) NOT NULL,
    parent_id VARCHAR(20),
    sort_order INT NOT NULL DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(20) NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR(20),
    FOREIGN KEY (parent_id) REFERENCES menu(menu_id)
);

CREATE TABLE IF NOT EXISTS menu_item (
    item_id VARCHAR(20) NOT NULL PRIMARY KEY,
    menu_id VARCHAR(20) NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    item_url VARCHAR(255),
    item_type VARCHAR(20) NOT NULL,
    sort_order INT NOT NULL DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(20) NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR(20),
    FOREIGN KEY (menu_id) REFERENCES menu(menu_id)
);

-- 콘텐츠 관리 테이블
CREATE TABLE IF NOT EXISTS content_category (
    category_id VARCHAR(20) NOT NULL PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    parent_id VARCHAR(20),
    description VARCHAR(200),
    sort_order INT NOT NULL DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(20) NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR(20),
    FOREIGN KEY (parent_id) REFERENCES content_category(category_id)
);

CREATE TABLE IF NOT EXISTS content (
    content_id VARCHAR(20) NOT NULL PRIMARY KEY,
    category_id VARCHAR(20) NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    author VARCHAR(50),
    view_count INT DEFAULT 0,
    is_published BOOLEAN DEFAULT false,
    published_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(20) NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR(20),
    FOREIGN KEY (category_id) REFERENCES content_category(category_id)
);

-- 파일 관리 테이블
CREATE TABLE IF NOT EXISTS file (
    file_id VARCHAR(20) NOT NULL PRIMARY KEY,
    original_name VARCHAR(255) NOT NULL,
    stored_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    file_size BIGINT NOT NULL,
    mime_type VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS file_attachment (
    attachment_id VARCHAR(20) NOT NULL PRIMARY KEY,
    file_id VARCHAR(20) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(20) NOT NULL,
    FOREIGN KEY (file_id) REFERENCES file(file_id)
);

-- 코드 관리 테이블
CREATE TABLE IF NOT EXISTS code_group (
    group_id VARCHAR(20) NOT NULL PRIMARY KEY,
    group_name VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS code (
    code_id VARCHAR(20) NOT NULL PRIMARY KEY,
    group_id VARCHAR(20) NOT NULL,
    code_name VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(20) NOT NULL,
    FOREIGN KEY (group_id) REFERENCES code_group(group_id)
);

CREATE TABLE IF NOT EXISTS code_value (
    value_id VARCHAR(20) NOT NULL PRIMARY KEY,
    code_id VARCHAR(20) NOT NULL,
    value VARCHAR(50) NOT NULL,
    label VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    sort_order INT NOT NULL DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(20) NOT NULL,
    FOREIGN KEY (code_id) REFERENCES code(code_id)
);

-- 초기 데이터 입력
INSERT INTO user_role (role_id, role_name, description, created_by) VALUES
('ROLE_ADMIN', '관리자', '시스템 관리자', 'SYSTEM'),
('ROLE_USER', '사용자', '일반 사용자', 'SYSTEM');

INSERT INTO cms_user (user_id, username, password, name, email, role_id, created_by) VALUES
('ADMIN', 'admin', '$2a$10$X04f6b7eRkGzKjhp7zP1YOZ7dZB4TCE2jQmHlQ1UZQgF0XxJNq9XG', '관리자', 'admin@example.com', 'ROLE_ADMIN', 'SYSTEM'),
('USER', 'user', '$2a$10$X04f6b7eRkGzKjhp7zP1YOZ7dZB4TCE2jQmHlQ1UZQgF0XxJNq9XG', '사용자', 'user@example.com', 'ROLE_USER', 'SYSTEM'); 