package com.D121201064.TaskApp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.D121201064.TaskApp.R
import com.D121201064.TaskApp.adapter.AdapterTask
import com.D121201064.TaskApp.databinding.FragmentHistoryBinding
import com.D121201064.TaskApp.databinding.FragmentHomeBinding
import com.D121201064.TaskApp.model.Task
import com.D121201064.TaskApp.viewmodel.TaskViewModel


class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding?= null
    private val binding get() = _binding!!
    private lateinit var taskViewModel: TaskViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        val adapterTask = AdapterTask()
        binding.recycleTugas.adapter = adapterTask
        binding.recycleTugas.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)

        taskViewModel.readAllDataHistory.observe(viewLifecycleOwner){task->
            adapterTask.setData(task)
        }

        binding.deleteAllHistory.setOnClickListener {
            val view = View.inflate(context,R.layout.dialog_yes_no,null)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)

            val dialog = builder.create()
            val yes = view.findViewById<Button>(R.id.yes)
            val no = view.findViewById<Button>(R.id.no)
            val title = view.findViewById<TextView>(R.id.title_dialog)
            title.text = "Delete History?"
            dialog.show()
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            yes.setOnClickListener {
                taskViewModel.deleteAllHistory()
                dialog.dismiss()
            }
            no.setOnClickListener {
                dialog.dismiss()
            }

        }



        return view
    }
}