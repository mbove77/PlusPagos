package com.bove.martin.pluspagos.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.presentation.MainActivity
import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import com.bove.martin.pluspagos.presentation.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_amoun.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AmountFragment : Fragment() {

    private val viewModel: MainActivityViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_amoun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.amount_fragment_tittle)
        if ((activity as MainActivity).bottomSheetIsVisible) (activity as MainActivity).hideBottomSheet()

        buttonPayment.setOnClickListener {
                viewModel.validateAmount(edAmount.getNumericValue())
                it.hideKeyboard()
        }

        viewModel.amountIsValid.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.result) {
                    viewModel.setUserAmount(edAmount.getNumericValue()!!)
                    findNavController().navigate(R.id.action_amounFragment_to_paymentMethodsFr)
                } else {
                    edAmount.error = it.errorMessage
                }
            }
        })

    }

}