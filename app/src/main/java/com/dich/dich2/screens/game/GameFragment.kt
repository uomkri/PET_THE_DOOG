package com.dich.dich2.screens.game

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dich.dich2.R
import com.dich.dich2.databinding.FragmentGameBinding
import com.dich.dich2.utils.VKWallPostCommand
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback


class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this).get(GameViewModel::class.java)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentGameBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.button2.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    binding.imageView.setImageResource(R.drawable.doog0)
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_DOWN -> {
                    binding.imageView.setImageResource(R.drawable.doog1)
                    binding.uid.text = "Your pet ID is: ${viewModel.generatePetId()}"
                    binding.textView3.text = "GOOD JOB! PRESS HERE TO SHARE!"
                    viewModel.incrementCounter()
                    setShareClickListener()
                    return@setOnTouchListener true
                } else -> return@setOnTouchListener false
            }
        }
        return binding.root
    }

    private fun setShareClickListener() {

        val photo = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + requireContext().resources.getResourcePackageName(R.drawable.doog1)
                    + '/' + requireContext().resources.getResourceTypeName(R.drawable.doog1)
                    + '/' + requireContext().resources.getResourceEntryName(R.drawable.doog1)
        )

        val photos = mutableListOf(photo)

        binding.textView3.setOnClickListener {
            VK.execute(VKWallPostCommand(
                "I have pet the doog ${viewModel.counter.value} times! Last pet ID was ${viewModel.lastId.value}.",
                photos
            ), object : VKApiCallback<Int> {
                override fun fail(error: Exception) {
                    Toast.makeText(requireContext(), "posting failed", Toast.LENGTH_SHORT).show()
                    Log.e("err", error.message!!)
                }

                override fun success(result: Int) {
                    Toast.makeText(requireContext(), "posting successful", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }
    }

}