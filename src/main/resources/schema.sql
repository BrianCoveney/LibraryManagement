CREATE TABLE IF NOT EXISTS books ( 
  book_id INT(11) NOT NULL AUTO_INCREMENT, 
  title VARCHAR(50) NOT NULL, 
  isbn VARCHAR(50) NOT NULL,
  author VARCHAR(50) NOT NULL, 
  publisher VARCHAR(50) NOT NULL,
  edition VARCHAR(50) NOT NULL,
  year_of_publication DATE NOT NULL,
  PRIMARY KEY (book_id) 
);

CREATE TABLE IF NOT EXISTS members ( 
  member_id INT(11) NOT NULL AUTO_INCREMENT, 
  name VARCHAR(50) NOT NULL, 
  address VARCHAR(50) NOT NULL,
  date_of_birth DATE NOT NULL, 
  loan_limit INT(11) NOT NULL,
  loan_length INT(11) NOT NULL,
  fines_outstanding DECIMAL(6,4) NOT NULL,
  PRIMARY KEY (member_id) 
);

CREATE TABLE IF NOT EXISTS loan (
  book_id INT(11) NOT NULL,
  member_id INT(11) NOT NULL,
  loan_date DATE NOT NULL,
  return_date DATE NOT NULL,
  CONSTRAINT fk_book_id FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
  CONSTRAINT fk_member_id FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE,
  PRIMARY KEY (book_id, member_id, loan_date)
);