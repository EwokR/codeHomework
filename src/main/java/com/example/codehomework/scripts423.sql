SELECT s.name, s.age, f.name
FROM students s
         LEFT JOIN faculties f on f.id = s.faculty_idFaculty;

SELECT s.name, s.age
FROM students s
INNER JOIN avatars a on s.id = a.student_idStudent