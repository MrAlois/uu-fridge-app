-- insert data into APP_USER table
INSERT INTO APP_USER(NAME, DEFAULT_LOCATION, DEFAULT_LAT, DEFAULT_LNG, ALLERGIC_TO, EMAIL, PHONE)
VALUES ('John Smith', 'Old Town Square 604/14, 110 00 Prague 1 - Old Town, Czechia', 50.0874654, 14.4212535, null, 'johnsmith@email.com', '1234567890'),
       ('Jane Doe', 'Havlíčkovo náměstí 552/1, 284 01 Kutná Hora-Vnitřní Město, Czechia ', 49.9486561, 15.2681123, 'NUTS', 'janedoe@email.com', '2345678901'),
       ('Petr Frolík', 'Kladno, 27252 Kladno, Central Bohemian Region, Czechia', 50.1478497, 14.1025379, 'MILK,EGGS', 'maryjohnson@email.com', '3456789012'),
       ('Robert Homeless', null, null, null, null, 'robert@addressless.com', '4567890123'),
       ('Emma Wilson', 'Old Town Square 1/4, 110 00 Prague 1 - Old Town, Czechia ', 50.0874654, 14.4212535, 'MILK', 'emmawilson@email.com', '5678901234'),
       ('Dave Lister', 'Čsl. armády 2125/34, 43401 Most, Czechia', 50.5105642, 13.6363306, 'MILK', 'lister@email.com', '9876543210'),
       ('Arnold Rimmer', 'Výstavní 80/99, 703 00 Ostrava - Vítkovice, Czechia ', 49.8172847, 18.2722797, null, 'rimmer@email.com', '8765432109'),
       ('Kryten', '9. května 1197, 73581 Bohumín - Nový Bohumín, Czechia ', 49.8992572,18.3504125, null, 'kryten@email.com', '7654321098'),
       ('Cat', 'Dolní náměstí 21/44, 77900 Olomouc, Czechia ', 49.5922931, 17.2511658, 'FISH', 'cat@email.com', '6543210987');

-- insert data into FOOD_LISTING table
INSERT INTO FOOD_LISTING(DONOR_ID, SHORT_DESCRIPTION, DESCRIPTION, EXPIRY_DATE, PICKUP_LOCATION, PICKUP_LAT, PICKUP_LNG, ALLERGENS)
VALUES(1, 'Veg Salad', 'The name gives an illusion of something healthy but in reality, it looks like a mossy pond on a rainy day, with a myriad of shades of green that don''t really promise a delightful mélange of flavors. In fact, if you''ve ever smelled a gym sock after a marathon - that’s the salad. I can''t help but imagine that chewing it will be much like chomping through an armful of twigs and leaves.', '2024-12-31 12:00:00', 'Havlíčkovo náměstí 552/1, 284 01 Kutná Hora-Vnitřní Město, Czechia ', 49.9486561, 15.2681123, 'MILK,NUTS'),
      (2, 'Wheat Bread', 'It''s one thing to be a health devotee but Whole Wheat Bread throws that affection into the darkest abyss. It''s denser than a neutron star and has a color palette varying between ''muddy puddle brown'' and ''unwashed potato''. It''s a load of ''loaf'' I won''t risk misplacing on my kitchen table, unless I want to mistake it for a brick.', '2024-11-30 18:00:00', 'Havlíčkovo náměstí 552/1, 284 01 Kutná Hora-Vnitřní Město, Czechia ', 49.9486561, 15.2681123, 'EGGS'),
      (3, 'Apple Pie', 'This is a truth-stretcher. Biting into it releases a unique combination of flavors - if you like stale autumn leaves. It''s an apple rebellion in your mouth and not the sweet revolution you''d hope for. Its filling has a vibrant ''toothpaste-green'' color of apple that’s determined to dash your expectations against the rocks. The crust''s sogginess sears through your culinary spirit like a damp woolen blanket in a winter''s night.', '2024-10-31 21:00:00','Old Town Square 604/14, 110 00 Prague 1 - Old Town, Czechia', 50.0874654, 14.4212535, 'NUTS'),
      (4, 'Fresh Pasta', 'It looks like a clay artist''s first experiment with spaghetti. The texture is mind-boggling, like gnawing fossilized worms from the Jurassic period. The smell is reminiscent of the hot summer day - of the thriving life in a vegetable patch, only that “life” meaning is more akin to "composting".', '2024-11-15 12:00:00', 'Kladno, 27252 Kladno, Central Bohemian Region, Czechia', 50.1478497, 14.1025379, 'EGGS'),
      (2, 'Vegan Cookies', 'Their unexciting taupe color works as a camouflage in a bakery. On the taste front, it''s as if someone tried to imagine what ''dirt flavored dessert'' would taste like and then made it worse. Their crunch is akin to stepping on autumn leaves, producing a distinct echo in your mouth that serves as a melancholy memory of what cookies should taste like.', '2024-10-15 18:00:00', 'Výstavní 80/99, 703 00 Ostrava - Vítkovice, Czechia ', 49.8172847, 18.2722797, 'WHEAT'),
      (6, 'Quantum Soup', 'Quantum soup, best served both hot and cold simultaneously. Yes, you read right. It''s Schrödinger''s soup, sustaining and confusing in equal measure - so usually, by the time you''ve finished the bowl, you''re not sure if you''ve eaten or just hallucinated a meal. Flavor-wise, it''s right around the region of spicy lemon with a hint of uncertainty principle.', '2024-01-31 11:00:00', 'Dolní náměstí 21/44, 77900 Olomouc, Czechia ', 49.5922931, 17.2511658, 'FISH'),
      (6, 'Light Bee Honey', 'Buzzing straight from a hard-light hive, this honey throws defracting flavor pulses onto your unsuspecting palate. Ethereal waves of this golden nectar teases your spirits with a lively dance of saccharine photon clusters. It''s a highly recommended companion for a late-night bout of existential dread.', '2024-02-28 09:30:00', 'Dolní náměstí 21/44, 77900 Olomouc, Czechia ', 49.5922931, 17.2511658, null),
      (7, 'Android Pâté', 'Despite the misleading name, it''s entirely free of circuitry...', '2024-12-31 13:00:00', '9. května 1197, 73581 Bohumín - Nový Bohumín, Czechia ', 49.8992572,18.3504125, 'NUTS'),
      (8, 'Space Weevil', 'There''s no escaping the space weevils even in your pasta...', '2024-11-30 18:00:00', 'Čsl. armády 2125/34, 43401 Most, Czechia', 50.5105642, 13.6363306, 'WHEAT');


-- insert data into FOOD_LISTING_CLAIM table
INSERT INTO FOOD_LISTING_CLAIM(USER_ID, LISTING_ID, STATE)
VALUES
    (3, 1, 'WAITING'),
    (4, 5, 'WAITING'),
    (8, 6, 'ACCEPTED'),
    (9, 7, 'CLAIMED'),
    (3, 8, 'WAITING');
