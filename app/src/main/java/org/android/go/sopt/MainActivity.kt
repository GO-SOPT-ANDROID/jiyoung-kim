package org.android.go.sopt

import android.os.Bundle
import android.util.Log
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
//        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)
//        if (currentFragment == null) {
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fcv_main, HomeFragment())
//                .commit()
//        }
        supportFragmentManager.beginTransaction().add(R.id.fcv_main, HomeFragment()).commit()
    }

    private fun clickNavigationBtn() {
        binding.bnvHome.setOnItemSelectedListener { item ->
            Log.d("main", "clickNavigationBtn() called")
            changeFragment(
                when (item.itemId) {
                    R.id.menu_home -> {
                        Log.d("main", "changeFragment() 호출 fragment = ${item.itemId}")
                        HomeFragment()
                    }
                    R.id.menu_gallery -> {
                        Log.d("main", "changeFragment() 호출 fragment = ${item.itemId}")
                        GalleryFragment()
                    }
                    else -> {
                        Log.d("main", "changeFragment() 호출 fragment = ${item.itemId}")
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
