package org.android.go.sopt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.presentation.home.GalleryFragment
import org.android.go.sopt.presentation.home.HomeFragment
import org.android.go.sopt.presentation.home.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickNavigationBtn()
//        supportFragmentManager.beginTransaction().add(R.id.fcv_main, HomeFragment()).commit()
    }

    private fun clickNavigationBtn() {
        binding.bnvHome.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.menu_home -> {
                        HomeFragment()
                    }
                    R.id.menu_gallery -> {
                        GalleryFragment()
                    }
                    else -> {
                        SearchFragment()
                    }
                }
            )
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .commit()
    }
}
