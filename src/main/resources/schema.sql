DROP TABLE IF EXISTS app_users;
DROP TABLE IF EXISTS jokes;

CREATE TABLE app_users( user_id int PRIMARY KEY AUTO_INCREMENT,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        firstname VARCHAR(255) NOT NULL,
                        lastname VARCHAR(255) NOT NULL
);
CREATE TABLE jokes ( id int PRIMARY KEY AUTO_INCREMENT,
                     setup VARCHAR(255) NOT NULL,
                     punchline VARCHAR(255) NOT NULL,
                     min_age INT NOT NULL,
                     category VARCHAR(50) NOT NULL,
                     rating INT NOT NULL CHECK (rating BETWEEN 1 and 5),
                     language VARCHAR(50) NOT NULL,

                     user_id INT NOT NULL,
                     -- Every joke must reference an existing user by its user_id
                     CONSTRAINT fk_jokes_user
                        FOREIGN KEY (user_id)
                        REFERENCES app_users(user_id)
);