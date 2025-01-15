INSERT INTO beers.styles (style_id, name)
VALUES (1, 'Pilsner');
INSERT INTO beers.styles (style_id, name)
VALUES (2, 'Pale ale');
INSERT INTO beers.styles (style_id, name)
VALUES (3, 'Red Ale');
INSERT INTO beers.styles (style_id, name)
VALUES (4, 'Porter');
INSERT INTO beers.styles (style_id, name)
VALUES (5, 'Stout');
INSERT INTO beers.styles (style_id, name)
VALUES (6, 'Indian pale ale');
INSERT INTO beers.styles (style_id, name)
VALUES (7, 'Weissbier');
INSERT INTO beers.styles (style_id, name)
VALUES (8, 'Special ale');

INSERT INTO beers.users (user_id, username, password, first_name, last_name, email, isAdmin)
VALUES (1, 'todor', 'pass1', 'Todor', 'Angelov', 'todor@example.com', false);
INSERT INTO beers.users (user_id, username, password, first_name, last_name, email, isAdmin)
VALUES (2, 'vladi', 'pass2', 'Vladi', 'Venkov', 'vladi@example.com', false);
INSERT INTO beers.users (user_id, username, password, first_name, last_name, email, isAdmin)
VALUES (3, 'pesho', 'pass3', 'Petar', 'Raykov', 'pesho@example.com', true);

INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (1, 'Kamenitza', 4.5, 8, 3);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (2, 'Tuborg', 4.5, 5, 3);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (3, 'Shumensko', 4.5, 3, 3);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (4, 'Guiness', 4.5, 6, 3);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (5, 'Astika', 4.5, 8, 3);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (6, 'Bud Light', 4.5, 2, 3);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (7, 'Corona', 4.5, 3, 3);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (8, 'Pirinsko', 4.5, 3, 3);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (9, 'Marrr', 4.5, 3, 3);
INSERT INTO beers.beers (beer_id, name, abv, style_id, created_by)
VALUES (10, 'Karrr', 4.5, 3, 3);

INSERT INTO beers.users_beers (user_id, beer_id, drunk)
VALUES (1, 1, 0);
INSERT INTO beers.users_beers (user_id, beer_id, drunk)
VALUES (1, 2, 0);
INSERT INTO beers.users_beers (user_id, beer_id, drunk)
VALUES (1, 3, 1);
INSERT INTO beers.users_beers (user_id, beer_id, drunk)
VALUES (1, 5, 1);
INSERT INTO beers.users_beers (user_id, beer_id, drunk)
VALUES (2, 3, 0);
INSERT INTO beers.users_beers (user_id, beer_id, drunk)
VALUES (2, 4, 0);
INSERT INTO beers.users_beers (user_id, beer_id, drunk)
VALUES (3, 3, 1);
INSERT INTO beers.users_beers (user_id, beer_id, drunk)
VALUES (3, 5, 1);
INSERT INTO beers.users_beers (user_id, beer_id, drunk)
VALUES (3, 7, 1);
INSERT INTO beers.users_beers (user_id, beer_id, drunk)
VALUES (3, 8, 1);
INSERT INTO beers.users_beers (user_id, beer_id, drunk)
VALUES (3, 10, 1);