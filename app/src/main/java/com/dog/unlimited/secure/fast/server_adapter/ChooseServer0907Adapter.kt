package com.dog.unlimited.secure.fast.server_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dog.unlimited.secure.fast.R
import com.dog.unlimited.secure.fast.dog.getServerIcon
import com.dog.unlimited.secure.fast.dog.show
import com.dog.unlimited.secure.fast.ss0907bean.Ss0807ServerData
import com.dog.unlimited.secure.fast.ss0907server.Ss0907ConnectMa
import kotlinx.android.synthetic.main.layout_choose_server_item.view.*

class ChooseServer0907Adapter (
    private val context: Context,
    private val list:ArrayList<Ss0807ServerData>,
    private val click:(data:Ss0807ServerData)->Unit
        ):RecyclerView.Adapter<ChooseServer0907Adapter.ChooseServerView>() {

    inner class ChooseServerView(view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                click.invoke(list[layoutPosition])
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseServerView {
        return ChooseServerView(LayoutInflater.from(context).inflate(R.layout.layout_choose_server_item,parent,false))
    }

    override fun onBindViewHolder(holder: ChooseServerView, position: Int) {
        with(holder.itemView){
            val data = list[position]
            item_server_name.text=data.dog_0907_country
            item_server_icon.setImageResource(getServerIcon(data.dog_0907_country))

            item_server_s.show(data.dog_0907_host == Ss0907ConnectMa.getCurrentServerData().dog_0907_host)
        }
    }
}