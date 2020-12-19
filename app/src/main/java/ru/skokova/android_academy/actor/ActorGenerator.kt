package ru.skokova.android_academy.actor

import ru.skokova.android_academy.R

object ActorGenerator {
    private val actors =
        hashMapOf(
            0 to listOf(
                Actor("Robert Downey Jr.", R.drawable.robert_downey_jr),
                Actor("Chris Evans", R.drawable.chris_evans),
                Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                Actor("Chris Hemsworth", R.drawable.chris_hemsworth),
                Actor("Scarlett Johansson", R.drawable.scarlett_johansson),
                Actor("Jeremy Renner", R.drawable.jeremy_renner),
                Actor("Paul Rudd", R.drawable.paul_rudd),
                Actor("Karen Gillan", R.drawable.karen_gillan),
                Actor("Benedict Cumberbatch", R.drawable.benedict_cumberbatch),
                Actor("Tom Holland", R.drawable.tom_holland)
            ),
            1 to listOf(
                Actor("John David Washington", R.drawable.john_washington),
                Actor("Robert Pattinson", R.drawable.robert_pattinson),
                Actor("Elizabeth Debicki", R.drawable.elizabeth_debicki),
                Actor("Kenneth Branagh", R.drawable.kenneth_branagh),
                Actor("Dimple Kapadia", R.drawable.dimple_kapadia),
                Actor("Aaron Taylor-Johnson", R.drawable.aaron_taylor_johnson),
                Actor("Michael Caine", R.drawable.michael_caine),
                Actor("Clémence Poésy", R.drawable.clemence_poesy)
            ),
            2 to listOf(
                Actor("Scarlett Johansson", R.drawable.scarlett_johansson),
                Actor("Florence Pugh", R.drawable.florence_pugh),
                Actor("David Harbour", R.drawable.david_harbour),
                Actor("O.T. Fagbenle", R.drawable.o_t_fagbenle),
                Actor("Rachel Weisz", R.drawable.rachel_weisz),
                Actor("William Hurt", R.drawable.william_hurt)
            ),
            3 to listOf(
                Actor("Gal Gadot", R.drawable.gal_gadot),
                Actor("Chris Pine", R.drawable.chris_pine),
                Actor("Kristen Wiig", R.drawable.kristen_wiig),
                Actor("Connie Nielsen", R.drawable.connie_nilsen),
                Actor("Pedro Pascal", R.drawable.pedro_pascal)
            )
        )

    fun getActorsById(id: Int): List<Actor> {
        return actors[id] ?: emptyList()
    }
}