CREATE DATABASE flaky_events CHARACTER SET utf8;
GRANT ALL PRIVILEGES ON flaky_events.* To 'flaky_events'@'localhost' IDENTIFIED BY 'flaky_events';
GRANT ALL PRIVILEGES ON flaky_events.* To 'flaky_events'@'%' IDENTIFIED BY 'flaky_events';
FLUSH PRIVILEGES;
