INSERT INTO app_user (email, password, pseudo, admin) VALUES
        ('a@a', '$2a$10$Wt8piqncC156t0XbZjwjvu59xy1e8kkM05r6UOCAekHcSigJttyWS', 'User A', true),
        ('b@b', '$2a$10$Wt8piqncC156t0XbZjwjvu59xy1e8kkM05r6UOCAekHcSigJttyWS', 'User B', false),
        ('c@c', '$2a$10$Wt8piqncC156t0XbZjwjvu59xy1e8kkM05r6UOCAekHcSigJttyWS', 'User C', false);

INSERT INTO recipe (name, creator_id) VALUES
        ('tarte aux pommes', 1),
        ('paella', 2),
        ('croissant', 1);

INSERT INTO tag (name) VALUES
        ('sud'),
        ('dessert'),
        ('arachide');

INSERT INTO recipe_tags (recipe_id, tags_id) VALUES
        (1, 2),
        (2, 1),
        (3, 2),
        (3, 3);

INSERT INTO question (content, asked_at, author_id) VALUES
        ('je veux un dessert', '2026-03-15 12:00:00', 2),
        ('quelque chose du sud sans arachide', '2026-03-16 13:30:00', 2),
        ('un petit dej rapide', '2026-03-17 08:15:00', 3);

INSERT INTO question_included_tags (question_id, included_tags_id) VALUES
        (1, 2),
        (2, 1);

INSERT INTO question_excluded_tags (question_id, excluded_tags_id) VALUES
        (2, 3);

INSERT INTO question_returned_recipes (question_id, returned_recipes_id) VALUES
        (1, 1),
        (1, 3),
        (2, 2),
        (3, 3);