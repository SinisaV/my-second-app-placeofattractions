package com.example.placeofattractions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lib.Attraction
import com.google.android.material.imageview.ShapeableImageView

@Suppress("DEPRECATION")
class MyAdapter(private val listOfAttractions : MutableList<Attraction>, private val recyclerViewInterface: RecyclerViewInterface) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_item, parent, false)
        return MyViewHolder(itemView, recyclerViewInterface)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemsViewModel = listOfAttractions[position]
        holder.imageView.setImageResource(R.drawable.recycleritemimg)
        holder.name.text = itemsViewModel.name
        holder.info.text = itemsViewModel.info
        holder.year.text = itemsViewModel.year.toString()
    }

    override fun getItemCount(): Int {
        return listOfAttractions.size
    }


    class MyViewHolder(ItemView: View, recyclerViewInterface : RecyclerViewInterface) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ShapeableImageView = itemView.findViewById(R.id.imageAttraction)
        val name: TextView = itemView.findViewById(R.id.textViewName)
        val info: TextView = itemView.findViewById(R.id.textViewInfo)
        val year: TextView = itemView.findViewById(R.id.textViewYear)

        init {
            itemView.setOnLongClickListener{
                val myPos = adapterPosition

                if (myPos != RecyclerView.NO_POSITION) {
                    recyclerViewInterface.onItemLongCLick(myPos)
                }
                return@setOnLongClickListener true
            }
            itemView.setOnClickListener {
                val myPos = adapterPosition

                if (myPos != RecyclerView.NO_POSITION) {
                    recyclerViewInterface.onItemClick(myPos)
                }
            }
        }
    }
}