package com.D121201064.TaskApp.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.D121201064.TaskApp.R
import com.D121201064.TaskApp.fragments.DetailTaskFragmentDirections
import com.D121201064.TaskApp.fragments.HistoryFragmentDirections
import com.D121201064.TaskApp.fragments.HomeFragmentDirections
import com.D121201064.TaskApp.model.Task
import com.D121201064.TaskApp.viewmodel.TaskViewModel

class AdapterTask:RecyclerView.Adapter<AdapterTask.TaskViewHolder>() {
    private var taskList = emptyList<Task>()
    private var context : Context?= null
    private lateinit var taskViewModel: TaskViewModel
    class TaskViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.title_task)
        val category:TextView = itemView.findViewById(R.id.task_category)
        val description:TextView = itemView.findViewById(R.id.task_description)
        val option:ImageView = itemView.findViewById(R.id.btn_options)
        val clickTask:CardView = itemView.findViewById(R.id.click_task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        context = parent.context
        taskViewModel = ViewModelProvider(context as FragmentActivity)[TaskViewModel::class.java]
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tugas,parent,false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = taskList[position]

        holder.title.text = currentItem.title
        holder.category.text = currentItem.category
        holder.description.text = currentItem.description

        when(currentItem.category){
            "Penting Mendesak"->{
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.red))
            }
            "Tidak Penting Mendesak"->{
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.oren))
            }
            "Penting Tidak Mendesak"->{
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.green))
            }
            "Tidak Penting Tidak Mendesak"->{
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.yellow))
            }
        }


        if (currentItem.stats.equals("Done")){
            holder.option.visibility = View.GONE
            holder.clickTask.setOnClickListener {

                holder.itemView.findNavController().navigate(HistoryFragmentDirections.actionHistoryFragmentToDetailTaskFragment(taskList[position]))
            }
        }else{
            holder.option.setOnClickListener {
                val popupMenu = PopupMenu(context,it)
                popupMenu.inflate(R.menu.popup_menu_adapter)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.edit_task->{
                            holder.itemView.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToEditTaskFragment(currentItem))
                            true
                        }
                        R.id.delete_task->{
                            taskViewModel.deleteTask(taskList[position])
                            Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT).show()
                            true
                        }
                        else->true
                    }
                }
                popupMenu.show()
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java).invoke(menu,true)
            }
            holder.clickTask.setOnClickListener {
                holder.itemView.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailTaskFragment(taskList[position]))
            }
        }

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun setData(task:List<Task>){
        this.taskList = task
        notifyDataSetChanged()
    }
}