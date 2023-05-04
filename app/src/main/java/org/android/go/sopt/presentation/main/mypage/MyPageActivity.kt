package org.android.go.sopt.presentation.main.mypage

import android.os.Bundle
import android.util.Log
import org.android.go.sopt.R
import org.android.go.sopt.data.model.MyInfo
import org.android.go.sopt.databinding.ActivityMyPageBinding
import org.android.go.sopt.util.BindingActivity
import org.android.go.sopt.util.getParcelable

class MyPageActivity : BindingActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {
    private var myInfo: MyInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myInfo = intent.getParcelable("myInfo", MyInfo::class.java)
        myInfo?.let {
            setText(it)
            Log.d("Home", it.name)
            Log.d("Home", it.specialty)
        }
    }

    private fun setText(myInfo: MyInfo) {
        with(binding) {
            "이름: ${myInfo.name}".also { tvMypageName.text = it }
            "특기: ${myInfo.specialty}".also { tvMypageSpecialty.text = it }
        }
    }
}
