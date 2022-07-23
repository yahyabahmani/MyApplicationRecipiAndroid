package com.example.myapplicationrecipi.ui.fragment.recipes.bottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.myapplicationrecipi.R
import com.example.myapplicationrecipi.ui.Test
import com.example.myapplicationrecipi.viewModel.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_recipes_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_recipes_bottom_sheet.view.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipesBottomSheet.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipesBottomSheet : BottomSheetDialogFragment() {
    private val recepiesViewModel by viewModels<RecipesViewModel>()
    private var mealTypeChip = "main course"
    private var mealTypeChipID = 0
    private var dietType = "gluten free"
    private var dietTypeChipID = 0

    @Inject lateinit var test:Test
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        recepiesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes_bottom_sheet, container, false)
    }

    override fun onViewCreated(mview: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mview, savedInstanceState)
        recepiesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selelctMealType
            dietType = value.selectDietType
            diet_type_chipGroup.check(value.selectDietTypeID)
            meal_type_chipGroup.check(value.selectMealTypeID)
        }
        mview.diet_type_chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds.first())
            val selectMealType = chip.text.toString().lowercase()
            mealTypeChip = selectMealType
            mealTypeChipID = checkedIds.first()
            Log.d("checkedIds", chip.text.toString())
        }



        mview.meal_type_chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds.first())
            val selectdietType = chip.text.toString().lowercase()
            dietType = selectdietType
            dietTypeChipID = checkedIds.first()
            Log.d("22dietTypeChipID", chip.text.toString())
        }
        mview.apply_button.setOnClickListener {
            Log.d("mealTypeChipID", mealTypeChipID.toString())
            Log.d("dietTypeChipID", dietTypeChipID.toString())

            recepiesViewModel.saveMeal(
                mealTypeChip,
                meal_type_chipGroup.checkedChipId,
                dietType,
                diet_type_chipGroup.checkedChipId
            )

            setFragmentResult(
                "result", bundleOf(
                    "result2" to 23
                )
            )

//            val bundle = Bundle()
//            bundle.putInt("test",11)

//            Navigation.findNavController(mview).navigateUp()
            findNavController().navigateUp()

        }
    }

    private fun updateChip(chipID: Int, chipGroup: ChipGroup) {
        Log.d("chipID", chipID.toString())

        if (chipID != 0) {
            try {
         chipGroup.findViewById<Chip>(chipID).isChecked = true
              //  chipGroup.check(chipID)


            } catch (e: Exception) {
                Log.d("RecepiBottomSheet2", e.message.toString())
            }
        }
    }

}