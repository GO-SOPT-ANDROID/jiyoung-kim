package org.android.go.sopt.presentation.main.mypage

import android.os.Build
import android.os.Bundle
import android.util.Log
import org.android.go.sopt.R
import org.android.go.sopt.data.model.MyInfo
import org.android.go.sopt.databinding.ActivityMyPageBinding
import org.android.go.sopt.util.BindingActivity

class MyPageActivity : BindingActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {
    private var myInfo: MyInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myInfo = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("myInfo", MyInfo::class.java)
        } else {
            intent.getParcelableExtra("myInfo")
        }
        myInfo?.let {
            setText(it)
            Log.d("Home", it.name)
            Log.d("Home", it.specialty)
        }
    }

    private fun setText(myInfo: MyInfo) {
        if (myInfo != null) {
            with(binding) {
                "이름: ${myInfo.name}".also { tvMypageName.text = it }
                "특기: ${myInfo.specialty}".also { tvMypageSpecialty.text = it }
            }
        }
    }
}
