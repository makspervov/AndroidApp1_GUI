package com.example.gui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterTablicy(var lista_ocen: List<ModelOceny>) :
    RecyclerView.Adapter<AdapterTablicy.OcenyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OcenyViewHolder {
        return OcenyViewHolder(
            lista_ocen,
            LayoutInflater.from(parent.context).inflate(R.layout.wiersz_oceny, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return lista_ocen.size
    }

    override fun onBindViewHolder(holder: OcenyViewHolder, position: Int) {
        val currentItem = lista_ocen[position]
        holder.no.text = currentItem.nazwa
        holder.rgg.tag = position
        val buttonToCheck = when (currentItem.ocena) {
            3 -> R.id.radio_button_three
            4 -> R.id.radio_button_four
            5 -> R.id.radio_button_five
            else -> R.id.radio_button_two
        }
        holder.rgg.check(buttonToCheck)
    }


    class OcenyViewHolder(var lista_ocen: List<ModelOceny>, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val rgg: RadioGroup = itemView.findViewById(R.id.radio_group_grades)
        val no: TextView = itemView.findViewById(R.id.nazwa_oceny)

        init {
            rgg.setOnCheckedChangeListener { _, id ->
                val index = rgg.tag as Int
                when (id) {
                    R.id.radio_button_two -> lista_ocen[index].ocena = 2
                    R.id.radio_button_three -> lista_ocen[index].ocena = 3
                    R.id.radio_button_four -> lista_ocen[index].ocena = 4
                    R.id.radio_button_five -> lista_ocen[index].ocena = 5
                    else -> {}
                }
            }
        }
    }

}
