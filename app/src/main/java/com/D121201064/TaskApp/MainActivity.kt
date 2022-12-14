package com.D121201064.TaskApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.D121201064.TaskApp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navControl = findNavController(R.id.fragment)
        binding.bottomNavi.setupWithNavController(navControl)

        binding.btnAddTask.setOnClickListener {
            val intent = Intent(this,AddTaskActivity::class.java)
            startActivity(intent)
        }

        navControl.addOnDestinationChangedListener{_,dest,_->
            if(dest.id == R.id.editTaskFragment||dest.id==R.id.detailTaskFragment){
                binding.bottomAppBar.visibility = View.GONE
                binding.btnAddTask.visibility = View.GONE
            }else{
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.btnAddTask.visibility = View.VISIBLE
            }
        }


    }
}