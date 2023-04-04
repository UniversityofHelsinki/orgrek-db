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
INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('19063', 'Kansainvalisen liikejuridiikan maisteriohjelma', null, null, null, 'kukkuuu');
INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID) VALUES ('5283', 'Minun oma laitos', null, null, null, 'minunoma');


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
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (66325, '19063', 'iam-johtoryhma', 'hy-humtdk-spt-jory', null, null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (66326, '19063', 'oppiaine_tunnus', 'MP50', null, null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (66327, '19063', 'name_fi', 'Kansainvalisen liikejuridiikan maisteriohjelma', null, null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (66328, '19063', 'name_en', 'Master''s Programme in International Business Law', null, null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (66329, '19063', 'name_sv', 'Magisterprogramme', null, null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (66330, '19063', 'type', 'maisteriohjelma', null, null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (5283, '5283', 'koira', 'koira', null, null);
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (5286, '5283', 'kissa', 'kissa', TO_DATE('2022-01-01', 'YYYY-MM-DD'), TO_DATE('2022-01-31', 'YYYY-MM-DD'));
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (5284, '5283', 'kani', 'kani', null, TO_DATE('2022-01-31', 'YYYY-MM-DD'));
INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE) VALUES (5285, '5283', 'meduusa', 'meduusa', TO_DATE('2022-01-01', 'YYYY-MM-DD'), null);


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
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('hy-humtdk-spt-jory', 'fi', 'Kansainvalisen liikejuridiikan maisteriohjelman johtoryhmä', null, null);
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('hy-humtdk-spt-jory', 'sv', 'Ledningsgruppen för magisterprogrammet i internationell affärsjuridik', null, null);
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('hy-humtdk-spt-jory', 'en', 'Steering group for Master''s Programme in International Business Law', null, null);

INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('user_name', 'en', 'User Name', null, null);
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('lokitus_testi_2', 'en', 'lokitus_testi_2', 'baabenom', TO_TIMESTAMP('2021-09-20 15:54:17.593000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('add_text_key_info', 'en', 'add_text_key_info', 'tzrasane', TO_TIMESTAMP('2019-10-30 10:43:10.214000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('public', 'en', 'Public', 'hpalva', TO_TIMESTAMP('2021-02-08 11:15:56.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('publicity', 'en', 'Publicity', 'hpalva', TO_TIMESTAMP('2021-02-08 11:08:03.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('download_history_excel_info', 'en', 'download_history_excel_info', 'tzrasane', TO_TIMESTAMP('2019-12-13 14:55:41.321000', 'YYYY-MM-DD HH24:MI:SS.FF6'));

INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('user_name', 'sv', 'Användarnamn', null, null);
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('public', 'sv', 'offentlig', 'baabenom', TO_TIMESTAMP('2021-09-20 15:54:17.597000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('add_text_key_info', 'sv', 'add_text_key_info', 'tzrasane', TO_TIMESTAMP('2019-10-30 10:43:10.219000', 'YYYY-MM-DD HH24:MI:SS.FF6'));

INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('tohtoriohjelma-joryt', 'fi', 'Tohtoriohjelmien johtoryhmät', 'baabenom', TO_TIMESTAMP('2022-04-04 15:47:03.802000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('tohtoriohjelma-joryt', 'en', 'Doctoral programme steering groups', 'baabenom', TO_TIMESTAMP('2022-04-04 15:54:29.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('tohtoriohjelma-joryt', 'sv', 'Doktorandprogrammens ledningsgrupper', 'baabenom', TO_TIMESTAMP('2022-04-04 15:56:24.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('maisteriohjelma-joryt', 'fi', 'Maisteriohjelmien johtoryhmät', 'baabenom', TO_TIMESTAMP('2022-04-04 15:48:07.642000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('maisteriohjelma-joryt', 'en', 'Master''s programme steering groups', 'baabenom', TO_TIMESTAMP('2022-04-04 16:12:27.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('maisteriohjelma-joryt', 'sv', 'Magisterprogrammens ledningsgrupper', 'baabenom', TO_TIMESTAMP('2022-04-04 16:11:02.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('kandiohjelma-joryt', 'fi', 'Kandiohjelmien johtoryhmät', 'baabenom', TO_TIMESTAMP('2022-04-04 15:48:38.563000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('kandiohjelma-joryt', 'en', 'Bachelor''s programme steering groups', 'baabenom', TO_TIMESTAMP('2022-04-04 16:12:26.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));
INSERT INTO TEXT (KEY, LANGUAGE, VALUE, USER_NAME, TIMESTAMP) VALUES ('kandiohjelma-joryt', 'sv', 'Kandidatprogrammens ledningsgrupper', 'baabenom', TO_TIMESTAMP('2022-04-04 16:11:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'));


INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (9222, '19063', '9999', null, null, 'toiminnanohjaus');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (9386, '3368', 'a1', TO_TIMESTAMP('2016-02-21 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2016-12-30 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'talous');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (9390, '3459', 'a1', null, TO_TIMESTAMP('2016-12-30 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'talous');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (9397, '3201', 'a1', null, TO_TIMESTAMP('2016-12-30 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'henkilosto');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (12179, '6777', 'a1', null, null, 'toiminnanohjaus');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (11116, '3337', 'a1', null, null, 'history');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (12200, '3471', 'a1', null, null, 'history');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (12201, 'a1', '9999', TO_TIMESTAMP('3000-12-30 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), null, 'history');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (55760, '3130', 'a1', TO_TIMESTAMP('3000-07-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('3000-08-15 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'talous');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (55759, '5935', 'a1', null, null, 'opetus');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (55761, '5935', 'a1', TO_TIMESTAMP('3000-07-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('3000-08-15 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'talous');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (55762, '5935', '6770', TO_TIMESTAMP('2018-07-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('2018-08-15 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'toiminnanohjaus');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (55763, '5935', '3130', TO_TIMESTAMP('3000-07-31 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), TO_TIMESTAMP('3000-08-15 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'toiminnanohjaus');
INSERT INTO EDGE (ID, CHILD_NODE_ID, PARENT_NODE_ID, START_DATE, END_DATE, HIERARCHY) VALUES (55797, '3288', 'a1', null, TO_TIMESTAMP('2018-07-27 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'tutkimus');

INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, KEY, VALUE, START_DATE, END_DATE) VALUES (1, 'henkilosto', 'type', 'koontiyksikko', null, null);
INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, KEY, VALUE, START_DATE, END_DATE) VALUES (2, 'henkilosto', 'type', 'tiedekunta', null, null);
INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, KEY, VALUE, START_DATE, END_DATE) VALUES (3, 'henkilosto', 'type', 'osasto', '2020-12-30 00:00:00.000000', null);
INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, KEY, VALUE, START_DATE, END_DATE) VALUES (4, 'henkilosto', 'type', 'laitos', null, null);
INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, KEY, VALUE, START_DATE, END_DATE) VALUES (5, 'henkilosto', 'hr_tunnus', null, '2022-01-30 00:00:00.000000', '2033-01-30 00:00:00.000000');
INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, KEY, VALUE, START_DATE, END_DATE) VALUES (6, 'henkilosto', 'hr_lyhenne', null, '2023-02-22 00:00:00.000000', '2033-03-20 00:00:00.000000');
INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, KEY, VALUE, START_DATE, END_DATE) VALUES (7, 'opetus', 'type', 'tohtoriohjelma', '2022-02-22 00:00:00.000000', '2033-03-20 00:00:00.000000');
INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, KEY, VALUE, START_DATE, END_DATE) VALUES (8, 'opetus', 'type', 'maisteriohjelma', '2022-02-22 00:00:00.000000', '2033-03-20 00:00:00.000000');
INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, KEY, VALUE, START_DATE, END_DATE) VALUES (9, 'opetus', 'type', 'kandiohjelma', '2022-02-22 00:00:00.000000', '2033-03-20 00:00:00.000000');
INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, KEY, VALUE, START_DATE, END_DATE) VALUES (10, 'opetus', 'type', 'tutkijakoulu', '2022-02-22 00:00:00.000000', '2033-03-20 00:00:00.000000');
INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, KEY, VALUE, START_DATE, END_DATE) VALUES (11, 'talous', 'talous_tunnus', null, '2022-02-22 00:00:00.000000', '2033-03-20 00:00:00.000000');
INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, KEY, VALUE, START_DATE, END_DATE) VALUES (12, 'talous', 'type', 'laitos', '2022-02-22 00:00:00.000000', '2033-03-20 00:00:00.000000');


INSERT INTO SECTION_ATTR (ID, SECTION, ATTR, START_DATE, END_DATE) VALUES (1, 'codes', 'emo_lyhenne', TIMESTAMP '2023-04-03 13:48:38.000000', null);
INSERT INTO SECTION_ATTR (ID, SECTION, ATTR, START_DATE, END_DATE) VALUES (2, 'codes', 'hr_lyhenne', TIMESTAMP '2023-04-03 13:48:59.000000', null);
INSERT INTO SECTION_ATTR (ID, SECTION, ATTR, START_DATE, END_DATE) VALUES (3, 'codes', 'hr_tunnus', TIMESTAMP '2023-04-03 13:48:59.000000', null);
INSERT INTO SECTION_ATTR (ID, SECTION, ATTR, START_DATE, END_DATE) VALUES (4, 'other_attributes', 'iam-henkilostoryhma', TIMESTAMP '2023-04-03 13:48:59.000000', null);
INSERT INTO SECTION_ATTR (ID, SECTION, ATTR, START_DATE, END_DATE) VALUES (5, 'codes', 'iam_ryhma', TIMESTAMP '2023-04-03 13:48:59.000000', null);
INSERT INTO SECTION_ATTR (ID, SECTION, ATTR, START_DATE, END_DATE) VALUES (6, 'codes', 'laskutus_tunnus', TIMESTAMP '2023-04-03 13:48:59.000000', null);
INSERT INTO SECTION_ATTR (ID, SECTION, ATTR, START_DATE, END_DATE) VALUES (7, 'codes', 'lyhenne', TIMESTAMP '2023-04-03 13:48:59.000000', null);
INSERT INTO SECTION_ATTR (ID, SECTION, ATTR, START_DATE, END_DATE) VALUES (8, 'codes', 'mainari_tunnus', TIMESTAMP '2023-04-03 13:48:59.000000', null);
INSERT INTO SECTION_ATTR (ID, SECTION, ATTR, START_DATE, END_DATE) VALUES (9, 'codes', 'oppiaine_tunnus', TIMESTAMP '2023-04-03 13:48:59.000000', null);
INSERT INTO SECTION_ATTR (ID, SECTION, ATTR, START_DATE, END_DATE) VALUES (10, 'codes', 'talous_tunnus', TIMESTAMP '2023-04-03 13:48:59.000000', null);
INSERT INTO SECTION_ATTR (ID, SECTION, ATTR, START_DATE, END_DATE) VALUES (11, 'codes', 'tutkimus_tunnus', TIMESTAMP '2023-04-03 13:48:59.000000', null);
INSERT INTO SECTION_ATTR (ID, SECTION, ATTR, START_DATE, END_DATE) VALUES (12, 'other_attributes', 'iam-johtoryhma', TIMESTAMP '2023-04-03 13:48:59.000000', null);
INSERT INTO SECTION_ATTR (ID, SECTION, ATTR, START_DATE, END_DATE) VALUES (13, 'other_attributes', 'publicity', TIMESTAMP '2023-04-03 13:48:59.000000', null);
