2026-10-20 — ChatGPT — Spring Boot JPA: Age Filtering

Full prompt:
How do I create a JPA query method to find all jokes suitable for a given age? explain briefly using examples.

AI contribution:
Explained how to use findAllByMinAgeLessThanEqual(int age) to return jokes suitable for the specified age.

What I did / accepted, rejected, or changed:
Used the suggested method as a reference for my age filter in JokeRepository.

2026-10-20 — ChatGPT — Spring Boot JPA: Age Filtering
Full prompt:
What does "projection" means in the context of this task "return the setup and punchline of jokes and nothing"? Explain briefly and give examples.

AI contribution:
Explained that a projection returns only selected fields instead of the full entity.

What I did / accepted, rejected, or changed:
Used the explanation as a reference to create a setup and punchline projection.

2026-10-20 — ChatGPT — Complete SQL DATA

Full prompt:
Please fill in the data for min_age and rating to complete the database data:
INSERT INTO jokes (setup, punchline, min_age, category, rating)
VALUES ('Why don''t skeletons fight each other?', 'They don''t have the guts.', 'DAD'),
('Why did the scarecrow win an award?', 'Because he was outstanding in his field.', 'DAD'),
('Why don''t eggs tell jokes?', 'They''d crack each other up.', 'DAD'),
('Why can''t you trust atoms?', 'Because they make up everything.', 'SCIENCE'),
('What do you call an educated tube?', 'A graduated cylinder.', 'SCIENCE'),
('Why did the physicist break up with the biologist?', 'There was no chemistry.', 'SCIENCE'),
('Why do programmers prefer dark mode?', 'Because light attracts bugs.', 'CODING'),
('Why did the developer go broke?', 'Because he used up all his cache.', 'CODING'),
('How many programmers does it take to change a light bulb?', 'None. It''s a hardware problem.', 'CODING');

AI contribution:
Helped with random data to fill the min_age and rating columns.

What I did / accepted, rejected, or changed:
I checked the format of the given SQL code.


2026-10-20 — ChatGPT — Spring Boot JPA: H2 Database Console

Full prompt (shortened summary of the conversation):
My Spring Boot application finishes with exit code 0, why is the H2 console not loading at localhost:8080/h2-console?
Check my pom.xml and application.properties to find the problem. See attached: files

AI contribution:
Suggested changing pom.xml, application.properties configurations.

What I did / accepted, rejected, or changed:
Checked the suggested configurations but kept my original pom.xml and properties. The problem was resolved by reloading the Maven project in IntelliJ.

2026-10-20 — ChatGPT — Spring Boot JPA: H2 Database Console
Full prompt:
While executing my code I encounter this error message:
org.springframework.dao.DataIntegrityViolationException: Could not extract column [2] from JDBC ResultSet [Data conversion error converting "DAD" [22018-240]]
I also attached my Joke.java implementation. Let me know what is missing.

AI contribution:
Explained that JPA was trying to read the category column value as a number instead of a string. Suggested adding @Enumerated(EnumType.STRING) to the category field.

What I did / accepted, rejected, or changed:
Added the annotation to Joke.java to match the string categories stored in the H2 database.

2026-10-26 — ChatGPT — Spring Boot: H2 Console and Security Issue

Full Prompt:
Take a look at my programs run output along with my pom.xml and identify why my H2 database isn't working or loading into memory. What could be causing this?

AI contribution:
Identified that spring-boot-starter-security was still active in pom.xml. Suggested commenting it out because Spring Security might be blocking access to the H2 console.

What I did / accepted, rejected, or changed:
Commented out the security dependency, reloaded Maven and restarted the application. Console started working again, so I kept this solution for now

2026-10-26 — ChatGPT — Spring Boot: H2 Console and Security Issue

Full Prompt:
What is the best way to keep Joke as a return type, instead of changing it to Optional<Joke> here:
public Optional<Joke> getJokeById(Integer id) {
return jokeRepository.findById(id);
}

AI contribution:
Explained how .orElseThrow() allows the service method to return a Joke directly while handling missing records.

What I did / accepted, rejected, or changed:
Chose to use .orElseThrow() to handle IDs that do not exist