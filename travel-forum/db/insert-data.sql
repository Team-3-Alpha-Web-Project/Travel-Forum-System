insert into users
(first_name, last_name, email, username, password, role, is_blocked, profile_photo_url)
values
    ('Spasimira', 'Genova', 'spasimira@traveltalk.com',
     'spasimiragenova', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_ADMIN', 0, null),

    ('Kalina', 'Stefanova', 'kalina@traveltalk.com',
     'kalinastefanova', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_ADMIN', 0, null),

    ('Alexandra', 'Alexova', 'alexandra@traveltalk.com',
     'alexandraalexova', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_ADMIN', 0, null),

    ('Daniel', 'Georgiev', 'daniel.georgiev@example.com',
     'danieltravels', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Elena', 'Ivanova', 'elena.ivanova@example.com',
     'elenawanders', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Sofia', 'Dimitrova', 'sofia.dimitrova@example.com',
     'sofiaabroad', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Viktor', 'Nikolov', 'viktor.nikolov@example.com',
     'viktortrip', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 1, null),

    ('Olivia', 'Carter', 'olivia.carter@example.com',
     'oliviatravels', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Ethan', 'Walker', 'ethan.walker@example.com',
     'ethanexplores', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Amelia', 'Harris', 'amelia.harris@example.com',
     'ameliaroutes', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Lucas', 'Martin', 'lucas.martin@example.com',
     'lucasontheroad', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Clara', 'Fischer', 'clara.fischer@example.com',
     'clarawanders', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Leon', 'Wagner', 'leon.wagner@example.com',
     'leonjourneys', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Emily', 'Wilson', 'emily.wilson@example.com',
     'emilycitybreaks', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Mateo', 'Garcia', 'mateo.garcia@example.com',
     'mateobudgettrip', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 1, null),

    ('Lucia', 'Romano', 'lucia.romano@example.com',
     'luciaeats', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Alice', 'Moretti', 'alice.moretti@example.com',
     'alicehiddenplaces', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Hugo', 'Laurent', 'hugo.laurent@example.com',
     'hugoweekends', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Nora', 'Jensen', 'nora.jensen@example.com',
     'norasolotravel', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null),

    ('Sarah', 'Andersson', 'sarah.andersson@example.com',
     'sarahcarryon', '{bcrypt}$2a$10$RETKEa8ipLASGDeoVuP0GejzTpb0eJeIrXW4JD5/YrUKMjt/w4jvS', 'ROLE_USER', 0, null);


insert into posts
    (title, content, user_id, created_at)
values
    (
        'Is London public transport always this hot in summer?',
        'I was genuinely shocked by the heat on parts of the London Underground. Some trains felt as if there was no cooling at all, especially when they were crowded. Is this normal every summer, and which lines are actually better during a heatwave?',
        1,
        '2026-06-25 18:20:00'
    ),

    (
        'Zell am See as a base for a ski holiday in Austria',
        'We stayed in Zell am See and I loved having several very different ski options around us. Schmittenhöhe felt like the easiest all-round choice for mixed abilities, Kitzsteinhorn was more exciting for stronger skiers and snow lovers, and the wider Ski ALPIN CARD area made it possible to add the Skicircus for more variety. I would absolutely use Zell am See as a base again for a group where everyone skis at a different level.',
        3,
        '2026-01-29 11:45:00'
    ),

    (
        'Is Cologne Cathedral worth planning a trip around?',
        'I expected Cologne Cathedral to be one of those famous landmarks that looks better in photos, but seeing it in person completely changed my mind. The scale and detail are incredible. I would not rush through it just because it is next to the station. Has anyone also done a guided visit or the tower?',
        2,
        '2024-12-08 09:15:00'
    ),

    (
        'Two unusual London museums worth leaving the main route',
        'If you want a break from the biggest London attractions, Sir John Soane''s Museum and the Grant Museum of Zoology are two very different stops. They are smaller, unusual and much easier to fit into a relaxed day than another huge museum. Which lesser-known London museum surprised you most?',
        17,
        '2026-03-08 16:40:00'
    ),

    (
        'Where to eat traditional Roman food without chasing trends',
        'I keep seeing the same viral restaurants in Rome, but I would rather eat somewhere focused on classic Roman dishes. Testaccio and Trastevere seem much more interesting for trattorias and traditional food. I am especially looking for great carbonara, artichokes and coda alla vaccinara. Any personal favourites?',
        16,
        '2026-03-15 20:10:00'
    ),

    (
        'A Lisbon dinner with traditional food and live Fado',
        'For a Lisbon evening I would love to combine Portuguese food with live Fado instead of booking dinner and a separate show. Fama de Alfama looks like the kind of place I mean. Has anyone been there recently, and is it better to reserve well in advance?',
        16,
        '2026-03-24 19:30:00'
    ),

    (
        'Vienna food tip: try a proper Beisl, not only schnitzel',
        'One of my favourite things in Vienna was discovering the idea of a traditional Beisl rather than only searching for the most famous schnitzel restaurant. The atmosphere felt much more local and relaxed. Which Viennese Beisl would you recommend for a first visit?',
        12,
        '2026-04-01 13:05:00'
    ),

    (
        'Best weekend trip from Sofia without taking extra leave',
        'I am looking for a Friday-evening to Sunday-night trip from Sofia that does not require taking a day off work. I care more about good food, walkable streets and an easy connection than ticking off ten attractions. Which city would you choose?',
        18,
        '2026-04-07 08:50:00'
    ),

    (
        'How I pack for five days with only a cabin bag',
        'My rule is to build every outfit around two pairs of shoes, repeat neutral layers and keep toiletries in travel sizes from the start. I also leave a little empty space because I always bring something home. What is the one thing you stopped packing once you learned to travel light?',
        20,
        '2026-04-14 17:25:00'
    ),

    (
        'Booking or Airbnb for a short city break?',
        'For two or three nights I usually compare the final price, location, check-in rules and cancellation policy before I care whether the place is a hotel or apartment. Airbnb can feel more spacious, but a hotel is often easier for late arrival and luggage storage. What decides it for you?',
        10,
        '2026-04-22 21:10:00'
    ),

    (
        'I nearly booked the wrong airport for my London trip',
        'Cheap flights to London can look like a bargain until you check how far the airport is from where you are staying. I now calculate the airport transfer time and cost before booking the flight itself. Has an airport transfer ever completely changed your travel budget?',
        4,
        '2026-05-01 10:35:00'
    ),

    (
        'Public transport passes can save your city break budget',
        'I used to buy single tickets without thinking, then realised that daily caps and multi-day passes can make a huge difference in some cities. Now I check the official transport options before I arrive. Which city has the easiest public transport system for visitors?',
        13,
        '2026-05-09 14:20:00'
    ),

    (
        'A simple two-day Amsterdam plan without rushing',
        'My ideal Amsterdam weekend would be one museum, a long walk through the canal streets and Jordaan, time for coffee, and an evening without another scheduled attraction. I think the city is much better when every hour is not planned. What would you keep in a two-day itinerary?',
        5,
        '2026-05-17 12:00:00'
    ),

    (
        'Solo travel safety habits that do not ruin the trip',
        'I share my accommodation details with one person, keep a backup bank card separately and avoid arriving somewhere unfamiliar with an empty phone battery. I do not want solo travel to feel paranoid, just prepared. What small safety habit has actually helped you?',
        19,
        '2026-05-26 18:55:00'
    ),

    (
        'Teufelsberg is the Berlin stop I did not expect to love',
        'I went to Berlin expecting the classic central sights, but Teufelsberg was the place that stayed in my head. The former listening station, the hill and the unusual atmosphere felt completely different from the main tourist route. What is your favourite unexpected place in Berlin?',
        9,
        '2026-06-03 15:45:00'
    ),

    (
        'Why I prefer staying outside the historic city centre',
        'I have started choosing accommodation one or two public transport stops outside the most central area. It is often quieter, I find more normal neighbourhood restaurants and I still reach the main sights quickly. Do you pay extra to stay central or choose better value slightly further out?',
        11,
        '2026-06-11 09:30:00'
    ),

    (
        'Paris in winter was much better than I expected',
        'I worried that winter would make Paris feel grey and disappointing, but shorter queues and slower days actually suited the trip. I spent more time in museums, cafés and neighbourhood walks instead of trying to stay outside all day. Would you choose Paris in winter again?',
        14,
        '2026-06-18 19:40:00'
    ),

    (
        'Check-in rules are the first thing I read before booking',
        'After almost booking a place with a check-in window that ended before my flight landed, I started reading arrival rules before reviews. Late check-in, key collection and luggage storage can matter more than a pretty room for a short trip. What booking detail do you always check first?',
        6,
        '2026-06-24 07:50:00'
    ),

    (
        'Kitzsteinhorn or Schmittenhöhe for mixed ski levels?',
        'We are planning an Austria ski trip with beginners, confident intermediate skiers and two people who want freeride or park options. Schmittenhöhe sounds like the better all-round mountain, while Kitzsteinhorn has glacier skiing, freeride routes and snow parks. Would you split the group by day or keep everyone on one mountain?',
        12,
        '2026-06-30 17:15:00'
    ),

    (
        'A Roman restaurant I would save for classic dishes',
        'Giggetto al Portico d''Ottavia caught my attention because the menu focuses on traditional Roman specialities such as artichokes alla giudia, carbonara and coda alla vaccinara. I would rather book one memorable traditional meal than chase five viral places. Has anyone eaten there recently?',
        16,
        '2026-07-04 20:30:00'
    );


insert into comments
(content, created_at, post_id, user_id)
values
    (
        'You are definitely not the only one. The Central line was the worst part of my London trip during warm weather.',
        '2026-02-14 19:05:00',
        1,
        4
    ),

    (
        'I had exactly the same reaction. Coming from cities with air-conditioned metro trains, the heat was honestly surprising.',
        '2026-02-14 20:30:00',
        1,
        8
    ),

    (
        'The buses felt much better to me than some Tube lines, although traffic made the journey much slower.',
        '2026-02-15 09:10:00',
        1,
        13
    ),

    (
        'For mixed abilities I would also choose Zell am See. It is much easier when beginners and stronger skiers do not have to stay together all day.',
        '2026-02-20 13:15:00',
        2,
        12
    ),

    (
        'We stayed near the centre of Zell am See and used different ski areas during the week. The variety was the best part of the trip.',
        '2026-02-20 16:40:00',
        2,
        18
    ),

    (
        'Would you recommend staying in Zell am See without a car? We are trying to decide between renting one and relying on ski buses.',
        '2026-02-21 10:25:00',
        2,
        5
    ),

    (
        'The cathedral was probably my favourite sight in Cologne. I also recommend walking along the Rhine after visiting it.',
        '2026-03-02 11:50:00',
        3,
        11
    ),

    (
        'The tower is worth it if you are comfortable with stairs. I found the climb exhausting but the experience was memorable.',
        '2026-03-02 14:20:00',
        3,
        17
    ),

    (
        'Sir John Soane''s Museum was one of my favourite accidental discoveries in London. It feels completely different from the large museums.',
        '2026-03-08 18:10:00',
        4,
        8
    ),

    (
        'I would add the Hunterian Museum to the list for people who enjoy unusual collections.',
        '2026-03-09 09:45:00',
        4,
        9
    ),

    (
        'Testaccio was my favourite area for food. It felt less overwhelming than searching around the busiest streets in the centre.',
        '2026-03-15 21:20:00',
        5,
        10
    ),

    (
        'For carbonara I always prefer smaller traditional restaurants with a short menu. The viral places usually have enormous queues.',
        '2026-03-16 12:35:00',
        5,
        14
    ),

    (
        'Reserve the Fado dinner in advance. We tried to find a place at the last minute and almost everything we liked was full.',
        '2026-03-24 21:00:00',
        6,
        5
    ),

    (
        'A traditional Beisl was also one of my best Vienna experiences. The atmosphere matters almost as much as the food.',
        '2026-04-01 15:40:00',
        7,
        16
    ),

    (
        'I would choose Thessaloniki for a short trip from Sofia. Great food, easy walks and you do not need a complicated itinerary.',
        '2026-04-07 10:15:00',
        8,
        3
    ),

    (
        'Belgrade is another good option. I did a weekend there and never felt that I needed an extra day off work.',
        '2026-04-07 13:25:00',
        8,
        11
    ),

    (
        'The thing I stopped packing was a different outfit for every single day. Repeating trousers saves so much space.',
        '2026-04-14 19:50:00',
        9,
        19
    ),

    (
        'For me the biggest difference is luggage storage. A hotel is much easier when your flight leaves late in the evening.',
        '2026-04-22 22:35:00',
        10,
        20
    ),

    (
        'I made this mistake with Stansted. The flight was cheap but the transfer added more money and time than I expected.',
        '2026-05-01 12:05:00',
        11,
        3
    ),

    (
        'This is why I always check the airport code before booking now. Some cities have several airports very far from the centre.',
        '2026-05-01 16:30:00',
        11,
        18
    ),

    (
        'Amsterdam is exactly the kind of city where overplanning ruins the experience. Walking around the canals was my favourite part.',
        '2026-05-17 14:40:00',
        13,
        4
    ),

    (
        'A charged power bank is probably my number one solo travel habit. It sounds simple until you actually need directions late at night.',
        '2026-05-26 20:15:00',
        14,
        20
    ),

    (
        'Teufelsberg surprised me too. It was so different from the museums and historic buildings I had planned before the trip.',
        '2026-06-03 18:05:00',
        15,
        5
    ),

    (
        'For a mixed group I would split by mountain for part of the day and meet later. Strong skiers get more freedom and beginners feel less pressured.',
        '2026-06-30 19:45:00',
        19,
        3
    );

insert into likes
(created_at, user_id, post_id)
values
    -- Post 1: London public transport
    ('2026-02-14 19:20:00', 4, 1),
    ('2026-02-14 20:45:00', 5, 1),
    ('2026-02-15 08:30:00', 8, 1),
    ('2026-02-15 10:15:00', 13, 1),
    ('2026-02-16 17:40:00', 20, 1),

    -- Post 2: Zell am See ski holiday
    ('2026-02-20 12:10:00', 1, 2),
    ('2026-02-20 13:25:00', 2, 2),
    ('2026-02-20 15:15:00', 5, 2),
    ('2026-02-20 17:00:00', 12, 2),
    ('2026-02-21 09:30:00', 18, 2),
    ('2026-02-22 14:20:00', 19, 2),

    -- Post 3: Cologne Cathedral
    ('2026-03-02 10:00:00', 3, 3),
    ('2026-03-02 12:10:00', 11, 3),
    ('2026-03-02 15:35:00', 17, 3),
    ('2026-03-03 09:00:00', 8, 3),

    -- Post 4: London museums
    ('2026-03-08 17:05:00', 3, 4),
    ('2026-03-08 18:30:00', 8, 4),
    ('2026-03-09 10:10:00', 9, 4),

    -- Post 5: Roman food
    ('2026-03-15 20:45:00', 10, 5),
    ('2026-03-15 21:40:00', 14, 5),
    ('2026-03-16 09:20:00', 16, 5),

    -- Post 6: Lisbon Fado
    ('2026-03-24 20:00:00', 5, 6),
    ('2026-03-25 08:30:00', 16, 6),

    -- Post 7: Vienna Beisl
    ('2026-04-01 14:10:00', 11, 7),
    ('2026-04-01 16:15:00', 16, 7),

    -- Post 8: Weekend trip from Sofia
    ('2026-04-07 09:30:00', 3, 8),
    ('2026-04-07 11:00:00', 11, 8),
    ('2026-04-07 14:10:00', 18, 8),

    -- Post 9: Cabin bag
    ('2026-04-14 18:00:00', 19, 9),
    ('2026-04-14 20:30:00', 20, 9),

    -- Post 10: Booking or Airbnb
    ('2026-04-22 21:45:00', 5, 10),
    ('2026-04-22 23:00:00', 20, 10),

    -- Post 11: London airport
    ('2026-05-01 11:00:00', 3, 11),
    ('2026-05-01 12:30:00', 18, 11),
    ('2026-05-01 17:00:00', 20, 11),

    -- Post 13: Amsterdam
    ('2026-05-17 13:00:00', 4, 13),
    ('2026-05-17 15:10:00', 5, 13),

    -- Post 14: Solo travel safety
    ('2026-05-26 19:20:00', 19, 14),
    ('2026-05-26 20:45:00', 20, 14),

    -- Post 15: Teufelsberg
    ('2026-06-03 16:30:00', 5, 15),
    ('2026-06-03 18:30:00', 9, 15),
    ('2026-06-04 08:20:00', 17, 15),

    -- Post 19: Ski levels
    ('2026-06-30 18:00:00', 1, 19),
    ('2026-06-30 18:20:00', 2, 19),
    ('2026-06-30 19:10:00', 3, 19),
    ('2026-06-30 20:15:00', 12, 19),

    -- Post 20: Roman restaurant
    ('2026-07-04 21:00:00', 10, 20),
    ('2026-07-04 21:30:00', 14, 20),
    ('2026-07-05 09:15:00', 16, 20);



insert into phone_numbers
(number, user_id)
values
    ('+359888123456', 1),
    ('+359887234567', 2),
    ('+359889345678', 3);


