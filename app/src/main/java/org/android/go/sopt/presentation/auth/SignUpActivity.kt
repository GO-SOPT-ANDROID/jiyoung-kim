package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.domain.model.MyInfo
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.BindingActivity
import org.android.go.sopt.util.ViewModelFactory
import org.android.go.sopt.util.hideKeyboard

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: SignUpViewModel by viewModels { (ViewModelFactory(this)) }
    private var signUpInfo: MyInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        observeSignUpValid()
        hideKeyBoard()
        clickSignUpBtn()
    }

    private fun clickSignUpBtn() {
        binding.btnSignupRegister.setOnClickListener {
            Log.d("SignUp", viewModel.getInfo().toString())
            signUpInfo = viewModel.getInfo()
            viewModel.signUpValid()
            viewModel.saveUserInfo()
        }
    }

    private fun observeSignUpValid() {
        viewModel.isSignUpValid.observe(this) {
            when (it) {
                true -> {
                    val intent = Intent(this, SignInActivity::class.java).apply {
                        Log.d("SignUp", signUpInfo.toString())
                        putExtra("myInfo", signUpInfo)
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                }
                else -> {
                    Log.d("SignUp", "회원가입 실패")
                }
            }
        }
    }

    private fun hideKeyBoard() {
        binding.clSignupMain.setOnClickListener {
            it.hideKeyboard()
        }
    }
}
