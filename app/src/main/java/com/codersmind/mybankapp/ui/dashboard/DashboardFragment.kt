package com.codersmind.mybankapp.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.codersmind.mybankapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboardragment.*
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    @Inject
    lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboardragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        sendBtn.setOnClickListener {
            processOnMoneySend()
        }


        logout.setOnClickListener {
            onLogout()
        }
    }


    private fun onLogout() = context?.let {
        MaterialAlertDialogBuilder(it).setTitle("Attention!")
            .setMessage("Do you really want to log out?")
            .setPositiveButton("Yes") { _, _ -> findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment) }
            .setNegativeButton("No") { _, _ ->

            }.show()
    }


    override fun onResume() {
        super.onResume()


        updateView()
    }


    private fun processOnMoneySend() {

        val validationState =
            viewModel.validate(accountNumberText.text.toString(), cashToSend.text.toString())


        if (validationState != ValidationState.OK) {
            showDialog(

                viewModel.validationStateToMessage(validationState)
            )


            return;
        }


        viewModel.sendMoney(accountNumberText.text.toString(), cashToSend.text.toString())


        updateView()


        context?.let {
            Toast.makeText(
                it,
                "You send ${cashToSend.text}$ to ${accountNumberText.text}",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun updateView() {

        loginText.text = "Witaj ${viewModel.loginName}"


        accountNrText.text = viewModel.accountNumber


        balance.text = viewModel.balance


        historyView.adapter = ArrayAdapter<String>(
            this.requireContext(),
            android.R.layout.simple_list_item_1,
            viewModel.history.toTypedArray()
        )
    }

    private fun showDialog(text: String) = context?.let {
        MaterialAlertDialogBuilder(it).setTitle("Attention!")
            .setMessage(text).show()
    }
}