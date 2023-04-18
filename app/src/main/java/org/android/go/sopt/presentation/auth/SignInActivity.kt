package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.data.model.MyInfo
import org.android.go.sopt.databinding.ActivitySignInBinding
import org.android.go.sopt.presentation.main.mypage.MyPageActivity
import org.android.go.sopt.util.BindingActivity
import org.android.go.sopt.util.hideKeyboard
import org.android.go.sopt.util.showSnackbar
import org.android.go.sopt.util.showToast

class SignInActivity : BindingActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private val viewModel: AuthViewModel by viewModels()
    private var myInfo: MyInfo? = null

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                myInfo = if (Build.VERSION.SDK_INT >= 33) {
                    result.data?.getParcelableExtra("myInfo", MyInfo::class.java)
                } else {
                    result.data?.getParcelableExtra("myInfo")
                }
                binding.root.showSnackbar("회원가입 완료!")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        clickButton()
        hideKeyBoard()
    }

    private fun hideKeyBoard() {
        binding.root.setOnClickListener {
            it.hideKeyboard()
        }
    }

    private fun clickButton() {
        with(binding) {
            // 로그인 버튼
            btnSigninBottom.setOnClickListener {
                if (myInfo?.id != null && myInfo?.pwd != null) {
                    viewModel.isSignInValid(myInfo!!.id, myInfo!!.pwd)
                    viewModel.isSignTextInValid.observe(this@SignInActivity) {
                        Log.d("SignIn", viewModel.isSignTextInValid.value.toString())
                        if (viewModel.isSignTextInValid.value == true) {
                            binding.root.showToast("로그인 성공!")
                            Intent(this@SignInActivity, MyPageActivity::class.java).apply {
                                putExtra("myInfo", myInfo)
                                startActivity(this)
                                finish()
                            }
                        } else {
                            binding.root.showToast("로그인 실패 ㅜㅜ")
                        }
                    }
                } else {
                    binding.root.showToast("로그인 정보를 입력해주세요!")
                }
            }

            // 회원가입 버튼
            btnSigninSignup.setOnClickListener {
                launcher.launch(Intent(this@SignInActivity, SignUpActivity::class.java))
            }
        }
    }
}
