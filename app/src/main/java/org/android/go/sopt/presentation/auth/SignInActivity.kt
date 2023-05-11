package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import org.android.go.sopt.MainActivity
import org.android.go.sopt.R
import org.android.go.sopt.data.model.MyInfo
import org.android.go.sopt.databinding.ActivitySignInBinding
import org.android.go.sopt.util.*

class SignInActivity : BindingActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private val viewModel: SignInViewModel by viewModels { (ViewModelFactory(applicationContext)) }
    private var signUpInfo: MyInfo? = null

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK) {
                val data = result.data ?: return@registerForActivityResult
                data.getParcelable("myInfo", MyInfo::class.java).apply {
                    this?.let {
                        signUpInfo = it
                        viewModel.getSignUpInfo(it)
                        binding.root.showSnackbar("회원가입 완료!")
                    }
                }
            } else {
                return@registerForActivityResult
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        observeAutoSignIn()
        hideKeyBoard()
        clickButton()
    }

    private fun hideKeyBoard() {
        binding.root.setOnClickListener {
            it.hideKeyboard()
        }
    }

    private fun observeAutoSignIn() {
        viewModel.isAutoSignInValid.observe(this) {
            when (it) {
                true -> {
                    Intent(this, MainActivity::class.java).apply {
                        startActivity(this)
                    }
                }
                else -> {
                    Log.d("SignIn", "자동 로그인 오류")
                }
            }
        }
    }

    private fun clickButton() {
        with(binding) {
            // 로그인 버튼
            btnSigninBottom.setOnClickListener {
                Log.d("SignIn", "내가 입력한 id:: ${viewModel.id.value}")
                Log.d("SignIn", "내가 입력한 pwd:: ${viewModel.pwd.value}")
                Log.d(
                    "SignIn",
                    "ss :: ${signUpInfo?.id} ooo ${signUpInfo?.pwd} 000 ${signUpInfo?.name} 000 ${signUpInfo?.specialty}"
                )
                viewModel.signInValid(signUpInfo!!.id, signUpInfo!!.pwd)
                root.showToast("로그인 성공!")
            }

            // 회원가입 버튼
            tvSigninSignup.setOnClickListener {
                launcher.launch(Intent(this@SignInActivity, SignUpActivity::class.java))
            }
        }
    }
}
