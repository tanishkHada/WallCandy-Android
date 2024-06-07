package com.example.wallcandy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wallcandy.activities.WallpaperSelectActivity
import com.example.wallcandy.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    private var bind : FragmentBottomSheetBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return bind!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners(){
        bind!!.download.setOnClickListener {
            (activity as WallpaperSelectActivity).onSelectOption(1)
        }

        bind!!.setHomeScreen.setOnClickListener {
            (activity as WallpaperSelectActivity).onSelectOption(2)
        }

        bind!!.setLockScreen.setOnClickListener {
            (activity as WallpaperSelectActivity).onSelectOption(3)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }
}