package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.go.sopt.R
import org.android.go.sopt.data.entity.MyInfo
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.BindingActivity
import org.android.go.sopt.util.hideKeyboard
import org.android.go.sopt.util.showSnackbar

@AndroidEntryPoint
class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: SignUpViewModel by viewModels()
    private var signUpInfo: MyInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        hideKeyBoard()
        validInput()
        observeSignUpResult()
        clickSignUpBtn()
        clearFocusLastEditText()
    }

    private fun clickSignUpBtn() {
        binding.btnSignupRegister.setOnClickListener {
            signUpInfo = viewModel.getInfo()
            Log.d("SignUp", "signUpInfo :: $signUpInfo")
            Log.d("SignUp", "signUpInfo :: ${signUpInfo?.id ?: "id null"}")
            viewModel.saveUserInfo()
        }
    }

    private fun observeSignUpResult() {
        viewModel.isSignUpSuccess.observe(this) { isSignUpSuccess ->
            if (isSignUpSuccess) {
                intentToSignInActivity()
            } else {
                binding.root.showSnackbar("회원가입 실패ㅠ")
            }
            Log.d("signUp", "isSignUpSuccess :: ${viewModel.isSignUpSuccess.value}")
        }
    }

    private fun intentToSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java).apply {
            Log.d("SignUp", "signUpInfo :: $signUpInfo")
            putExtra("myInfo", signUpInfo)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun hideKeyBoard() {
        binding.clSignupMain.setOnClickListener {
            it.hideKeyboard()
        }
    }

    private fun validInput() {
        viewModel.isIdValid.observe(this) { isIdValid ->
            Log.d("signUp", viewModel.isIdValid.value.toString())
            if (!isIdValid) {
                binding.edtSignupIdMain.error = "아이디 형식이 잘못 되었어요 :("
            } else {
                binding.edtSignupIdMain.error = null
                binding.edtSignupIdMain.isErrorEnabled = false
            }
        }
        viewModel.isPwdValid.observe(this) { isPwdValid ->
            Log.d("signUp", viewModel.isPwdValid.value.toString())
            if (!isPwdValid) {
                binding.edtSignupPwdMain.error = "비밀번호 형식이 잘못 되었어요 :("
            } else {
                binding.edtSignupPwdMain.error = null
                binding.edtSignupPwdMain.isErrorEnabled = false
            }
        }
    }

    private fun clearFocusLastEditText() {
        binding.clSignupMain.setOnClickListener {
            binding.edtSignupSpecialty.clearFocus()
        }
    }
}
