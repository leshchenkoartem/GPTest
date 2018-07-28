package com.gp.gptest.view.history

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gp.gptest.R
import com.gp.gptest.model.LocationHistory
import java.text.SimpleDateFormat
import java.util.*


/**
 *  Created by artem on 28.07.2018.
 */
class HtoriyAdapter : PagedListAdapter<LocationHistory, HtoriyAdapter.HistoryViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_row, parent, false)

        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val location = currentList?.get(position)
        holder.ltTv.text = location?.lat.toString()
        holder.lnTv.text = location?.lon.toString()
        holder.accurTv.text = location?.accuracy.toString()
        holder.providerTv.text = location?.provider

        val date = Date(location?.time?:0)
        holder.dateTv.text = SimpleDateFormat.getDateTimeInstance().format(date)

    }

    class HistoryViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val lnTv: TextView
        val ltTv: TextView
        val dateTv: TextView
        val accurTv: TextView
        val providerTv: TextView

        init {
            ltTv = v.findViewById(R.id.ltTv)
            lnTv = v.findViewById(R.id.lnTv)
            dateTv = v.findViewById(R.id.dateTv)
            accurTv = v.findViewById(R.id.accurTv)
            providerTv = v.findViewById(R.id.providerTv)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
                DiffUtil.ItemCallback<LocationHistory>() {
            override fun areItemsTheSame(old: LocationHistory,
                                         new: LocationHistory): Boolean =
                    old.id == new.id

            override fun areContentsTheSame(old: LocationHistory,
                                            new: LocationHistory): Boolean =
                    old == new
        }
    }

}