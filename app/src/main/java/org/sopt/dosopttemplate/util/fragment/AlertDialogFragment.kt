package org.sopt.dosopttemplate.util.fragment

import android.os.Bundle
import android.view.View
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.FragmentAlertDialogBinding
import org.sopt.dosopttemplate.util.binding.BindingDialogFragment

class AlertDialogFragment(
    private val title: String,
    private val negativeButtonLabel: String,
    private val positiveButtonLabel: String,
    val handleNegativeButton: () -> Unit,
    val handlePositiveButton: () -> Unit
) : BindingDialogFragment<FragmentAlertDialogBinding>(R.layout.fragment_alert_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setCanceledOnTouchOutside(false)
        initDialogText(title, positiveButtonLabel, negativeButtonLabel)
        initNegativeButtonClickListener(handleNegativeButton)
        initPositiveButtonClickListener(handlePositiveButton)
    }

    private fun initDialogText(
        title: String,
        positiveButtonLabel: String,
        negativeButtonLabel: String
    ) {
        binding.tvAlertFragmentTitle.text = title
        binding.tvAlertFragmentPositive.text = positiveButtonLabel
        binding.tvAlertFragmentNegative.text = negativeButtonLabel
    }

    private fun initNegativeButtonClickListener(handleNegativeButton: () -> Unit) {
        binding.tvAlertFragmentNegative.setOnClickListener {
            handleNegativeButton.invoke()
            dismiss()
        }
    }

    private fun initPositiveButtonClickListener(handlePositiveButton: () -> Unit) {
        binding.tvAlertFragmentPositive.setOnClickListener {
            handlePositiveButton.invoke()
            dismiss()
        }
    }
}