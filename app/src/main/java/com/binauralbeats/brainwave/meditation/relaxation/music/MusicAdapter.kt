package com.binauralbeats.brainwave.meditation.relaxation.music

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(
    var imageList: ArrayList<Int>,
    var nameList: ArrayList<String>,
    var audioBeats: ArrayList<Int>,
    var activity: Activity
) : RecyclerView.Adapter<MusicAdapter.MusicViewHoldewr>() {

    class MusicViewHoldewr(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image1: ImageView = itemView.findViewById(R.id.image)
        var txtName: TextView = itemView.findViewById(R.id.text)
        var card_View: CardView = itemView.findViewById(R.id.card_View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHoldewr {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return MusicViewHoldewr(view)
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

    override fun onBindViewHolder(holder: MusicViewHoldewr, position: Int) {
        holder.image1.setImageResource(imageList.get(position))
        holder.txtName.text = nameList.get(position)
        holder.card_View.setOnClickListener {

            val intent = Intent(activity, MusicActivity::class.java)
            var beatName: String = nameList.get(position)
            var imageName: Int = imageList.get(position)
            var currentAudio: Int = audioBeats.get(position)
//            intent.putExtra("nameList", nameList)
//            intent.putExtra("imageList", imageList)
//            intent.putExtra("audioBeats", audioBeats)
            intent.putExtra("beatName", beatName)
            intent.putExtra("currentAudio", currentAudio)
            intent.putExtra("position", position)
            intent.putExtra("imageName", imageName)


            activity.startActivity(intent)


        }


    }


}
