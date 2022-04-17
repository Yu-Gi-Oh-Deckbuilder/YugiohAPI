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

insert into ban_list(type)
values('TCG'),('OCG'),('GOAT'),('FreeForm');

insert into deck (name, owner_id,ban_list_id)
values ('deck1',2,1),
('deck2',2,2),
('deck3',2,3),
('deck4',2,4);

insert into card_amount(card_id,card_amount)
VALUES(59575539,3),
(46986414,1),
(61370518,2),
(83347294,1),

(59575539,3),
(46986414,1),
(61370518,2),
(83347294,1),

(59575539,3),
(46986414,1),
(61370518,2),
(83347294,1);

insert into deck_cards(deck_id,cards_id)
VALUES (1,1),
(2,2),
(3,3),
(4,4);

insert into wishlist (name,owner_id)
values('wishlist1',2),
('wishlist2',2),
('wishlist3',2),
('wishlist4',2);

insert into wishlist_cards(wishlist_id,cards_id)
VALUES (1,5),
(2,6),
(3,7),
(4,8);


insert into wishlist_shared_users (wishlist_id,	shared_users_id)
values (1, 2),
(1, 3),
(1, 4),
(2, 3),
(4, 2),
(2, 2),
(3, 2);

insert into inventory (owner_id)
values (1),
(2),
(3),
(4);

insert into inventory_cards (inventory_id, cards_id)
values (2, 9),
(2,10),
(2,11),
(2,12);
