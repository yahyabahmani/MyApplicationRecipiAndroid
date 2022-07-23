package com.example.myapplicationrecipi.ui.fragment.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationrecipi.R
import com.example.myapplicationrecipi.adapter.RecipeCallBack
import com.example.myapplicationrecipi.adapter.RecipesAdapterSecond
import com.example.myapplicationrecipi.viewModel.MainViewModel
import com.example.myapplicationrecipi.adapter.RecipsAdapter
import com.example.myapplicationrecipi.databinding.FragmentRecepiBinding
import com.example.myapplicationrecipi.util.Constants.Companion.api_Key
import com.example.myapplicationrecipi.util.NetworkResult
import com.example.myapplicationrecipi.viewModel.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recepi.view.*


@AndroidEntryPoint
class RecepiFragment : Fragment(), RecipeCallBack {
    private var _binding: FragmentRecepiBinding? = null//Question
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private val recipesViewModel: RecipesViewModel by viewModels()

    //    private lateinit var mview: View
    private val mAdapter by lazy { RecipsAdapter() }
    private val mAdapter2 by lazy { RecipesAdapterSecond(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mainViewModel  = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragme

        //  mview  =   inflater.inflate(R.layout.fragment_recepi, container, false) //  question inflater
        _binding = FragmentRecepiBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        showShimmerEffect()
        getData()

        binding.recipesFab.setOnClickListener {
            setFragmentResultListener("result") { resultKey, result ->
                getData()
            }
            findNavController().navigate(
                RecepiFragmentDirections.actionRecepiFragmentToRecipesBottomSheet()
//                R.id.action_recepiFragment_to_recipesBottomSheet
            )
        }
        return binding.root


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getData() {
        mainViewModel.getRecipes(recipesViewModel.applyQuesry())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        mAdapter.setData(it)
                        mAdapter2.submitList(it.results)
                    }
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

    private fun showShimmerEffect() {
        binding.recepiRecycler.adapter = mAdapter2
        binding.recepiRecycler.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onItemClicked(position:Int) {
        Toast.makeText(requireContext(), "item clicked $position", Toast.LENGTH_SHORT).show()
    }
//         recepiRecycler.apply {
//            // set a LinearLayoutManager to handle Android
//            // RecyclerView behavior
//            layoutManager = LinearLayoutManager(activity)
//            // set the custom adapter to the RecyclerView
//            adapter = RecyclerAdapter()
//        }
}


