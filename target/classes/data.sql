INSERT INTO app_users (email, firstname, lastname, password, role, locked)
VALUES
    ('oleksandr@jokes.com', 'Oleksandr', 'Kotik', '$2y$10$w3ZbVUdEigmY5Jk96E8iwe00O8MABPk02/J6YfTUCfhDR830GfSda', 'WRITER', FALSE),
    ('john@jokes.com', 'John', 'Kennedy', '$2y$10$PJrt3fYQb4sgPdlWShmSa.X81SnG89G63ApOzbA0PEYncV6A2Dqtm', 'ADMIN', FALSE),
    ('peter@jokes.com', 'Peter', 'MacCollin', '$2y$10$ZRsmirSBurDomYJClDfC1erDJnInP51cegpKGXRqwGkbOSO3IbmPS', 'WRITER', TRUE);

INSERT INTO jokes
(setup, punchline, min_age, category, rating, language, user_id, date_added)
VALUES
    ('Why don''t skeletons fight each other?', 'They don''t have the guts.', 6, 'DAD', 4, 'ENGLISH', 1, '2026-11-01'),
    ('Why did the scarecrow win an award?', 'Because he was outstanding in his field.', 6, 'DAD', 5, 'ENGLISH', 1, '2026-11-02'),
    ('Why don''t eggs tell jokes?', 'They''d crack each other up.', 5, 'DAD', 4, 'ENGLISH', 1, '2026-11-03'),
    ('Why can''t you trust atoms?', 'Because they make up everything.', 10, 'SCIENCE', 5, 'ENGLISH', 1, '2026-11-04'),
    ('What do you call an educated tube?', 'A graduated cylinder.', 12, 'SCIENCE', 4, 'ENGLISH', 1, '2026-11-05'),
    ('Why did the physicist break up with the biologist?', 'There was no chemistry.', 10, 'SCIENCE', 4, 'ENGLISH', 2, '2026-11-06'),
    ('Why do programmers prefer dark mode?', 'Because light attracts bugs.', 12, 'CODING', 5, 'ENGLISH', 2, '2026-11-07'),
    ('Why did the developer go broke?', 'Because he used up all his cache.', 13, 'CODING', 4, 'ENGLISH', 2, '2026-11-08'),
    ('How many programmers does it take to change a light bulb?', 'None. It''s a hardware problem.', 12, 'CODING', 5, 'ENGLISH', 2, '2026-11-09'),

    -- Spanish jokes
    ('¿Qué hace una vaca con los ojos cerrados?', 'Leche concentrada.', 5, 'DAD', 5, 'SPANISH', 2, '2026-11-10'),
    ('¿Cuál es el colmo de un jardinero?', 'Que siempre lo dejen plantado.', 6, 'DAD', 4, 'SPANISH', 3, '2026-11-11'),
    ('¿Por qué el protón nunca está triste?', 'Porque siempre es positivo.', 10, 'SCIENCE', 5, 'SPANISH', 3, '2026-11-12'),
    ('¿Por qué el electrón no pagó la cuenta?', 'Porque tenía carga negativa.', 12, 'SCIENCE', 4, 'SPANISH', 3, '2026-11-13'),
    ('¿Por qué los programadores confunden Halloween con Navidad?', 'Porque OCT 31 = DEC 25.', 13, 'CODING', 5, 'SPANISH', 3, '2026-11-14'),
    ('¿Qué le dijo un bit a otro bit?', 'Nos vemos en el byte.', 11, 'CODING', 4, 'SPANISH', 3, '2026-11-15');