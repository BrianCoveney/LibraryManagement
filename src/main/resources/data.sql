REPLACE INTO books VALUES (1, 'Spring in action', '161729120X, 9781617291203', 'Craig Walls', 'Manning Publications', 'Fourth edition', '2015-01-01'),
(2, 'Effective Java', '978-0134685991, 0134685997', 'Joshua Bloch', 'Addison-Wesley Professional', 'Third edition', '2017-01-01'),
(3, 'Code Complete', '0735619670, 9780735619678', 'Steve McConnell', 'Microsoft Press', 'Second edition', '2004-01-01'),
(4, 'Working Effectively with Legacy Code', '978-0131177055, 0131177052', 'Michael Feathers', 'Addison Wesley', 'First edition', '2004-01-01'),
(5, 'The Pragmatic Programmer', '978-0201616224, 020161622X', 'David Thomas', 'Prentice Hall', 'First edition', '1999-01-01'),
(6, 'Clean Code', '978-0132350884, 0132350882', 'Robert Martin', 'Prentice Hall', 'First edition', '2008-01-01'),
(7, 'Design patterns', '978-0201633610, 0201633612', 'Erich Gamma', 'Addison Wesley', 'First edition', '1994-01-01'),
(8, 'The Clean Coder', '978-0137081073, 0137081073', 'Robert Martin', 'Prentice Hall', 'First edition', '2011-01-01'),
(9, 'Clean Architecture', '978-0134494166, 0134494164', 'Robert Martin', 'Prentice Hall', 'First edition', '2017-01-01'),
(10, 'Secure architectures with OpenBSD', '9780321193667, 0321193660', 'Brandon Palmer, et al', 'Addison-Wesley', 'First edition', '2004-01-01'),
(11, 'The age of the economist', '9780321088123, 0321088123', 'Daniel R Fusfeld', 'Addison-Wesley', 'Ninth edition', '2002-01-01'),
(12, 'Hadoop in action', '1935182196, 9781935182191', 'Chuck Lam', 'Manning Publications', 'First edition', '2011-01-01');

REPLACE INTO members VALUES (1, 'Brian Bloggs', '123 Glen Village Douglas, Cork', '1977-09-18', 4, 14, 0.0),
(2, 'John Smyth', 'Monkstown Cork', '2001-03-01', 4, 7, 0.0),
(3, 'Emma Stone', 'Blarney Cork', '2005-09-01', 2, 2, 0.0),
(4, 'Will Smith', 'Dublin', '1977-03-01', 4, 14, 0.0),
(5, 'David Jones', 'Cork', '2001-03-01', 4, 7, 0.0),
(6, 'Peter Marks', 'Mayo', '2005-09-01', 2, 2, 0.0),
(7, 'Henry Ford', 'Galway', '1977-03-01', 2, 7, 0.0),
(8, 'Mary Kate', 'Kerry', '1977-03-01', 4, 14, 0.0),
(9, 'Frank Bruno', 'Cork', '2005-09-01', 2, 2, 0.0);



REPLACE INTO loan VALUES (1, 9, '2018-06-01', '2018-06-17'),
(2, 8, '2018-06-01', '2018-06-09'),
(3, 7, '2018-06-01', '2018-06-11'),
(4, 6, '2018-06-01', '2018-06-11'),
(5, 5, '2018-06-01', '2018-06-13'),
(6, 4, '2018-06-01', '2018-06-17'),
(7, 3, '2018-06-01', '2018-06-16'),
(8, 2, '2018-06-01', '2018-06-18'),
(9, 1, '2018-06-01', '2018-06-11');
