package org.android.go.sopt.presentation.main.home

import androidx.lifecycle.ViewModel
import org.android.go.sopt.R
import org.android.go.sopt.data.model.Music

class HomeViewModel : ViewModel() {
    val itemList: List<Music> =
        listOf(
            Music(R.drawable.kitkat, 1, "Paul Blanco", "Star Ceiling"),
            Music(R.drawable.kitkat, 2, "Jay Sean", "Do you love me"),
            Music(R.drawable.kitkat, 3, "pH-1", "ZOMBIES"),
            Music(R.drawable.kitkat, 4, "G-DRAGON", "결국 (Feat. 로제 of BLACKPINK)"),
            Music(R.drawable.kitkat, 5, "쿠기(Coogie)", "Justin Bieber (Feat. 박재범)"),
            Music(R.drawable.kitkat, 6, "Drake", "Do not Disturb"),
            Music(R.drawable.kitkat, 7, "Hoody(후디)", "Baby Oh Baby"),
            Music(R.drawable.kitkat, 8, "ASH ISLAND", "Play (Prod. TOIL)"),
            Music(R.drawable.kitkat, 9, "SZA", "I Hate U"),
            Music(R.drawable.kitkat, 10, "처음 마주쳤을 때처럼", "TOIL, GIST")

        )
}
