-- Create tables
--CREATE TABLE DatabaseManager (
--	username VARCHAR (50) PRIMARY KEY,
--	password VARCHAR (50) NOT NULL
--);


--CREATE TABLE Nationality (
--	nationality_id VARCHAR (3) PRIMARY KEY,
--	country VARCHAR (100) NOT NULL,
--	UNIQUE (country)
--);


--CREATE TABLE RatingPlatform (
--	platform_id INTEGER PRIMARY KEY,
--	platform_name VARCHAR (100) NOT NULL,
--	UNIQUE (platform_name)
--);


--CREATE TABLE Genre (
--	genre_id INTEGER PRIMARY KEY,
--	genre_name VARCHAR (50) NOT NULL,
--	UNIQUE (genre_name)
--);


--CREATE TABLE District (
--	district_id INTEGER PRIMARY KEY,
--	district_name VARCHAR (100) NOT NULL,
--	UNIQUE (district_name)
--);


--In Users, Audience, and Director relations, an account_type attribute was created to disallow a username from appearing on Audience, Director and Subcription tables. Also, CHECK constraint was introduced for Users, Audience and Director to ensure that account_type columns are set to appropriate values.
CREATE TABLE Users (
	username VARCHAR (50) PRIMARY KEY,
	password VARCHAR (50) NOT NULL,
	name VARCHAR (50) NOT NULL,
	surname VARCHAR (50) NOT null,
	account_type VARCHAR (10) NOT NULL,
	UNIQUE (username, account_type),
	CHECK (account_type IN ('AUDIENCE', 'DIRECTOR'))
);


CREATE TABLE Director (
	username VARCHAR (50) PRIMARY KEY,
	nationality VARCHAR (50) NOT NULL,
	platform_id INTEGER NULL,
	account_type VARCHAR (10) NOT NULL,
	FOREIGN KEY (username, account_type) REFERENCES Users (username, account_type),
    CHECK (account_type = 'DIRECTOR')
);


CREATE TABLE Audience (
	username VARCHAR (50) PRIMARY KEY,
	account_type VARCHAR (10) NOT NULL,
	FOREIGN KEY (username, account_type) REFERENCES Users (username, account_type),
    CHECK (account_type = 'AUDIENCE')
);


--Since capacity of theatre is not mentioned in the assignment, I took it as 100 for each theatre.
CREATE TABLE Theatre (
	theatre_id INTEGER PRIMARY KEY,
	theatre_name VARCHAR (100) NOT NULL,
	capacity INTEGER CHECK (capacity>0 AND capacity<=100) NOT NULL
);


CREATE TABLE Subscription (
	audience_username VARCHAR (50) NOT NULL,
	platform_id INTEGER NOT NULL,
	PRIMARY KEY (audience_username, platform_id),
	FOREIGN KEY (audience_username) REFERENCES Audience (username)
);


--CREATE TABLE Agreement (
--	director_username VARCHAR (50) PRIMARY KEY,
--	platform_id INTEGER NOT NULL,
--	UNIQUE (director_username, platform_id),
--	FOREIGN KEY (director_username) REFERENCES Director (username)
--);


CREATE TABLE Movie (
	movie_id INTEGER PRIMARY KEY,
	movie_name VARCHAR (100) NOT NULL,
	director_username VARCHAR (50) NOT NULL,
	platform_id INTEGER NOT NULL,
	prequel_movie_id INTEGER,
	duration INTEGER CHECK(duration > 0 AND duration < 5),
	average_rating REAL CHECK (average_rating >= 0 AND average_rating <= 5),
	UNIQUE (movie_id, prequel_movie_id),
	UNIQUE (movie_id, platform_id),
	FOREIGN KEY (director_username) REFERENCES Director (username),
	FOREIGN KEY (prequel_movie_id) REFERENCES Movie (movie_id)
);


