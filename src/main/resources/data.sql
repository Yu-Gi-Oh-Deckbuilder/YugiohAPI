insert into user_role (name)
values ('user'),
('manager');

insert into users (username, password, first_name, last_name, email, user_role_id)
values ('admin', 'admin', 'John', 'Doe', 'johndoe@email.com', 2),
('user', 'user', 'Jane', 'Doe', 'jane@email.com', 1),
('samuel1', 'password', 'Samuel', 'valencia', 'samuel@email.com', 1),
('abel1', 'password', 'Abel', 'Asres', 'abel@email.com', 1),
('john1', 'password', 'John', 'Le', 'john@email.com', 1),
('nikoloi1', 'password', 'Nikoloi', 'Ellis', 'nikoloi@email.com', 1);

insert into deck (cards, owner_id)
values ('¬í  sr  java.util.HashMap  ÚÁÃ `Ñ   F  loadFactorI  thresholdxp?@      w         sr  java.lang.Integer â ¤÷  8   I  valuexr  java.lang.Number ¬    à    xp    q ~  sq ~      q ~  x
', 2),
('59575539, 3, 46986414, 2, 61370518, 3, 83347294, 1', 3),
('59575539, 3, 46986414, 2, 61370518, 3, 83347294, 1', 4);

insert into inventory (cards, owner_id)
values ('59575539, 3, 46986414, 2, 61370518, 3, 83347294, 1', 5),
('59575539, 3, 46986414, 2, 61370518, 3, 83347294, 1', 6);


insert into wishlist (cards, owner_id)
values ('59575539, 3, 46986414, 2, 61370518, 3, 83347294, 1', 2),
('59575539, 3, 46986414, 2, 61370518, 3, 83347294, 1', 3);

insert into wishlist_shared_users (wishlist_id,	shared_users_id)
values (1, 2),
(1, 3),
(1, 4),
(2, 3);
