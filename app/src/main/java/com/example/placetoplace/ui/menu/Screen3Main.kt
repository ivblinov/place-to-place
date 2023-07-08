package com.example.placetoplace.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.placetoplace.PlaceViewModel
import com.example.placetoplace.data.UserInfo
import com.example.placetoplace.databinding.FragmentScreen3MainBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Screen3Main.newInstance] factory method to
 * create an instance of this fragment.
 */
class Screen3Main : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentScreen3MainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentScreen3MainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isMyPlaceAndPlaceWished()
        }

        binding.cityEditText.doOnTextChanged { text, _, _, _ ->
            binding.resultCity.text = text
        }

        binding.buttonSave.setOnClickListener {
            val city = binding.cityEditText.text.toString()
            val number = binding.numberEditText.text.toString()
            val street = binding.streetEditText.text.toString()
            val house = binding.houseEditText.text.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.onSave(city, number, street, house, MY_PLACE)
            }
        }

        binding.buttonSaveWish.setOnClickListener {
            val city = binding.cityEditTextWish.text.toString()
            val number = binding.numberEditTextWish.text.toString()
            val street = binding.streetEditTextWish.text.toString()
            val house = binding.houseEditTextWish.text.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.onSave(city, number, street, house, PLACE_WISHED)
            }
        }

/*        binding.buttonMyAddress.setOnClickListener {
            FirebaseCrashlytics.getInstance().log("This is log message with additional info")
            try {
                throw Exception("My first exception")
            }
            catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
            }

        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val MY_PLACE = "my_place"
        const val PLACE_WISHED = "place_wished"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Screen3Main.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Screen3Main().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}