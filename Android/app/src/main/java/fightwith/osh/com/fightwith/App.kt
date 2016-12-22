package fightwith.osh.com.fightwith

import android.app.Application

class App : Application() {
    lateinit var subjects: List<Subject>

    override fun onCreate() {
        super.onCreate()
        instance = this
        subjects = with(resources) {
            listOf(
                    Subject(getString(R.string.trump), getString(R.string.trump_text), getDrawable(R.drawable.trump), getString(R.string._for), getString(R.string.against)),
                    Subject(getString(R.string.console_war), getString(R.string.console_war_text), getDrawable(R.drawable.pc_war), getString(R.string.console), getString(R.string.pc)),
                    Subject(getString(R.string.politics), getString(R.string.politics_text), getDrawable(R.drawable.politics), getString(R.string._for), getString(R.string.against)),
                    Subject(getString(R.string.ForeignSoccer), getString(R.string.foreign_desc), getDrawable(R.drawable.abroad_sport), getString(R.string.RealMadrid), getString(R.string.Barcelona)),
                    Subject(getString(R.string.SuperHeroes), getString(R.string.super_desc), getDrawable(R.drawable.super_heroes), getString(R.string.Marvel), getString(R.string.DC)),
                    Subject(getString(R.string.LocalFootball), getString(R.string.local_desc), getDrawable(R.drawable.local_sport), getString(R.string.Maccabi), getString(R.string.HaPoel)),
                    Subject(getString(R.string.xbox_vs_ps), getString(R.string.xbox_desc), getDrawable(R.drawable.console_war), getString(R.string.XBox), getString(R.string.Playstation)),
                    Subject(getString(R.string.Sports), getString(R.string.sports_desc), getDrawable(R.drawable.sports), getString(R.string.football), getString(R.string.basketball)),
                    Subject(getString(R.string.Phones), getString(R.string.phones_os_desc), getDrawable(R.drawable.os), getString(R.string.Android), getString(R.string.iOS)),
                    Subject(getString(R.string.PreferredWeather), getString(R.string.weather_desc), getDrawable(R.drawable.cold_hot), getString(R.string.Cold), getString(R.string.hot)),
                    Subject(getString(R.string.pokemonvsdigimon), getString(R.string.pokemon_desc), getDrawable(R.drawable.poke), getString(R.string.Pokemon), getString(R.string.Digimon)),
                    Subject(getString(R.string.Monkeys), getString(R.string.monkey_desc), getDrawable(R.drawable.h_v_b), getString(R.string.Bantu), getString(R.string.Harambe)),
                    Subject(getString(R.string.fiction), getString(R.string.fiction_desc), getDrawable(R.drawable.tw_vs_hp), getString(R.string.harry_potter), getString(R.string.twilight)),
                    Subject(getString(R.string.youth_movement), getString(R.string.movement_desc), getDrawable(R.drawable.movments), getString(R.string.tzofim), getString(R.string.ilanoar)),
                    Subject(getString(R.string.Games), getString(R.string.games_desc), getDrawable(R.drawable.o_v_p), getString(R.string.Paladins), getString(R.string.Overwatch)),
                    Subject(getString(R.string.Bakugan_vs_YuGiHo), getString(R.string.bakugan_desc), getDrawable(R.drawable.yugi), getString(R.string.Bakugan), getString(R.string.yugihu)),
                    Subject(getString(R.string.starwars_vs_startrek), getString(R.string.stars_desc), getDrawable(R.drawable.star), getString(R.string.star_wars), getString(R.string.star_trek)),
                    Subject(getString(R.string.Batman_vs_superman), getString(R.string.batman_desc), getDrawable(R.drawable.b_v_s), getString(R.string.Batman), getString(R.string.Superman))


            )
        }
    }

    companion object {
        lateinit var instance: App

        val Addr = "192.168.4.185"
        val Port = 54890
    }
}