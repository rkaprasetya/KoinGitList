package com.raka.repolistkoin.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.raka.repolistkoin.BR
import com.raka.repolistkoin.data.model.compact.RepoCompact
import com.raka.repolistkoin.databinding.ItemRepoListBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_repo_list.view.*

class RepoListAdapter() :
    RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {
    var repoList: List<RepoCompact> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ItemRepoListBinding.inflate(inflater,parent,false)
        return ViewHolder(dataBinding)
    }

    override fun getItemCount() = repoList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setup(repoList[position])
    }

    fun updateList(repoList:List<RepoCompact>){
        this.repoList = repoList
        notifyDataSetChanged()
    }

    class ViewHolder constructor(private val dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        val avatarImage = itemView.item_avatar
        fun setup(itemData: RepoCompact) {
            dataBinding.setVariable(BR.itemData,itemData)
            dataBinding.executePendingBindings()
            Picasso.get().load(itemData.avatar_url).into(avatarImage)
            itemView.setOnClickListener {
                val bundle = bundleOf("url" to itemData.html_url)
            }
        }
    }


}