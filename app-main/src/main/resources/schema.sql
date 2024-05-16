create table if not exists users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  username varchar(255) UNIQUE,
  password varchar(255),
  email varchar(255) UNIQUE,
  bio text,
  image varchar(511),
  PRIMARY KEY (id)
);

create table if not exists articles (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT not null,
  slug varchar(255) UNIQUE,
  title varchar(255),
  description text,
  body text,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

create table if not exists article_favorites (
  article_id BIGINT not null,
  user_id BIGINT not null,
  primary key(article_id, user_id)
);

create table if not exists follows (
  user_id BIGINT not null,
  follow_id BIGINT not null
);

create table if not exists tags (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name varchar(255) not null,
  PRIMARY KEY (id)
);

create table if not exists article_tags (
  article_id BIGINT not null,
  tag_id BIGINT not null
);

create table if not exists comments (
  id BIGINT NOT NULL AUTO_INCREMENT,
  body text,
  article_id BIGINT not null,
  user_id BIGINT not null,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);
