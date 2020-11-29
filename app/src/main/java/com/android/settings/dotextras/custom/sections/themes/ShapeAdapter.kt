package com.android.settings.dotextras.custom.sections.themes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.settings.dotextras.R
import com.android.settings.dotextras.custom.utils.ResourceHelper
import com.android.settings.dotextras.system.OverlayController

class ShapeAdapter(
    private val overlayController: OverlayController,
    private val items: ArrayList<Shape>
) :
    RecyclerView.Adapter<ShapeAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_shapes,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shape: Shape = items[position]
        holder.label.text = shape.label
        holder.preview.setImageDrawable(
            overlayController.Shapes().createShapeDrawable(holder.preview.context, shape.path)
        )
        holder.shapeLayout.setOnClickListener {
            select(position)
            overlayController.Shapes().setOverlay(shape.packageName, shape, holder)
        }
        updateSelection(shape, holder)
    }

    private fun updateSelection(shape: Shape, holder: ViewHolder) {
        val accentColor: Int = ResourceHelper.getAccent(holder.shapeLayout.context)
        if (shape.selected) {
            holder.shapeLayout.setBackgroundColor(accentColor)
            holder.shapeLayout.invalidate()
        } else {
            holder.shapeLayout.setBackgroundColor(
                ContextCompat.getColor(
                    holder.shapeLayout.context,
                    android.R.color.transparent
                )
            )
            holder.shapeLayout.invalidate()
        }
    }

    private fun select(pos: Int) {
        for (i in items.indices) {
            items[i].selected = pos == i
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val shapeLayout: LinearLayout = view.findViewById(R.id.shapeLayout)
        val label: TextView = view.findViewById(R.id.shapeLabel)
        val preview: ImageView = view.findViewById(R.id.shapePreview)
    }

}