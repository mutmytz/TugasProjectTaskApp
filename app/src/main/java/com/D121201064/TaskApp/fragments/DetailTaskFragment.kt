package com.D121201064.TaskApp.fragments

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.D121201064.TaskApp.R
import com.D121201064.TaskApp.databinding.FragmentDetailTaskBinding
import com.D121201064.TaskApp.databinding.FragmentEditTaskBinding
import com.D121201064.TaskApp.model.Task
import com.D121201064.TaskApp.viewmodel.TaskViewModel

class DetailTaskFragment : Fragment() {
    private var _binding: FragmentDetailTaskBinding?= null
    private val binding get() = _binding!!
    private lateinit var taskViewModel: TaskViewModel
    private val args by navArgs<DetailTaskFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailTaskBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        binding.titleTask.setText(args.currentTask.title)
        binding.taskCategory.setText(args.currentTask.category)
        if (args.currentTask.stats.equals("Done")){
            binding.btnDone.visibility = View.GONE
        }else{
            binding.btnDone.visibility = View.VISIBLE
        }
        when(args.currentTask.category){
            "Penting Mendesak"->{
                binding.colorCategory.backgroundTintList= ColorStateList.valueOf(resources.getColor(R.color.red))
            }
            "Tidak Penting Mendesak"->{
                binding.colorCategory.backgroundTintList= ColorStateList.valueOf(resources.getColor(R.color.oren))
            }
            "Penting Tidak Mendesak"->{
                binding.colorCategory.backgroundTintList= ColorStateList.valueOf(resources.getColor(R.color.green))
            }
            "Tidak Penting Tidak Mendesak"->{
                binding.colorCategory.backgroundTintList= ColorStateList.valueOf(resources.getColor(R.color.yellow))
            }
        }

        binding.taskDescription.setText(args.currentTask.description)

        binding.btnBack.setOnClickListener {

            if (args.currentTask.stats.equals("Done")){
                findNavController().navigate(R.id.action_detailTaskFragment_to_historyFragment)
            }else{
                findNavController().navigate(R.id.action_detailTaskFragment_to_homeFragment)
            }
        }

        binding.btnDone.setOnClickListener {
            val view = View.inflate(context,R.layout.dialog_yes_no,null)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)

            val dialog = builder.create()
            val yes = view.findViewById<Button>(R.id.yes)
            val no = view.findViewById<Button>(R.id.no)

            dialog.show()
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            yes.setOnClickListener {
                val task = Task(args.currentTask.id,args.currentTask.title,args.currentTask.category, args.currentTask.description,"Done")
                taskViewModel.updateTask(task)
                Toast.makeText(requireContext(),"Done",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                findNavController().navigate(R.id.action_detailTaskFragment_to_homeFragment)
            }
            no.setOnClickListener {
                dialog.dismiss()
            }

        }

        return view
    }
}