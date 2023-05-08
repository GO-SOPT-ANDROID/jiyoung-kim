package org.android.go.sopt.presentation.main.mypage

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.android.go.sopt.databinding.FragmentMypageBinding
import org.android.go.sopt.presentation.auth.SignInActivity
import org.android.go.sopt.util.ViewModelFactory

class MypageFragment : Fragment() {
    private val viewModel: MypageViewModel by viewModels { (ViewModelFactory(requireContext())) }

    private var _binding: FragmentMypageBinding? = null
    private val binding: FragmentMypageBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.vm = viewModel
        super.onViewCreated(view, savedInstanceState)
        clickWithdrawBtn()
    }

    private fun clickWithdrawBtn() {
        binding.btnMypageWithdraw.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val intent = Intent(activity, SignInActivity::class.java)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("잠깐!")
            .setMessage("회원탈퇴 하시겠어요?")
            .setPositiveButton(
                "확인",
                DialogInterface.OnClickListener { dialog, id ->
                    viewModel.deleteUser()
                    startActivity(intent)
                }
            )
            .setNegativeButton(
                "취소",
                DialogInterface.OnClickListener { dialog, id ->
                }
            )
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
