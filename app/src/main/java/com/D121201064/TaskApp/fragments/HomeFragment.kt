package com.D121201064.TaskApp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.D121201064.TaskApp.adapter.AdapterTask
import com.D121201064.TaskApp.databinding.FragmentHomeBinding
import com.D121201064.TaskApp.viewmodel.TaskViewModel


class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding ?= null
    private val binding get() = _binding!!
    private lateinit var taskViewModel:TaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        val adapterTask = AdapterTask()
        binding.recycleTugas.adapter = adapterTask
        binding.recycleTugas.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        taskViewModel.readAllData.observe(viewLifecycleOwner){task->
            adapterTask.setData(task)
        }

        return view
    }
}