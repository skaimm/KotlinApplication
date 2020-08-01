package com.smtkcbs.kotlinapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.smtkcbs.kotlinapplication.R
import com.smtkcbs.kotlinapplication.adapter.FoodRecyclerAdapter
import com.smtkcbs.kotlinapplication.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*

class FoodListFragment : Fragment() {

    private lateinit var viewModel : FoodListViewModel
    private val recyclerFoodAdapter = FoodRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()

        foodListRecycler.layoutManager = LinearLayoutManager(context)
        foodListRecycler.adapter = recyclerFoodAdapter

        swipeResfreshLayout.setOnRefreshListener {
            progressBar.visibility = View.VISIBLE
            errorText.visibility = View.GONE
            foodListRecycler.visibility = View.GONE
            viewModel.refresh()
            swipeResfreshLayout.isRefreshing = false
        }
        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.foods.observe(viewLifecycleOwner, Observer {
            it.let {

                foodListRecycler.visibility = View.VISIBLE
                recyclerFoodAdapter.updateFoodList(it)
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it.let {
                if(it){

                    foodListRecycler.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    errorText.visibility = View.VISIBLE
                }else{
                    errorText.visibility = View.GONE
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it.let {
                if(it){
                    foodListRecycler.visibility = View.GONE
                    errorText.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }else{
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

}