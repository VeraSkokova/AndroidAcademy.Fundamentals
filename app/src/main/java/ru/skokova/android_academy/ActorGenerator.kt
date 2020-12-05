package ru.skokova.android_academy

class ActorGenerator {
    fun getAvengersActors(): List<Actor> {
        return listOf(
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
        )
    }
}