package com.marktkachenko.lebenindeutschland.models.questions

import com.marktkachenko.lebenindeutschland.R

enum class Lands (val nameId: Int, val value: Int) {
    BADEN_WUERTTEMBERG(R.string.baden_württemberg, 1),
    BAVARIA(R.string.bavaria, 2),
    BERLIN(R.string.berlin, 3),
    BRANDENBURG(R.string.brandenburg, 4),
    BREMEN(R.string.bremen, 5),
    HAMBURG(R.string.hamburg, 6),
    HESSE(R.string.hesse, 7),
    LOWER_SAXONY(R.string.lower_saxony, 8),
    MECKLENBURG_VORPOMMERN(R.string.mecklenburg_vorpommern, 9),
    NORTH_RHINE_WESTPHALIA(R.string.north_rhine_westphalia, 10),
    RHINELAND_PALATINATE(R.string.rhineland_palatinate, 11),
    SAARLAND(R.string.saarland, 12),
    SAXONY(R.string.saxony, 13),
    SAXONY_ANHALT(R.string.saxony_anhalt, 14),
    SCHLESWIG_HOLSTEIN(R.string.schleswig_holstein, 15),
    THURINGIA(R.string.thuringia, 16)
}

enum class Topics(val nameId: Int, val value: Int) {
    CONSTITUTIONAL_BODIES(R.string.constitutional_bodies, 1),
    CONSTITUTIONAL_PRINCIPLES(R.string.constitutional_principles, 2),
    FEDERALISM(R.string.federalism, 3),
    SOCIAL_SYSTEM(R.string.social_system, 4),
    FUNDAMENTAL_RIGHTS(R.string.fundamental_rights, 5),
    ELECTIONS_AND_PARTICIPATION(R.string.elections_and_participation, 6),
    POLITICAL_PARTIES(R.string.political_parties, 7),
    TASKS_OF_THE_STATE(R.string.tasks_of_the_state, 8),
    DUTIES_OF_THE_STATE(R.string.duties_of_the_state, 9),
    STATE_SYMBOLS(R.string.state_symbols, 10),
    MUNICIPALITY(R.string.municipality, 11),
    LAW_AND_EVERYDAY_LIFE(R.string.law_and_everyday_life, 12),
    NATIONAL_SOCIALISM_AND_ITS_CONSEQUENCES(R.string.national_socialism_and_its_consequences, 13),
    IMPORTANT_STAGES_AFTER_1945(R.string.important_stages_after_1945, 14),
    REUNIFICATION(R.string.reunification, 15),
    GERMANY_IN_EUROPE(R.string.germany_in_europe, 16),
    RELIGIOUS_DIVERSITY(R.string.religious_diversity, 17),
    EDUCATION(R.string.education, 18),
    HISTORY_OF_MIGRATION(R.string.history_of_migration, 19),
    INTERCULTURAL_COEXISTENCE(R.string.intercultural_coexistence, 20)
}
