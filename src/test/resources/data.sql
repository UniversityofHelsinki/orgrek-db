INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('a1', 'Helsingin yliopisto (HY)', null, null, TO_TIMESTAMP('2021-02-08 11:08:48.677154', 'YYYY-MM-DD HH24:MI:SS.FF6'), '42785051');
INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('3368', 'HY, Lääketieteellinen tiedekunta (LTDK)', null, null, TO_TIMESTAMP('2019-06-28 11:53:47.402784', 'YYYY-MM-DD HH24:MI:SS.FF6'), '16549737');
INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('3459', 'Helsingin yliopiston koe-eläinkeskus', null, null, TO_TIMESTAMP('2017-12-29 18:01:29.136638', 'YYYY-MM-DD HH24:MI:SS.FF6'), '57023347');
INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('3201', 'Svenska social- och kommunalhögskolan', null, null, TO_TIMESTAMP('2018-02-02 20:26:52.463092', 'YYYY-MM-DD HH24:MI:SS.FF6'), '55437194');
INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('6777', 'HY, Muut yksiköt (MUUT)', TO_TIMESTAMP('2010-01-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), null, TO_TIMESTAMP('2018-08-13 12:51:49.232870', 'YYYY-MM-DD HH24:MI:SS.FF6'), '74223428');
INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('3337', 'IPR University Center', null, TO_TIMESTAMP('2013-12-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2017-12-28 11:41:25.657187', 'YYYY-MM-DD HH24:MI:SS.FF6'), '75908005');
INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('3471', 'HY, Helsingin yliopiston koulutus- ja kehittämispalvelut (HYKKE)', null, TO_TIMESTAMP('2016-12-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2016-10-17 18:00:11.585986', 'YYYY-MM-DD HH24:MI:SS.FF6'), '21682135');
INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('9999', 'HY, Tulevaisuuden Helsingin yliopisto 3000 (HY)', TO_TIMESTAMP('3000-12-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), null,  TO_TIMESTAMP('2021-10-17 18:00:11.585986', 'YYYY-MM-DD HH24:MI:SS.FF6'), '99999999');
INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('5935', 'HY, Oikeustieteellinen tiedekunta (OIKTDK)', null, null, TO_TIMESTAMP('2019-12-19 14:40:51.699943', 'YYYY-MM-DD HH24:MI:SS.FF6'), '10020336');
INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('3288', 'Biotekniikan instituutti', null, null, TO_TIMESTAMP('2018-01-16 14:23:40.265476', 'YYYY-MM-DD HH24:MI:SS.FF6'), '14856367');
INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('6770', 'HY, Tiedekunnat (TDK)', TO_TIMESTAMP('1961-03-19 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), null, TO_TIMESTAMP('2020-01-23 13:46:40.790825', 'YYYY-MM-DD HH24:MI:SS.FF6'), '32800844');
INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('3130', 'Erilliset laitokset', TO_TIMESTAMP('3000-03-19 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), null, TO_TIMESTAMP('2017-12-28 10:49:04.463428', 'YYYY-MM-DD HH24:MI:SS.FF6'), '42275629');

INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (3648, 'a1', 'name_en', 'University of Helsinki', null, null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (3649, 'a1', 'name_sv', 'Helsingfors universitet', null, null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (3650, 'a1', 'lyhenne', 'HY', null, null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (3652, 'a1', 'type', 'yritys / yhteiso', null, null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (3653, 'a1', 'name_fi', 'Helsingin yliopisto', null, null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (15779, 'a1', 'hr_lyhenne', 'HY', TO_TIMESTAMP('2013-12-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (15780, 'a1', 'hr_tunnus', '51000053', TO_TIMESTAMP('2013-12-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (55923, 'a1', 'writerin_laittama', 'attribuutti', TO_TIMESTAMP('2018-01-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2018-08-23 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (55387, 'a1', 'testissä1', 'tänään', TO_TIMESTAMP('2018-01-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2018-08-30 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (55384, 'a1', 'testissä2', 'testi', TO_TIMESTAMP('2018-01-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2018-08-24 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (55380, 'a1', 'testissä3', 'testi', TO_TIMESTAMP('2018-01-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2018-08-17 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (55381, 'a1', 'testissä4', 'testi', TO_TIMESTAMP('2018-01-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2018-08-17 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (55390, 'a1', 'testissä5', 'tänään', TO_TIMESTAMP('2018-01-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2018-08-04 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (55393, 'a1', 'testissä6', 'tänään', TO_TIMESTAMP('2018-01-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2018-07-15 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (55400, 'a1', 'testissä', 'testi-juttu', TO_TIMESTAMP('2018-01-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2018-07-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (56272, 'a1', 'ohtutestaa', 'ohtutestaa', TO_TIMESTAMP('1962-02-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (56322, 'a1', 'Julkisuus', 'julkinen', TO_TIMESTAMP('2021-01-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (56325, 'a1', 'publicity', 'Julkinen', TO_TIMESTAMP('2021-01-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), null);


INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('public', 'fi', 'julkinen', null, null);
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('node_attr_value', 'fi', 'Attribuutin arvo*', null, null);
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('start_date', 'fi', 'Voimassaolo alkaa (pp.kk.vvvv)', 'pmjokela', TO_TIMESTAMP('2018-08-20 14:37:07.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('end_date', 'fi', 'Voimassaolo päättyy (pp.kk.vvvv)', 'pmjokela', TO_TIMESTAMP('2018-08-20 14:37:01.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('add_new_node_attr', 'fi', 'Lisää uusi attribuutti kohteeseen', null, null);
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('add_btn_text', 'fi', 'Lisää', null, null);
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('parent_units', 'fi', 'Yläyksiköt', null, null);
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('children_units', 'fi', 'Aliyksiköt', null, null);
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('value', 'fi', 'Arvo', null, null);
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('start', 'fi', 'Alku', null, null);

INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('user_name', 'en', 'User Name', null, null);
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('lokitus_testi_2', 'en', 'lokitus_testi_2', 'baabenom', TO_TIMESTAMP('2021-09-20 15:54:17.593000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('add_text_key_info', 'en', 'add_text_key_info', 'tzrasane', TO_TIMESTAMP('2019-10-30 10:43:10.214000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('public', 'en', 'Public', 'hpalva', TO_TIMESTAMP('2021-02-08 11:15:56.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('publicity', 'en', 'Publicity', 'hpalva', TO_TIMESTAMP('2021-02-08 11:08:03.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('download_history_excel_info', 'en', 'download_history_excel_info', 'tzrasane', TO_TIMESTAMP('2019-12-13 14:55:41.321000', 'YYYY-MM-DD HH24:MI:SS.FF6'));

INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('user_name', 'sv', 'Användarnamn', null, null);
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('public', 'sv', 'offentlig', 'baabenom', TO_TIMESTAMP('2021-09-20 15:54:17.597000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('add_text_key_info', 'sv', 'add_text_key_info', 'tzrasane', TO_TIMESTAMP('2019-10-30 10:43:10.219000', 'YYYY-MM-DD HH24:MI:SS.FF6'));

INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, TYPE) VALUES (9386, '3368', 'a1', TO_TIMESTAMP('2016-02-30 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2016-12-30 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'talous');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, TYPE) VALUES (9390, '3459', 'a1', null, TO_TIMESTAMP('2016-12-30 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'talous');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, TYPE) VALUES (9397, '3201', 'a1', null, TO_TIMESTAMP('2016-12-30 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'henkilosto');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, TYPE) VALUES (12179, '6777', 'a1', null, null, 'toiminnanohjaus');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, TYPE) VALUES (11116, '3337', 'a1', null, null, 'history');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, TYPE) VALUES (12200, '3471', 'a1', null, null, 'history');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, TYPE) VALUES (12201, 'a1', '9999', TO_TIMESTAMP('3000-12-30 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), null, 'history');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, TYPE) VALUES (55760, '3130', 'a1', TO_TIMESTAMP('3000-07-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('3000-08-15 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'talous');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, TYPE) VALUES (55759, '5935', 'a1', null, null, 'opetus');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, TYPE) VALUES (55761, '5935', 'a1', TO_TIMESTAMP('3000-07-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('3000-08-15 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'talous');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, TYPE) VALUES (55762, '5935', '6770', TO_TIMESTAMP('2018-07-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2018-08-15 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'toiminnanohjaus');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, TYPE) VALUES (55763, '5935', '3130', TO_TIMESTAMP('3000-07-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('3000-08-15 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'toiminnanohjaus');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, TYPE) VALUES (55797, '3288', 'a1', null, TO_TIMESTAMP('2018-07-27 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'tutkimus');

