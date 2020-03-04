package com.wearetriple.tripleonboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.wearetriple.tripleonboarding.adapter.InfoTopicAdapter
import com.wearetriple.tripleonboarding.data.InfoTopic
import kotlinx.android.synthetic.main.activity_info.*


class InfoActivity : AppCompatActivity() {

    private val infoTopics = arrayListOf<InfoTopic>()
    private val infoTopicAdapter = InfoTopicAdapter(infoTopics)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        getAllInfoTopics()
        initView()
    }

    private fun initView() {
        rvInfoTopics.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvInfoTopics.adapter = infoTopicAdapter
    }

    private fun getAllInfoTopics() {
        val database = FirebaseDatabase.getInstance()
        val infoRef = database.getReference("info")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataSnapshot.children.forEach {
                    val item: InfoTopic? = it.getValue(InfoTopic::class.java)

                    if(item != null){
                        // TODO: Should user be notified when -1 is going to be assigned or when
                        //  -1 is going to be used (when opening dialog in InfoTopicAdapter)
                        item.id = it.key?.toInt() ?: -1
                        infoTopics.add(item)
                    }
                }
                println("\n\nList\n\n")
                println(infoTopics)

                infoTopicAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }

        infoRef.addValueEventListener(postListener)
    }
}
