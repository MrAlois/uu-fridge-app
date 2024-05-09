-- insert data into APP_USER table
INSERT INTO PUBLIC.APP_USER(NAME, DEFAULT_LOCATION, DIETARY_RESTRICTIONS, EMAIL, PHONE)
VALUES ('John Smith', 'Location A', 'VEGAN', 'johnsmith@email.com', '1234567890'),
       ('Jane Doe', 'Location B', 'GLUTEN_FREE', 'janedoe@email.com', '2345678901'),
       ('Mary Johnson', 'Location C', 'VEGETARIAN', 'maryjohnson@email.com', '3456789012'),
       ('Robert Brown', 'Location D', 'NONE', 'robertbrown@email.com', '4567890123'),
       ('Emma Wilson', 'Location B', 'DAIRY_FREE', 'emmawilson@email.com', '5678901234');

-- insert data into FOOD_LISTING table
INSERT INTO PUBLIC.FOOD_LISTING(DONOR_ID, SHORT_DESCRIPTION, DESCRIPTION, QUANTITY, EXPIRY_DATE, PICKUP_LOCATION, DIETARY_INFO)
VALUES (1, 'Veg Salad', 'Fresh Vegetable Salad', 10, '2024-12-31 12:00:00', 'Location A', 'VEGAN'),
       (2, 'Wheat Bread', 'Whole Wheat Bread', 20, '2024-11-30 18:00:00', 'Location B', 'GLUTEN_FREE'),
       (3, 'Apple Pie', 'Delicious Apple Pie', 5, '2024-10-31 21:00:00', 'Location C', 'CONTAINS_GLUTEN, CONTAINS_DAIRY'),
       (4, 'Fresh Pasta', 'Freshly Made Bread Pasta', 15, '2024-11-15 12:00:00', 'Location D', 'CONTAINS_GLUTEN'),
       (2, 'Vegan Cookies', 'Vegan Chocolate Cookies', 30, '2024-10-15 18:00:00', 'Location B', 'VEGAN, GLUTEN_FREE');

-- insert data into FOOD_LISTING_CLAIM table
INSERT INTO PUBLIC.FOOD_LISTING_CLAIM(USER_ID, LISTING_ID, STATE)
VALUES (1, 2, 'WAITING'),
       (2, 3, 'ACCEPTED'),
       (3, 1, 'REJECTED'),
       (4, 4, 'WAITING'),
       (5, 5, 'ACCEPTED');

-- insert data into FOOD_LISTING_PHOTO table
-- replace 'BINARY-DATA-HERE' with the actual binary data of the photo
INSERT INTO PUBLIC.FOOD_LISTING_PHOTO(LISTING_ID, DATA)
VALUES (1, 'BINARY-DATA-HERE'),
       (2, 'BINARY-DATA-HERE'),
       (3, 'BINARY-DATA-HERE'),
       (4, 'BINARY-DATA-HERE'),
       (5, 'BINARY-DATA-HERE');