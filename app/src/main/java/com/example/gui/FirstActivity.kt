package com.example.gui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gui.databinding.FragmentFirstBinding

class FirstActivity : Fragment() {
    companion object {
        const val ButtonState = "ButtonState"
    }

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notValidGrades = context?.getString(R.string.errorGradeNotInAScope)
        val emptyField = context?.getString(R.string.errorMissingText)
        val notValidText = context?.getString(R.string.errorNotValidText)

        val regex = Regex("[^A-Za-z ]+")

        if (savedInstanceState?.getString(ButtonState) == "Visible") {
            binding.submitButton.visibility = View.VISIBLE
        } else {
            binding.submitButton.visibility = View.INVISIBLE
        }

        binding.gradesEditText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.gradesEditText.text.isNotEmpty()) {
                    val grades = binding.gradesEditText.text.toString().toInt()
                    if (!((grades <= 15) and (grades >= 5))) {
                        binding.gradesEditText.error = notValidGrades
                        Toast.makeText(context, notValidGrades, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    binding.gradesEditText.error = emptyField
                    Toast.makeText(context, emptyField, Toast.LENGTH_SHORT).show()
                }
                showButton()
            }
        }
        binding.nameEditText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.nameEditText.text.isEmpty()) {
                    binding.nameEditText.error = emptyField
                    Toast.makeText(context, emptyField, Toast.LENGTH_SHORT).show()
                }
                if (binding.nameEditText.text.matches(regex)){
                    binding.nameEditText.error = notValidText
                    Toast.makeText(context, notValidText, Toast.LENGTH_SHORT).show()
                }
                showButton()
            }
        }
        binding.surnameEditText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.surnameEditText.text.isEmpty()) {
                    binding.surnameEditText.error = emptyField
                    Toast.makeText(context, emptyField, Toast.LENGTH_SHORT).show()
                }
                if (binding.surnameEditText.text.matches(regex)){
                    binding.surnameEditText.error = notValidText
                    Toast.makeText(context, notValidText, Toast.LENGTH_SHORT).show()
                }
                showButton()
            }
        }
        binding.submitButton.setOnClickListener {
            val bundle = bundleOf(
                "grades_count" to binding.gradesEditText.text.toString(),
                "name" to binding.nameEditText.text.toString(),
                "surname" to binding.surnameEditText.text.toString()
            )
            val fragment = SecondActivity()
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_FirstActivity_to_SecondActivity, bundle)

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        if (this.arguments?.getString("grades_avg") != null) {
            binding.sredniaOcen.text = context?.getString(R.string.your_srednia_label) + " " + String.format("%.2f", this.arguments?.getString("grades_avg").toString().toDouble())
            binding.sredniaOcen.visibility = View.VISIBLE
            binding.nameEditText.text = this.arguments?.getString("name").toString().toEditable()
            binding.surnameEditText.text = this.arguments?.getString("surname").toString().toEditable()
            binding.gradesEditText.text = this.arguments?.getString("grades_count").toString().toEditable()
            binding.submitButton.visibility = View.VISIBLE
            if (this.arguments?.getString("grades_avg").toString().toDouble() >= 3.0) {
                binding.submitButton.text = context?.getString(R.string.congrats)
                buttonExit("Gratulacje!")
            } else {
                binding.submitButton.text = context?.getString(R.string.oh_no)
                buttonExit("Warunek!")
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) { // Here You have to save count value
        super.onSaveInstanceState(outState)
        if (binding.submitButton.visibility == View.VISIBLE) {
            outState.putString(ButtonState, "Visible")
        } else {
            outState.putString(ButtonState, "Invisible")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateGrades(): String? {
        return if (binding.gradesEditText.text.isNotEmpty()) {
            val grades = binding.gradesEditText.text.toString().toInt()
            if ((grades <= 15) and (grades >= 5)) {
                null
            } else {
                "Error"
            }
        } else {
            "Error"
        }
    }

    private fun validateName(): String? {
        return if (binding.nameEditText.text.isNotEmpty()) {
            null
        } else "Error"
    }

    private fun validateSurname(): String? {
        return if (binding.surnameEditText.text.isNotEmpty()) {
            null
        } else "Error"
    }

    private fun showButton() {
        if ((validateGrades() == null) and (validateName() == null) and (validateSurname() == null)) {
            binding.submitButton.visibility = View.VISIBLE
        } else {
            binding.submitButton.visibility = View.INVISIBLE
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun buttonExit(exitText: String) {
        binding.submitButton.setOnClickListener {
            Toast.makeText(context, exitText, Toast.LENGTH_SHORT).show()
            activity?.finishAffinity()
        }
    }
}