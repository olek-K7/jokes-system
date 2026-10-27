DROP TABLE IF EXISTS jokes;
CREATE TABLE jokes ( id int PRIMARY KEY AUTO_INCREMENT,
                     setup VARCHAR(255) NOT NULL,
                     punchline VARCHAR(255) NOT NULL,
                     min_age INT NOT NULL,
                     category VARCHAR(50) NOT NULL,
                     rating INT NOT NULL CHECK (rating BETWEEN 1 and 5),
                     language VARCHAR(50) NOT NULL
);