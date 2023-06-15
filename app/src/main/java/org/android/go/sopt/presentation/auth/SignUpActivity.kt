package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.go.sopt.R
import org.android.go.sopt.data.entity.MyInfo
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.LoadingDialog
import org.android.go.sopt.util.base.BindingActivity
import org.android.go.sopt.util.hideKeyboard
import org.android.go.sopt.util.state.UiState

@AndroidEntryPoint
class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog() }
    private val viewModel: SignUpViewModel by viewModels()
    private var signUpInfo: MyInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        hideKeyBoard()
        validInput()
        observeSignUpState()
        clearFocusLastEditText()
        clickSignUpBtn()
    }

    private fun hideKeyBoard() {
        binding.clSignupMain.setOnClickListener {
            it.hideKeyboard()
        }
    }
    private fun clickSignUpBtn() {
        binding.btnSignupRegister.setOnClickListener {
            signUpInfo = viewModel.getInfo()
            Log.d("SignUp", "signUpInfo :: $signUpInfo")
            Log.d("SignUp", "signUpInfo :: ${signUpInfo?.id ?: "id null"}")
            viewModel.signUp()
        }
    }

    private fun validInput() {
        viewModel.isIdValid.observe(this) { isIdValid ->
            Log.d("signUp", viewModel.isIdValid.value.toString())
            if (!isIdValid) {
                binding.edtSignupIdMain.error = getString(R.string.signup_id_invalid)
            } else {
                binding.edtSignupIdMain.error = null
                binding.edtSignupIdMain.isErrorEnabled = false
            }
        }
        viewModel.isPwdValid.observe(this) { isPwdValid ->
            Log.d("signUp", viewModel.isPwdValid.value.toString())
            if (!isPwdValid) {
                binding.edtSignupPwdMain.error = getString(R.string.signup_pwd_invalid)
            } else {
                binding.edtSignupPwdMain.error = null
                binding.edtSignupPwdMain.isErrorEnabled = false
            }
        }
    }

    private fun observeSignUpState() {
        viewModel.signUpState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> loadingDialog.show(supportFragmentManager, "LOADING_DIALOG")
                is UiState.Success -> intentToSignInActivity()
                is UiState.Failure -> Toast.makeText(this, getString(R.string.signup_failure_toast), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    /*
    ui State 적용 전 회원가입 로직 처리 코드
    private fun observeSignUpResult() {
        viewModel.isSignUpSuccess.observe(
            this,
            EventObserver { isSignUpSuccess ->
                if (isSignUpSuccess) {
                    intentToSignInActivity()
                } else {
                    binding.root.showSnackbar("회원가입 실패ㅠ")
                }
                Log.d("signUp", "isSignUpSuccess :: ${viewModel.isSignUpSuccess.value}")
            }
        )
    }

     */

    private fun intentToSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java).apply {
            Log.d("SignUp", "signUpInfo :: $signUpInfo")
            putExtra("myInfo", signUpInfo)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun clearFocusLastEditText() {
        binding.clSignupMain.setOnClickListener {
            binding.edtSignupSpecialty.clearFocus()
        }
    }
}
