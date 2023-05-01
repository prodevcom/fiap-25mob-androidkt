package br.com.fiap25mob.mbamobile.presentation.guitarlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap25mob.mbamobile.data.db.GuitarsEntity
import br.com.fiap25mob.mbamobile.databinding.GuitarItemBinding

class GuitarListAdapter(private val guitarsEntity: List<GuitarsEntity>) :
    RecyclerView.Adapter<GuitarListAdapter.GuitarListViewHolder>() {

    var onItemClick: ((entity: GuitarsEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuitarListViewHolder {
        val view = GuitarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuitarListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuitarListViewHolder, position: Int) {
        holder.bindView(guitarsEntity[position])
    }

    override fun getItemCount() = guitarsEntity.size

    inner class GuitarListViewHolder(private val itemBinding: GuitarItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val textViewGuitarBrand = itemBinding.guitarName
        private val textViewGuitarModel = itemBinding.guitarModel

        fun bindView(guitarsEntity: GuitarsEntity) {
            textViewGuitarBrand.text = guitarsEntity.brand
            textViewGuitarModel.text = guitarsEntity.model
            itemBinding.root.setOnClickListener {
                onItemClick?.invoke(guitarsEntity)
            }
        }
    }
}