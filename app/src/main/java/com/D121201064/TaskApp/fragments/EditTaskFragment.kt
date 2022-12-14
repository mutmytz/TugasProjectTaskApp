package com.D121201064.TaskApp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.D121201064.TaskApp.R
import com.D121201064.TaskApp.adapter.AdapterTask
import com.D121201064.TaskApp.databinding.FragmentEditTaskBinding
import com.D121201064.TaskApp.databinding.FragmentHomeBinding
import com.D121201064.TaskApp.model.Task
import com.D121201064.TaskApp.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

class EditTaskFragment : Fragment() {
    private var _binding: FragmentEditTaskBinding?= null
    private val binding get() = _binding!!
    private lateinit var taskViewModel: TaskViewModel
    private val args by navArgs<EditTaskFragmentArgs>()
    private lateinit var category:ArrayAdapter<CharSequence>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEditTaskBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        binding.taskTitle.setText(args.currentTask.title)
        category = ArrayAdapter.createFromResource(requireContext(),R.array.category_task,android.R.layout.simple_list_item_1)
        binding.dropdownCategory.setText(args.currentTask.category)
        binding.dropdownCategory.setAdapter(category)

        binding.taskDescription.setText(args.currentTask.description)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_editTaskFragment_to_homeFragment)
        }

        binding.btnSave.setOnClickListener {
            val title = binding.taskTitle.text.toString()
            val category = binding.dropdownCategory.text.toString()
            val description = binding.taskDescription.text.toString()

            if(title.isEmpty()){
                binding.taskTitle.error = "Title task is required!"
                binding.taskTitle.requestFocus()
                return@setOnClickListener
            }
            if (category.isEmpty()){
                Toast.makeText(requireContext(),"Blank category!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(description.isEmpty()){
                binding.taskDescription.error = "Description is required!"
                binding.taskDescription.requestFocus()
                return@setOnClickListener
            }

            val task = Task(args.currentTask.id,title,category, description,"Ongoing")
            taskViewModel.updateTask(task)
            Toast.makeText(requireContext(),"Task edited",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editTaskFragment_to_homeFragment)
        }

        return view
    }
}