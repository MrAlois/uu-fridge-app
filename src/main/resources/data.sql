-- insert data into APP_USER table
INSERT INTO APP_USER(NAME, DEFAULT_LOCATION, ALLERGIC_TO, EMAIL, PHONE)
VALUES ('John Smith', 'Location A', 'NONE', 'johnsmith@email.com', '1234567890'),
       ('Jane Doe', 'Location B', 'NUTS', 'janedoe@email.com', '2345678901'),
       ('Mary Johnson', 'Location C', 'MILK,EGGS', 'maryjohnson@email.com', '3456789012'),
       ('Robert Brown', 'Location D', 'NONE', 'robertbrown@email.com', '4567890123'),
       ('Emma Wilson', 'Location B', 'MILK', 'emmawilson@email.com', '5678901234');

-- insert data into FOOD_LISTING table
INSERT INTO FOOD_LISTING(DONOR_ID, SHORT_DESCRIPTION, DESCRIPTION, QUANTITY, EXPIRY_DATE, PICKUP_LOCATION, ALLERGENS)
VALUES (1, 'Veg Salad', 'The name gives an illusion of something healthy but in reality, it looks like a mossy pond on a rainy day, with a myriad of shades of green that don''t really promise a delightful mélange of flavors. In fact, if you''ve ever smelled a gym sock after a marathon - that’s the salad. I can''t help but imagine that chewing it will be much like chomping through an armful of twigs and leaves.', 10, '2024-12-31 12:00:00', 'Location A', 'MILK,NUTS'),
       (2, 'Wheat Bread', 'It''s one thing to be a health devotee but Whole Wheat Bread throws that affection into the darkest abyss. It''s denser than a neutron star and has a color palette varying between ''muddy puddle brown'' and ''unwashed potato''. It''s a load of ''loaf'' I won''t risk misplacing on my kitchen table, unless I want to mistake it for a brick.', 20, '2024-11-30 18:00:00', 'Location B', 'EGGS'),
       (3, 'Apple Pie', 'This is a truth-stretcher. Biting into it releases a unique combination of flavors - if you like stale autumn leaves. It''s an apple rebellion in your mouth and not the sweet revolution you''d hope for. Its filling has a vibrant ''toothpaste-green'' color of apple that’s determined to dash your expectations against the rocks. The crust''s sogginess sears through your culinary spirit like a damp woolen blanket in a winter''s night.', 5, '2024-10-31 21:00:00', 'Location C', 'NONE'),
       (4, 'Fresh Pasta', 'It looks like a clay artist''s first experiment with spaghetti. The texture is mind-boggling, like gnawing fossilized worms from the Jurassic period. The smell is reminiscent of the hot summer day - of the thriving life in a vegetable patch, only that “life” meaning is more akin to "composting".', 15, '2024-11-15 12:00:00', 'Location D', 'EGGS,SOY'),
       (2, 'Vegan Cookies', 'Their unexciting taupe color works as a camouflage in a bakery. On the taste front, it''s as if someone tried to imagine what ''dirt flavored dessert'' would taste like and then made it worse. Their crunch is akin to stepping on autumn leaves, producing a distinct echo in your mouth that serves as a melancholy memory of what cookies should taste like.', 30, '2024-10-15 18:00:00', 'Location B', 'PEANUTS,WHEAT,EGGS');

-- insert data into FOOD_LISTING_CLAIM table
INSERT INTO FOOD_LISTING_CLAIM(USER_ID, LISTING_ID, STATE)
VALUES (1, 2, 'WAITING'),
       (2, 3, 'ACCEPTED'),
       (3, 1, 'REJECTED'),
       (4, 4, 'WAITING'),
       (5, 5, 'ACCEPTED');
