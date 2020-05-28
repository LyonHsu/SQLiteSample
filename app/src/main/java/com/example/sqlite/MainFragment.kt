package com.example.sqlite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.main_fragment.*

/**
 * https://www.hangge.com/blog/cache/detail_2294.html
 */
class MainFragment : Fragment() {
    val TAG = "MainFragment"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = LayoutInflater.from(context).inflate(R.layout.main_fragment, container, false)
        init()
        return view
    }

    override fun onResume() {
        super.onResume()
        val array=memberDatabaseHelper.getNames()
        val len = array.size
        var sss:String=""
        for(i in 0..len-1){
            val moedl: MemberDatabaseHelper.ItemModel = array[i]
            sss = sss+ "["+i+"]"+moedl.name+"("+moedl.time+")\n"
        }
        textView2.text = sss
    }

    fun init(){
        memberDatabaseHelper.addName("Lyon")


    }


}