package com.example.myapplicationrecipi.ui.fragment.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationrecipi.MainViewModel
import com.example.myapplicationrecipi.R
import com.example.myapplicationrecipi.adapter.RecipsAdapter
import com.example.myapplicationrecipi.util.Constants.Companion.api_Key
import com.example.myapplicationrecipi.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recepi.view.*


@AndroidEntryPoint
class RecepiFragment : Fragment() {
private lateinit var mainViewModel: MainViewModel
private  lateinit var newRecsView:RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private lateinit var mview: View
    private val mAdapter  by lazy { RecipsAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragme

        mview  =   inflater.inflate(R.layout.fragment_recepi, container, false)
       mainViewModel  = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        showShimmerEffect()
        getData()
        return  mview

        
    }

    private fun getData() {
    mainViewModel.getRecipes(applyQuesry())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data.let { mAdapter.setData(it!!) }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(), response.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Toast.makeText(
                        requireContext(), "Loaingggggg",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    private fun applyQuesry():HashMap<String,String> {
    val query:HashMap<String,String>  =  HashMap()
        query["number"] = "50"
        query["apiKey"] = api_Key
        query["addRecipeNutrition"] = "true"
        query["fillIngredients"] = "true"
        return query
    }
private fun showShimmerEffect(){
    mview.recepiRecycler.adapter  = mAdapter
    mview.recepiRecycler.layoutManager = LinearLayoutManager(requireContext())

}
//         recepiRecycler.apply {
//            // set a LinearLayoutManager to handle Android
//            // RecyclerView behavior
//            layoutManager = LinearLayoutManager(activity)
//            // set the custom adapter to the RecyclerView
//            adapter = RecyclerAdapter()
//        }
    }


