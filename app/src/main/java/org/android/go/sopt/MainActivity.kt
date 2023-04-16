package org.android.go.sopt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.presentation.main.gallery.GalleryFragment
import org.android.go.sopt.presentation.main.home.HomeFragment
import org.android.go.sopt.presentation.main.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickNavigationBtn()
        scrollToTop()
//        supportFragmentManager.beginTransaction().add(R.id.fcv_main, HomeFragment()).commit()
    }

    private fun clickNavigationBtn() {
        binding.bnvMain.setOnItemSelectedListener { item ->
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

    private fun scrollToTop() {
        binding.bnvMain.setOnItemReselectedListener { item ->
            if (item.itemId == R.id.menu_home) {
                val homeFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)
                homeFragment?.let {
                    Log.d("mainn", homeFragment.toString())
                    // home fragment view에 접근
                    val homeBinding = FragmentHomeBinding.bind(it.requireView())
                    homeBinding.rcvHomeView.scrollToPosition(0)
                }
            }
        }
    }
}
