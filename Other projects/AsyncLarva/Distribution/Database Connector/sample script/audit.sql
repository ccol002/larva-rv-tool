﻿DROP DATABASE IF EXISTS audit;
CREATE DATABASE audit;

USE audit;

CREATE TABLE login_log
(
  id Int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id Int UNSIGNED NOT NULL,
  login_time Datetime NOT NULL,
  login Boolean NOT NULL,
  success Boolean NOT NULL
) ENGINE = InnoDB
;

INSERT INTO login_log (user_id, login_time, login, success)
VALUES (106,'05-01-14 01:00:00',TRUE,FALSE),
 (102,'07-01-14 01:00:00',TRUE,FALSE),
 (103,'05-01-14 01:00:00',FALSE,TRUE),
 (105,'13-01-14 01:00:00',TRUE,FALSE),
 (100,'06-01-14 01:00:00',TRUE,FALSE),
 (110,'13-01-14 01:00:00',FALSE,TRUE),
 (109,'05-01-14 01:00:00',TRUE,FALSE),
 (101,'03-01-14 01:00:00',TRUE,FALSE),
 (115,'13-01-14 01:00:00',FALSE,TRUE),
 (109,'02-01-14 01:00:00',TRUE,FALSE),
 (101,'05-01-14 01:00:00',TRUE,FALSE),
 (115,'04-01-14 01:00:00',FALSE,TRUE),
 (107,'10-01-14 01:00:00',TRUE,FALSE),
 (112,'12-01-14 01:00:00',TRUE,FALSE),
 (113,'13-01-14 01:00:00',FALSE,TRUE),
 (113,'13-01-14 01:00:00',TRUE,FALSE),
 (105,'02-01-14 01:00:00',TRUE,FALSE),
 (101,'14-01-14 01:00:00',FALSE,TRUE),
 (106,'11-01-14 01:00:00',TRUE,FALSE),
 (111,'02-01-14 01:00:00',TRUE,FALSE),
 (108,'06-01-14 01:00:00',FALSE,TRUE),
 (105,'10-01-14 01:00:00',TRUE,FALSE),
 (101,'06-01-14 01:00:00',TRUE,FALSE),
 (108,'07-01-14 01:00:00',FALSE,TRUE),
 (111,'06-01-14 01:00:00',TRUE,FALSE),
 (106,'12-01-14 01:00:00',TRUE,FALSE),
 (113,'10-01-14 01:00:00',FALSE,TRUE),
 (109,'12-01-14 01:00:00',TRUE,FALSE),
 (108,'08-01-14 01:00:00',TRUE,FALSE),
 (110,'05-01-14 01:00:00',FALSE,TRUE),
 (103,'05-01-14 01:00:00',TRUE,FALSE),
 (110,'14-01-14 01:00:00',TRUE,FALSE),
 (111,'13-01-14 01:00:00',FALSE,TRUE),
 (111,'05-01-14 01:00:00',TRUE,FALSE),
 (109,'04-01-14 01:00:00',TRUE,FALSE),
 (104,'04-01-14 01:00:00',FALSE,TRUE),
 (103,'07-01-14 01:00:00',TRUE,FALSE),
 (113,'14-01-14 01:00:00',TRUE,FALSE),
 (115,'14-01-14 01:00:00',FALSE,TRUE),
 (103,'03-01-14 01:00:00',TRUE,FALSE),
 (114,'03-01-14 01:00:00',TRUE,FALSE),
 (111,'11-01-14 01:00:00',FALSE,TRUE),
 (108,'11-01-14 01:00:00',TRUE,FALSE),
 (109,'07-01-14 01:00:00',TRUE,FALSE),
 (104,'05-01-14 01:00:00',FALSE,TRUE),
 (114,'13-01-14 01:00:00',TRUE,FALSE),
 (101,'10-01-14 01:00:00',TRUE,FALSE),
 (101,'05-01-14 01:00:00',FALSE,TRUE),
 (103,'10-01-14 01:00:00',TRUE,FALSE),
 (103,'08-01-14 01:00:00',TRUE,FALSE);