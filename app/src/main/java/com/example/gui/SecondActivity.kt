package com.example.gui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gui.databinding.FragmentSecondBinding

class SecondActivity : Fragment() {

    private lateinit var listaOcen: RecyclerView
    private var mDane: ArrayList<ModelOceny> = arrayListOf()
    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listaOcen = requireView().findViewById(R.id.lista_ocen_rv)
        listaOcen.layoutManager = LinearLayoutManager(context)
        val classes = resources.getStringArray(R.array.classes)
        for (i in 0 until this.arguments?.getString("grades_count").toString().toInt()) {
            mDane.add(ModelOceny(classes[i], 5))
        }
        listaOcen.adapter = AdapterTablicy(mDane)
        binding.obliczSrednia.setOnClickListener {
            val bundle2 = bundleOf(
                "grades_avg" to obliczSrednia(mDane).toString(),
                "grades_count" to this.arguments?.getString("grades_count").toString(),
                "name" to this.arguments?.getString("name"),
                "surname" to this.arguments?.getString("surname").toString()
            )
            val fragment = FirstActivity()
            fragment.arguments = bundle2
            findNavController().navigate(R.id.action_SecondActivity_to_FirstActivity, bundle2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun obliczSrednia(oceny: List<ModelOceny>): Double {
        return (oceny.sumOf { x -> x.ocena }.toDouble() / oceny.size.toDouble())
    }
}