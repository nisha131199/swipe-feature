package com.nisha.swipe.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.nisha.swipe.R
import com.nisha.swipe.databinding.FragmentMiddleBinding

class MiddleFragment : Fragment() {

    private val binding by lazy { FragmentMiddleBinding.inflate(layoutInflater) }

    /**
     * Inflates the layout for this fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    /**
     * Resets the view to its initial state.
     */
    fun resetView() {
        binding.ivPass.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.white))
        binding.ivLike.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.white))
        binding.ibLike.backgroundTintList = null
        binding.ibPass.backgroundTintList = null
        hideMarks()
    }

    /**
     * Configures the view for swipe interactions.
     */
    fun setSwipeView() {
        binding.ivPass.backgroundTintList = null
        binding.ivLike.backgroundTintList = null
        binding.ibLike.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.white))
        binding.ibPass.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.white))
    }

    /**
     * Loads an image into the profile image view using Glide.
     *
     * @param imageUrl The URL of the image to load.
     */
    fun setImage(imageUrl: String) {
        Glide.with(binding.root).load(imageUrl).into(binding.ivProfile)
    }

    fun displayLikeMark() {
        binding.ibLikeMark.isVisible = true
        binding.ibPassMark.isVisible = false
    }

    fun displayPassMark() {
        binding.ibPassMark.isVisible = true
        binding.ibLikeMark.isVisible = false
    }

    fun hideMarks() {
        binding.ibPassMark.isVisible = false
        binding.ibLikeMark.isVisible = false
    }
}