package com.codersmind.mybankapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.codersmind.mybankapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @Inject

    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loginButton.setOnClickListener {

            viewModel.tryLogin(
                loginInput.text.toString().trim(),
                passwordInput.text.toString().trim()
            )

                .observe(viewLifecycleOwner) { loginState ->
                when (loginState) {
                    LoginState.SUCCESS -> onSuccessfulLogin()
                    LoginState.FAIL -> onFailedLogin()
                    else -> {}
                }
            }
        }
    }


    private fun onSuccessfulLogin() {
        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
    }


    private fun onFailedLogin() {
        Toast.makeText(this.context,"Błąd logowania!", Toast.LENGTH_LONG).show();
    }
}