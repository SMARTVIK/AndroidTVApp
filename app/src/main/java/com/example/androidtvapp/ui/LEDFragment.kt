package com.example.androidtvapp.ui

import ListAdapter
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.leanback.widget.VerticalGridView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtvapp.R
import com.example.androidtvapp.model.DashBoardDataResponseItem
import com.example.androidtvapp.network.Resource
import com.example.androidtvapp.repository.LEDRepository
import com.example.androidtvapp.viewmodel.DashboardViewModel
import com.example.androidtvapp.viewmodel.DashboardViewModelFactory

class LEDFragment : Fragment() {

    private var dialog: Dialog ? = null
    private lateinit var recyclerView: VerticalGridView
    private lateinit var adapter: ListAdapter
    private lateinit var viewModel: DashboardViewModel
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_tv, container, false)
        viewModelSetUp()
        recyclerView = view.findViewById(R.id.recyclerView)
        return view
    }

    private fun viewModelSetUp() {
        val repo = LEDRepository()
        val viewModelFactory = DashboardViewModelFactory(requireActivity().application, repo)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[DashboardViewModel::class.java]
        viewModel.getDashboardData()
        viewModel.dashboardData.observe(viewLifecycleOwner) {response->
            when (response) {
                is Resource.Success -> {
                    hideLoader()
                   setUpList(response.data)
                }

                is Resource.Error -> {
                    hideLoader()
                    response.message?.let { errorMessage ->
                        Toast.makeText(requireContext(), "Error Message ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                }

                is Resource.Loading -> {
                    showLoadingDialog(requireContext(), "Loading...")
                }
            }
        }
    }

    private fun hideLoader() {
        dialog?.let {
            dialog?.dismiss()
        }
    }

    private fun setUpList(data: List<DashBoardDataResponseItem>?) {
        adapter = ListAdapter(data)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
    }

    private fun showLoadingDialog(context: Context, message: String) : Dialog? {
        dialog = Dialog(context)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setContentView(R.layout.custom_dialog_loading)

        val loadingText = dialog?.findViewById<TextView>(R.id.loadingText)
        loadingText?.text = message // Set your custom loading message here

        dialog?.show()
        return dialog
    }
}
