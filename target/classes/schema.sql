DROP TABLE IF EXISTS jokes;
DROP TABLE IF EXISTS app_users;

CREATE TABLE app_users( user_id int PRIMARY KEY AUTO_INCREMENT,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        firstname VARCHAR(255) NOT NULL,
                        lastname VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        role VARCHAR(30) NOT NULL,
                        locked BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE TABLE jokes ( id int PRIMARY KEY AUTO_INCREMENT,
                     setup VARCHAR(255) NOT NULL,
                     punchline VARCHAR(255) NOT NULL,
                     min_age INT NOT NULL,
                     category VARCHAR(50) NOT NULL,
                     rating INT NOT NULL CHECK (rating BETWEEN 1 and 5),
                     language VARCHAR(50) NOT NULL,

                     user_id INT NOT NULL,
                     date_added DATE NOT NULL,
                     -- Every joke must reference an existing user by its user_id
                     CONSTRAINT fk_jokes_user
                        FOREIGN KEY (user_id)
                        REFERENCES app_users(user_id)
);