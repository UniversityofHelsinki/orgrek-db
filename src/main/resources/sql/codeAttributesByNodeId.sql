SELECT * FROM NODE_ATTR WHERE NODE_ID = :node_id AND (KEY = 'lyhenne' OR
                                                      KEY = 'emo_lyhenne' OR
                                                      KEY = 'hr_lyhenne' OR
                                                      KEY = 'iam_ryhma' OR
                                                      KEY = 'hr_tunnus' OR
                                                      KEY = 'laskutus_tunnus' OR
                                                      KEY = 'mainari_tunnus' OR
                                                      KEY = 'oppiaine_tunnus' OR
                                                      KEY = 'talous_tunnus' OR
                                                      KEY = 'tutkimus_tunnus')