CREATE TABLE MovieGenreList (
	movie_id INTEGER NOT NULL,
	genre_id INTEGER NOT NULL,
	PRIMARY KEY (movie_id, genre_id),
	FOREIGN KEY (movie_id) REFERENCES Movie (movie_id)
);


CREATE TABLE WatchedMovie (
	audience_username VARCHAR (50) NOT NULL,
	movie_id INTEGER NOT NULL,
	PRIMARY KEY (audience_username, movie_id),
	FOREIGN KEY (audience_username) REFERENCES Audience (username),
    FOREIGN KEY (movie_id) REFERENCES Movie (movie_id)
);


CREATE TABLE MovieSession (
	session_id BIGSERIAL PRIMARY KEY,
	movie_id INTEGER NOT NULL,
	theatre_id INTEGER NOT NULL,
	screening_date DATE,
	from_timeslot INTEGER NOT NULL CHECK(from_timeslot > 0 AND from_timeslot < 5),
	to_timeslot INTEGER NOT NULL CHECK(to_timeslot > 0 AND to_timeslot < 5 AND to_timeslot > from_timeslot),
	UNIQUE (session_id, movie_id),
	UNIQUE (theatre_id, screening_date, from_timeslot, to_timeslot),
	FOREIGN KEY (movie_id) REFERENCES Movie (movie_id),
	FOREIGN KEY (theatre_id) REFERENCES Theatre (theatre_id)
);


CREATE TABLE Ticket (
	audience_username VARCHAR (50) NOT NULL,
	session_id INTEGER NOT NULL,
	movie_id INTEGER NOT NULL,
	prequel_movie_id INTEGER,
	PRIMARY KEY (audience_username, session_id),
	UNIQUE (audience_username, session_id, movie_id),
	FOREIGN KEY (audience_username) REFERENCES Audience (username),
	FOREIGN KEY (session_id, movie_id) REFERENCES MovieSession (session_id, movie_id),
    FOREIGN KEY (movie_id, prequel_movie_id) REFERENCES Movie (movie_id, prequel_movie_id),
    FOREIGN KEY (audience_username, prequel_movie_id) REFERENCES WatchedMovie (audience_username, movie_id)
);

CREATE TABLE Rating (
	audience_username VARCHAR (50) NOT NULL,
	movie_id INTEGER NOT NULL,
	session_id INTEGER NOT NULL,
	platform_id INTEGER NOT NULL,
	rating REAL NOT NULL CHECK (rating >= 0 AND rating <= 5),
	PRIMARY KEY (audience_username, movie_id),
	FOREIGN KEY (session_id, movie_id) REFERENCES MovieSession (session_id, movie_id),
	FOREIGN KEY (audience_username, platform_id) REFERENCES Subscription (audience_username, platform_id),
	FOREIGN KEY (movie_id, platform_id) REFERENCES Movie (movie_id, platform_id),
	FOREIGN KEY (audience_username, session_id, movie_id) REFERENCES Ticket (audience_username, session_id, movie_id)
);


CREATE OR REPLACE FUNCTION UpdateMovieAverageRating()
  RETURNS trigger AS
$BODY$
BEGIN
    UPDATE Movie
    SET average_rating = (SELECT AVG(r.rating) FROM Rating r WHERE r.movie_id = NEW.movie_id)
    WHERE movie_id = NEW.movie_id;
    RETURN NULL;
END;
$BODY$
  LANGUAGE plpgsql;


DROP TRIGGER IF EXISTS average_rating_trigger ON Rating;
CREATE TRIGGER average_rating_trigger
  AFTER INSERT
  ON Rating
  FOR EACH ROW
  EXECUTE PROCEDURE UpdateMovieAverageRating();


INSERT INTO Theatre
(theatre_id, theatre_name, capacity)
VALUES(1, 'Odeon Theatre', 100);
INSERT INTO Theatre
(theatre_id, theatre_name, capacity)
VALUES(2, 'Carnegie Hall', 100);
