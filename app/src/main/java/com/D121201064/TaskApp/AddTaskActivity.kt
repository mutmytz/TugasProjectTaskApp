package com.D121201064.TaskApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.D121201064.TaskApp.databinding.ActivityAddTaskBinding
import com.D121201064.TaskApp.model.Task
import com.D121201064.TaskApp.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding :ActivityAddTaskBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var category:ArrayAdapter<CharSequence>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        //add array category
        category = ArrayAdapter.createFromResource(this,R.array.category_task,android.R.layout.simple_list_item_1)
        binding.dropdownCategory.setAdapter(category)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnCreateTask.setOnClickListener {
            val title = binding.taskTitle.text.toString()
            val category = binding.dropdownCategory.text.toString()
            val description = binding.taskDescription.text.toString()

            if(title.isEmpty()){
                binding.taskTitle.error = "Title task is required!"
                binding.taskTitle.requestFocus()
                return@setOnClickListener
            }
            if (category.isEmpty()){
                Toast.makeText(this,"Blank category!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(description.isEmpty()){
                binding.taskDescription.error = "Description is required!"
                binding.taskDescription.requestFocus()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val task = Task(0,title,category, description,"Ongoing")
                taskViewModel.addTask(task)
                Toast.makeText(this@AddTaskActivity,"Success added task",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}