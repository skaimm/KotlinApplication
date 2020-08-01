package com.smtkcbs.kotlinapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.smtkcbs.kotlinapplication.R
import com.smtkcbs.kotlinapplication.databinding.FragmentFoodDetailsBinding
import com.smtkcbs.kotlinapplication.utils.downloadImage
import com.smtkcbs.kotlinapplication.viewmodel.FoodDetailsViewModel
import kotlinx.android.synthetic.main.fragment_food_details.*

class FoodDetailsFragment : Fragment() {

    private lateinit var viewModel : FoodDetailsViewModel
    private var foodID = 0
    private lateinit var dataBinding : FragmentFoodDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_food_details,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            foodID = FoodDetailsFragmentArgs.fromBundle(it).foodID
        }

        viewModel = ViewModelProviders.of(this).get(FoodDetailsViewModel::class.java)
        viewModel.getDataFromRoom(foodID)

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.foodData.observe(viewLifecycleOwner, Observer {

            it?.let {
                dataBinding.choosenFood = it
            }

            /*

            // using before the data binding
            it?.let {

                foodName.text = it.foodName
                foodCalorie.text = it.foodCalorie
                foodCarbohydrate.text = it.foodCarbohydrate
                foodProtein.text = it.foodProtein
                foodFat.text = it.foodFat
                foodImage.downloadImage(it.foodImage)
            }*/
        })
    }

}