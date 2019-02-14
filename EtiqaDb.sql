CREATE DATABASE  IF NOT EXISTS `etiqadb`;

USE `etiqadb`;

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`(
   `course_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
   `course_name` varchar(50) NOT NULL
   )ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `student_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `student_name` varchar(100) NOT NULL,
  `student_age` int NOT NULL,
  `course_id` int,
  CONSTRAINT `fk_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
