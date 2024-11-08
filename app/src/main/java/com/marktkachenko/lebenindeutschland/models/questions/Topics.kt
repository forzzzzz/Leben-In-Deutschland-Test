package com.marktkachenko.lebenindeutschland.models.questions

import com.marktkachenko.lebenindeutschland.R

enum class Lands (val nameId: Int, val value: Int, val numberOfQuestions: Int = 10) {
    BADEN_WUERTTEMBERG(R.string.baden_württemberg, 21),
    BAVARIA(R.string.bavaria, 22),
    BERLIN(R.string.berlin, 23),
    BRANDENBURG(R.string.brandenburg, 24),
    BREMEN(R.string.bremen, 25),
    HAMBURG(R.string.hamburg, 26),
    HESSE(R.string.hesse, 27),
    LOWER_SAXONY(R.string.lower_saxony, 28),
    MECKLENBURG_VORPOMMERN(R.string.mecklenburg_vorpommern, 29),
    NORTH_RHINE_WESTPHALIA(R.string.north_rhine_westphalia, 30),
    RHINELAND_PALATINATE(R.string.rhineland_palatinate, 31),
    SAARLAND(R.string.saarland, 32),
    SAXONY(R.string.saxony, 33),
    SAXONY_ANHALT(R.string.saxony_anhalt, 34),
    SCHLESWIG_HOLSTEIN(R.string.schleswig_holstein, 35),
    THURINGIA(R.string.thuringia, 36)
}

enum class Topics(val nameId: Int, val numberOfQuestions: Int, val value: Int, val theme: Themes) {
    CONSTITUTIONAL_BODIES(R.string.constitutional_bodies, 31, 1, Themes.POLITICS_IN_DEMOCRACY),
    CONSTITUTIONAL_PRINCIPLES(R.string.constitutional_principles, 22, 2, Themes.POLITICS_IN_DEMOCRACY),
    FEDERALISM(R.string.federalism, 8, 3, Themes.POLITICS_IN_DEMOCRACY),
    SOCIAL_SYSTEM(R.string.social_system, 8, 4, Themes.POLITICS_IN_DEMOCRACY),
    FUNDAMENTAL_RIGHTS(R.string.fundamental_rights, 22, 5, Themes.POLITICS_IN_DEMOCRACY),
    ELECTIONS_AND_PARTICIPATION(R.string.elections_and_participation, 30, 6, Themes.POLITICS_IN_DEMOCRACY),
    POLITICAL_PARTIES(R.string.political_parties, 7, 7, Themes.POLITICS_IN_DEMOCRACY),
    TASKS_OF_THE_STATE(R.string.tasks_of_the_state, 4, 8, Themes.POLITICS_IN_DEMOCRACY),
    DUTIES(R.string.duties, 8, 9, Themes.POLITICS_IN_DEMOCRACY),
    STATE_SYMBOLS(R.string.state_symbols, 6,  10, Themes.POLITICS_IN_DEMOCRACY),
    MUNICIPALITY(R.string.municipality, 10, 11, Themes.POLITICS_IN_DEMOCRACY),
    LAW_AND_EVERYDAY_LIFE(R.string.law_and_everyday_life, 36, 12, Themes.POLITICS_IN_DEMOCRACY),
    NATIONAL_SOCIALISM_AND_ITS_CONSEQUENCES(R.string.national_socialism_and_its_consequences, 20, 13, Themes.HISTORY_AND_RESPONSIBILITY),
    IMPORTANT_STAGES_AFTER_1945(R.string.important_stages_after_1945, 33, 14, Themes.HISTORY_AND_RESPONSIBILITY),
    REUNIFICATION(R.string.reunification, 15, 15, Themes.HISTORY_AND_RESPONSIBILITY),
    GERMANY_IN_EUROPE(R.string.germany_in_europe, 21, 16, Themes.HISTORY_AND_RESPONSIBILITY),
    RELIGIOUS_DIVERSITY(R.string.religious_diversity, 5, 17, Themes.PEOPLE_AND_SOCIETY),
    EDUCATION(R.string.education, 7, 18, Themes.PEOPLE_AND_SOCIETY),
    HISTORY_OF_MIGRATION(R.string.history_of_migration, 4, 19, Themes.PEOPLE_AND_SOCIETY),
    INTERCULTURAL_COEXISTENCE(R.string.intercultural_coexistence, 3, 20, Themes.PEOPLE_AND_SOCIETY)
}