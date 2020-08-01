package com.smtkcbs.kotlinapplication.adapter

import android.database.DatabaseUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.smtkcbs.kotlinapplication.R
import com.smtkcbs.kotlinapplication.databinding.FoodRecyclerRowBinding
import com.smtkcbs.kotlinapplication.model.Food
import com.smtkcbs.kotlinapplication.utils.downloadImage
import com.smtkcbs.kotlinapplication.view.FoodListFragmentDirections
import kotlinx.android.synthetic.main.food_recycler_row.view.*

class FoodRecyclerAdapter(val foodList : ArrayList<Food>) : RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>(), FoodClickListener{
    class FoodViewHolder(var dataView: FoodRecyclerRowBinding) : RecyclerView.ViewHolder(dataView.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.food_recycler_row,parent,false)
        val view = DataBindingUtil.inflate<FoodRecyclerRowBinding>(inflater,R.layout.food_recycler_row,parent,false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.dataView.food = foodList.get(position)
        holder.dataView.listener = this

        /*

        // before using data binding

        holder.itemView.name.text = foodList.get(position).foodName
        holder.itemView.calorie.text = foodList.get(position).foodCalorie
        holder.itemView.image.downloadImage(foodList.get(position).foodImage)

        holder.itemView.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailsFragment(foodList.get(position).foodID)
            Navigation.findNavController(it).navigate(action)
        }*/
    }

    fun updateFoodList( newFoodList : List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun clickledFood(view: View) {
        val foodID = view.foodID.text.toString().toIntOrNull()
        foodID?.let {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailsFragment(it)
            Navigation.findNavController(view).navigate(action)
        }
    }
}